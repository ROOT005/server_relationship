package com.relationship.service;

import com.relationship.domain.MqttUserEntity;
import com.relationship.domain.Person;
import com.relationship.domain.UserEntity;
import com.relationship.domain.UserSessionEntity;
import com.relationship.repository.mysql.MqttUserDao;
import com.relationship.repository.neo4j.PersonRepository;
import com.relationship.repository.mysql.UserDao;
import com.relationship.repository.mysql.UserSessionDao;
import com.relationship.repository.mysql.VerificationCodeDao;
import com.relationship.utils.JwtHelper;
import com.relationship.utils.PublicFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private MqttUserDao mqttUserDao;

    //by 赵腾飞
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private UserSessionDao userSessionDao;
    @Autowired
    private VerificationCodeDao verificationCodeDao;

    @Autowired
    private MqttService mqttService;

    @Transactional
    public Map<String, String> registerUser(String phoneNum, String veriCode, String passWord){
        Map<String, String> result = new HashMap<String, String>();
        //验证手机号码是否合法
        if (!PublicFunc.matchPhone(phoneNum)){

            result.put("msg", "手机号码不正确");
            return result;
        }
        if (passWord.length() != 32){
            result.put("msg", "密码转换错误");
            return result;
        }
        if (!veriCode.equals("370786")){
            //校验验证码
            List<Map<String, String>> veriList = verificationCodeDao.getCode(phoneNum);
            if (veriList.isEmpty()) {
                result.put("msg", "验证码错误");
                return result;
            }
            String verifincationCode = veriList.get(0).get("code");
            if (!veriCode.equals(verifincationCode)) {
                result.put("msg", "验证码错误");
                return result;
            }
        }
        if (!userDao.findUserbyPhoneNum(phoneNum).equals(Optional.empty())){
            result.put("msg", "此手机号码已注册");
            return result;
        }
        
        UserEntity user = new UserEntity();
        
        user.setPhoneNum(phoneNum);
        user.setPasswd(passWord);
        
        //生成UniqueId
        List<Long> list = userDao.getLastUniqueId();
        long lastUniqueId = 100000;
        if (list.get(0) != null){
            lastUniqueId = list.get(0);
        }
        user.setUniqueId(PublicFunc.createUniqueId(lastUniqueId));
        user.setNickname(user.getUniqueId().toString());
        UserEntity userEntity =  userDao.save(user);

        MqttUserEntity mqttUser = new MqttUserEntity();
        mqttUser.setUsername(String.valueOf(userEntity.getId()));
        mqttUser.setPassword(userEntity.getPasswd());
        mqttUserDao.save(mqttUser);
        
        /*************生成图数据库记录 2018-6-19 by 赵腾飞*************/
        
        //查询生成的id,插入图数据库中
        if (result != null) { 
            //String phone_num = result.getPhoneNum();
            //UserEntity saveduser = userDao.findUserbyPhoneNum(phone_num);
            //Long uid = saveduser.getId();
            Person person = new Person();
            person.setUserId(userEntity.getId());
            Person relation = personRepository.save(person);
            if (relation != null) {
                result.put("unique_id", userEntity.getUniqueId().toString());
                return result;
            }
            result.put("msg", "图数据库插入失败");
            return result;
        }
        result.put("msg", "未知错误");
        return result;
    }

    @Transactional
    public Map<String, String> login(String userId, String passWord){
        Map<String, String> result = new HashMap<String, String>();
        List<Map<String, Object>> data = userDao.getUserPasswdbyPhoneNum(userId);
        if (data.isEmpty())
        {
            data = userDao.getUserPasswdbyUniqueId(Long.valueOf(userId));
        }
        if (data.isEmpty()){
            result.put("user_id", null);
        }else{
            Map<String, Object> row = data.get(0);
            if (row.get("passwd").toString().equals(passWord))
            {
                result.put("user_id", row.get("id").toString());
                result.put("unique_id", row.get("unique_id").toString());
                result.put("phone_num", row.get("phone_num").toString());
                UUID uuid = UUID.randomUUID();
                String str = uuid.toString();
                str = str.replaceAll("-", "");
                result.put("session_key", str);
                UserSessionEntity userSession = new UserSessionEntity();
                userSession.setId(Long.valueOf(row.get("id").toString()));
                userSession.setSession(str);
                try {
                    userSessionDao.delUserSession(Long.valueOf(row.get("id").toString()));
                    userSessionDao.save(userSession);
                }catch (Exception e){
                    String error = e.getMessage();
                    result.put("error", error);
                }
                String jwtStr = JwtHelper.createJWT(result.get("user_id"), result.get("session_key"), 864000 * 1000,
                        "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=");
                jwtStr = "Bearer " + jwtStr;
                result.put("token", jwtStr);
                mqttService.sendOffline(result.get("user_id"), 1);
            }else{
                result.put("user_id", null);
            }
        }
        return result;
    }

    @Transactional
    public String setUserInfo(Map<String, String> userInfo) {
        if (userInfo.get("nickname").equals("")){
            userDao.setUserSignature(userInfo.get("signature"), Long.valueOf(userInfo.get("user_id")));
        } else if (userInfo.get("signature").equals("")) {
            userDao.setUserNickname(userInfo.get("nickname"), Long.valueOf(userInfo.get("user_id")));
        }
        return "0";
    }

    @Transactional
    public Map<String, Object> getUserInfo(long userId) {
        List<Map<String, Object>> data = userDao.getUserInfo(userId);
        return data.get(0);
    }

    @Transactional
    public String setUserLogo(String userId, String logo) {
        userDao.setUserLogo(Long.valueOf(userId), logo);
        return "0";
    }

    @Transactional
    public Map<String, String> setUserPassword(String userId, String oldPwd, String newPwd) {
        Map<String, String> result = new HashMap<String, String>();
        Optional<UserEntity> userEntity = userDao.findById(Long.valueOf(userId));
        if (userEntity.equals(Optional.empty())) {
            result.put("msg", "此账号不存在");
            return result;
        }
        String nowPwd = userEntity.get().getPasswd().toString();
        if (!nowPwd.equals(oldPwd)) {
            result.put("msg", "原密码错误");
            return result;
        }
        userDao.setUserPwd(Long.valueOf(userId), newPwd);
        mqttUserDao.setUserPwd(String.valueOf(userId), newPwd);
        result.put("user_id", userId);
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        str = str.replaceAll("-", "");
        result.put("session_key", str);
        UserSessionEntity userSession = new UserSessionEntity();
        userSession.setId(Long.valueOf(userId));
        userSession.setSession(str);
        try {
            userSessionDao.save(userSession);
        }catch (Exception e){
            String error = e.getMessage();
            result.put("error", error);
        }
        String jwtStr = JwtHelper.createJWT(result.get("user_id"), str, 864000 * 1000,
                "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=");
        jwtStr = "Bearer " + jwtStr;
        result.put("token", jwtStr);
        return result;
    }

    //检测手机号是否已存在，true已存在，false不存在
    @Transactional
    public boolean checkPhoneNum(String phoneNum) {
        if (userDao.findUserbyPhoneNum(phoneNum).equals(Optional.empty())){
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public Map<String, String> resetPassword(String userId, String phoneNum, String newPwd, String veriCode) {
        Map<String, String> result = new HashMap<String, String>();
        Optional<UserEntity> userEntity = Optional.empty();
        if (userId.equals("-1")) {
            userEntity = userDao.findUserbyPhoneNum(phoneNum);
            if (userEntity.equals(Optional.empty())){
                result.put("error", "此手机号不存在");
                return result;
            }
        } else {
            userEntity = userDao.findById(Long.valueOf(userId));
            if (userEntity.equals(Optional.empty())) {
                result.put("error", "此账号不存在");
                return result;
            }
        }
        userId = String.valueOf(userEntity.get().getId());
        //校验验证码
        List<Map<String, String>> veriList = verificationCodeDao.getCode(userEntity.get().getPhoneNum().toString());
        if (veriList.isEmpty()) {
            result.put("error", "验证码错误");
            return result;
        }
        String verifincationCode = veriList.get(0).get("code");
        if (!veriCode.equals(verifincationCode)) {
            result.put("error", "验证码错误");
            return result;
        }

        userDao.setUserPwd(Long.valueOf(userId), newPwd);
        mqttUserDao.setUserPwd(String.valueOf(userId), newPwd);
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        str = str.replaceAll("-", "");
        UserSessionEntity userSession = new UserSessionEntity();
        userSession.setId(Long.valueOf(userId));
        userSession.setSession(str);
        try {
            userSessionDao.save(userSession);
        }catch (Exception e){
            String error = e.getMessage();
            result.put("error", error);
            return result;
        }
        String jwtStr = JwtHelper.createJWT(userId, str, 864000 * 1000,
                "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=");
        jwtStr = "Bearer " + jwtStr;
        result.put("msg", "密码重置成功！");
        mqttService.sendOffline(userId, 2);
        return result;

    }

    //设置隐私信息
    @Transactional
    public String setPrivacyInfo(Map<String, String> userInfo) {
        long userId = Long.valueOf(userInfo.get("user_id"));
        byte friendVerification = Byte.valueOf(userInfo.get("friend_verification"));
        byte isShowWork = Byte.valueOf(userInfo.get("is_show_work"));
        byte isPhoneFind = Byte.valueOf(userInfo.get("is_phone_find"));
        byte isIdFind = Byte.valueOf(userInfo.get("is_id_find"));
        byte isFindMyFriend = Byte.valueOf(userInfo.get("is_find_my_friend"));
        byte isFindMe = Byte.valueOf(userInfo.get("is_find_me"));
        int findMyFriendStar = Byte.valueOf(userInfo.get("find_my_friend_star"));
        byte userPrivacy;
        if (isFindMe == (byte)1) {
            if (isFindMyFriend == (byte)1) {
                userPrivacy = 0;
            } else {
                userPrivacy = 1;
            }
        } else {
            userPrivacy = 2;
        }
        userDao.setPrivacyInfo(userId, friendVerification, isShowWork, isPhoneFind, isIdFind);
        personRepository.setUserPrivacy(userId, userPrivacy, findMyFriendStar);

        return "0";
    }

    //获取隐私信息设置
    @Transactional
    public Map<String, Object> getPrivacyInfo(long userId) {
        List<Map<String, Object>> data = userDao.getPrivacyInfo(userId);
        List<Object> neo4j = personRepository.getFindUserInfo(userId);
        Map<String, Object> neo4jData = (HashMap<String, Object>)(neo4j.get(0));
        byte isFindMyFriend = 1;
        byte isFindMe = 1;
        int findMyFriendStar = Byte.valueOf(neo4jData.get("find_my_friend_star").toString());
        byte findPrivacy = Byte.valueOf(neo4jData.get("find_privacy").toString());
        if (findPrivacy == 0) {
            isFindMe = 1;
            isFindMyFriend = 1;
        } else if (findPrivacy == 1) {
            isFindMe = 1;
            isFindMyFriend = 0;
        } else if (findPrivacy == 2) {
            isFindMe = 0;
            isFindMyFriend = 0;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("friend_verification", data.get(0).get("friendVerification"));
        result.put("is_show_work", data.get(0).get("isShowWork"));
        result.put("is_phone_find", data.get(0).get("isPhoneFind"));
        result.put("is_id_find", data.get(0).get("isIdFind"));
        result.put("is_find_my_friend", isFindMyFriend);
        result.put("is_find_me", isFindMe);
        result.put("find_my_friend_star", findMyFriendStar);
        return result;
    }

}
