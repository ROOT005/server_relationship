package com.relationship.service;
/**
 * 好友管理服务
 * 2018-6-19 by 赵腾飞
 * */

import com.relationship.domain.AddFriendMessageEntity;
import com.relationship.domain.Relationship;
import com.relationship.domain.UserEntity;
import com.relationship.domain.WorksInfoEntity;
import com.relationship.repository.mysql.AddFriendMessageRepository;
import com.relationship.repository.mysql.ConsummateJobRepository;
import com.relationship.repository.mysql.FriendManageRepository;
import com.relationship.repository.mysql.UserDao;
import com.relationship.repository.neo4j.RelationshipRepository;
import com.relationship.utils.message.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FriendsManageService {
    private FriendManageRepository friendManageRepository;
    private RelationshipRepository relationshipRepository;
    private AddFriendMessageRepository addFriendMessageRepository;

    @Autowired
    private ConsummateJobRepository consummateJobRepository;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MqttService mqttService;
    
    public FriendsManageService(FriendManageRepository friendManageRepository,
            RelationshipRepository relationshipRepository,
            AddFriendMessageRepository addFriendMessageRepository) {
        this.friendManageRepository = friendManageRepository;
        this.relationshipRepository = relationshipRepository;
        this.addFriendMessageRepository = addFriendMessageRepository;
    }
    
    //查找好友是否存在
    public ResponseResult searchFriend(String searchInfo) {
        UserEntity user = friendManageRepository.findByPhoneNum(searchInfo);
        if (user == null) {
            user = friendManageRepository.findByUniqueId(Long.valueOf(searchInfo));
            if (user == null) {
                return new ResponseResult(1, "该用户不存在！", null);
            }
        }
        Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("id", user.getId());
        userInfo.put("unique_id", user.getUniqueId());
        userInfo.put("sex", user.getSex());
        userInfo.put("city_id", user.getCityId());
        userInfo.put("logo", user.getLogo());
        userInfo.put("nickname", user.getNickname());
        if (user.getWorkId() != Long.valueOf(0)) {
            Optional<WorksInfoEntity> worksInfoEntity = consummateJobRepository.findById(user.getWorkId());
            userInfo.put("job", worksInfoEntity.get().getJob());
        } else {
            userInfo.put("job", "");
        }
        return new ResponseResult(0, "查询成功", userInfo);
    }
    
    //添加好友
    public ResponseResult addFriend(Map<String, Object> body) {
        Long user_id = Long.valueOf(body.get("user_id").toString());
        Long friend_id = Long.valueOf(body.get("friend_id").toString());
        int star = (int)Float.parseFloat(body.get("star").toString());
        String relationname = body.get("type").toString();
        String message = body.get("message").toString();
        String remark = body.get("remark").toString();

        Optional<UserEntity> friendInfo = userDao.findById(friend_id);
        //先根据给的id查询是否已经是好友
		List<Object> result = relationshipRepository.findRelatinship(user_id, friend_id);

		if(result.get(0)!=null){
			return new ResponseResult(0,"你们已经是好友",null);
		}

        Date date = new Date();   
        Timestamp sendTime = new Timestamp(date.getTime());

        //插入好友添加好友消息表
        AddFriendMessageEntity friendMessageEntity = new  AddFriendMessageEntity();
        friendMessageEntity.setUserId(user_id);
        friendMessageEntity.setFriendUid(friend_id);
        friendMessageEntity.setType(relationname);
        friendMessageEntity.setStar(star);
        friendMessageEntity.setSendTime(sendTime);
        friendMessageEntity.setMessage(message);
        friendMessageEntity.setRemark(remark);
        
        addFriendMessageRepository.save(friendMessageEntity);
        //TODO 发送mqtt消息

        Long addFriendId = friendMessageEntity.getId();
        Optional<UserEntity> userInfo = userDao.findById(user_id);
        String userUniqueId = userInfo.get().getUniqueId().toString();
        mqttService.sendAddFriend(String.valueOf(friendMessageEntity.getId()), user_id.toString(), friend_id.toString(),
                userUniqueId, message, sendTime);
        return new ResponseResult(0, "发送成功!", null);
    }
    
    //查询返回名片
    public ResponseResult getFriendInfo(long userId, long friendId) {
        Optional<UserEntity> user = friendManageRepository.findById(friendId);
        if (!user.isPresent()) {
            return new ResponseResult(1, "该用户不存在！", null);
        }
        List<Object> listObject = relationshipRepository.findRelatinship(userId, friendId);
        listObject = (List<Object>)listObject.get(0);
        if (listObject == null) {
            return new ResponseResult(1, "尚未建立好友关系", null);
        }
        Map<String, Object> rData = (Map<String,Object>)listObject.get(1);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("user_id", user.get().getId());
        data.put("logo", user.get().getLogo());
        data.put("nickname", user.get().getNickname());
        data.put("uniqueid", user.get().getUniqueId());
        data.put("sex", user.get().getSex());
        data.put("cityid", user.get().getCityId());
        if (rData.containsKey("remark")) {
            data.put("remark", rData.get("remark"));
        } else {
            data.put("remark", "");
        }
        if (rData.containsKey("tab")) {
            data.put("tab", rData.get("tab"));
        } else {
            data.put("tab", "");
        }
        return new ResponseResult(0, "查询成功", data);
    }
    //关于是否有加好友申请的消息
    public ResponseResult messageOfAddFriend(Map<String, Object> data) {
        Long user_id = Long.parseLong(data.get("user_id").toString());
        List<AddFriendMessageEntity> result = addFriendMessageRepository.addFriendMessage(user_id);
        if (result.isEmpty()) {
            return new ResponseResult(1,"没有消息",null);
        }
        return new ResponseResult(0,"有新的好友",result);
    }


    //好友消息处理,code=1接受, code=2拒绝
    public ResponseResult replyAddFriend(Map<String, Object> data) {
        
        int code = Integer.parseInt(data.get("code").toString());
        Long messageid = Long.parseLong(data.get("add_friend_id").toString());
        String message = data.get("message").toString();

        if (code == 1) {
            //查询消息内容并插入图数据库生成关系,修改消息记录result的值
            Optional<AddFriendMessageEntity> result = addFriendMessageRepository.findById(messageid);
            Long user_id = result.get().getUserId();
            Long friend_id = result.get().getFriendUid();
            int star = result.get().getStar();
            String relationname = result.get().getType();
            String remark = result.get().getRemark();

            //先根据给的id查询是否已经是好友
            List<Object> relationshipResult = relationshipRepository.findRelatinship(user_id, friend_id);

            if(relationshipResult.get(0)!=null){
                return new ResponseResult(1,"你们已经是好友",null);
            }
            
            //建立双向关系
            relationshipRepository.addRelationship(user_id, friend_id, star, relationname, remark);
            relationshipRepository.addRelationship(friend_id, user_id, star, relationname, remark);
            
            //写入添加好友消息result
            addFriendMessageRepository.updateResult(code, messageid);

            mqttService.sendNewFriend(friend_id.toString(), user_id.toString());
            return new ResponseResult(0, "添加成功!", null);
        }
        if (code == 2) {
            //写入添加好友消息列表中
            addFriendMessageRepository.updateResult(code, messageid);
            return new ResponseResult(0, "拒绝成功!", null);
        }
        return new ResponseResult(1, "添加失败!", null);
    }
    
    //修改好友备注
    public ResponseResult setFriendRemarkTab(Map<String, Object> data) {
        Long user_id = Long.parseLong(data.get("user_id").toString());
        Long friend_id = Long.parseLong(data.get("friend_id").toString());
        String remark = data.get("remark").toString();
        String tab = data.get("tab").toString();
        relationshipRepository.setRemarkTab(user_id, friend_id, remark, tab);
        return new ResponseResult(0, "修改成功！", null);
    }

    //修改好友星级
    public ResponseResult setFriendStar(Map<String, Object> data) {
        Long user_id = Long.parseLong(data.get("user_id").toString());
        Long friend_id = Long.parseLong(data.get("friend_id").toString());
        int star = (int)Float.parseFloat(data.get("star").toString());
        relationshipRepository.setStar(user_id, friend_id, star);
        return new ResponseResult(0, "修改成功！", null);
    }
    
    //删除好友
    public ResponseResult deleteFriend(Map<String, Object> data) {
        Long user_id = Long.parseLong(data.get("user_id").toString());
        Long friend_id = Long.parseLong(data.get("friend_id").toString());
        relationshipRepository.deleteFriend(user_id, friend_id);
        return new ResponseResult(0, "删除成功！", null);
    }
    
    //加入黑名单
    public ResponseResult addBlacklist(Map<String, Object> data) {
        
        Long user_id = Long.parseLong(data.get("user_id").toString());
        Long friend_id = Long.parseLong(data.get("friend_id").toString());
        
        relationshipRepository.addBlacklist(user_id, friend_id);
        
        return new ResponseResult(0,"成功", null);
    }
    
    //从黑名单移除
    public ResponseResult removeBlacklist(Map<String, Object> data) {
        
        Long user_id = Long.parseLong(data.get("user_id").toString());
        Long friend_id = Long.parseLong(data.get("friend_id").toString());
        relationshipRepository.removeBlacklist(user_id, friend_id);
        
        return new ResponseResult(0,"成功", null);
    }
    
    //查询黑名单
    public ResponseResult findBlacklist(Map<String, Object> data) {
        Long user_id = Long.parseLong(data.get("user_id").toString());
        List<Object> result = relationshipRepository.blacklistFriend(user_id);

        String patten = "(?<=user_id=)([0-9]*)";
        Pattern r = Pattern.compile(patten);
        Matcher m = r.matcher(result.toString());
        HashSet<Long> set = new HashSet<>();
        while (m.find()) {
            set.add(Long.valueOf(m.group()));
        }
        List<Map<String, Object>> userInfo = null;
        if (set.size() != 0) {
            List<Long> userIds = new ArrayList<Long>();
            userIds.addAll(set);
            userInfo = userDao.getUserInfoWithList(userIds);
        }

        return new ResponseResult(0,"成功", result, userInfo);
    }
    
    //查询所有好友
    public ResponseResult allFriend(Map<String, Object> data) {
        Long user_id = Long.parseLong(data.get("user_id").toString());
        List<Relationship> result = relationshipRepository.findFriend(user_id);
		Long friend_id;
        Optional<UserEntity> user;
        Map<String, Object> friends = new HashMap<>();



		for(int i = 0 ; i < result.size() ; i++) {
            Map<String, Object> friendinfo = new HashMap<>();
			friend_id = result.get(i).getFriend().getUserId();
			user = friendManageRepository.findById(friend_id);
            friendinfo.put("user_id", friend_id.toString());
			friendinfo.put("logo",user.get().getLogo()==null? null:user.get().getLogo());
			friendinfo.put("remark",result.get(i).getRemark());
			friendinfo.put("star", result.get(i).getStar());
			friendinfo.put("unique_id", user.get().getUniqueId().toString());
			friendinfo.put("sex", user.get().getSex());
			friendinfo.put("city_id", user.get().getCityId());
			friendinfo.put("phone_num", user.get().getPhoneNum());
			friendinfo.put("tab", result.get(i).getTab());


            if (user.get().getNickname()==null) {
				friendinfo.put("nickname",user.get().getUniqueId().toString());
				friends.put(user.get().getUniqueId().toString(), friendinfo);
			}else{
				friendinfo.put("nickname", user.get().getNickname());
				friends.put(user.get().getUniqueId().toString(), friendinfo);
			}

        }

        return new ResponseResult(0,"成功", friends);
    }
    //深度查询
    public ResponseResult depthFind(Map<String, Object> data) {
        Long user_id = Long.parseLong(data.get("user_id").toString());
        int depth = Integer.parseInt(data.get("depth").toString());
        Object result;
        
        switch (depth) {
        case 1:
            result = relationshipRepository.firstDepthFriend(user_id);
            break;
        case 2:
            result = relationshipRepository.secendDepthFriend(user_id);
            break;
        case 3:
            result = relationshipRepository.thirdDepthFriend(user_id);
            break;
        default:
            result = null;
            break;
        }
        
        return new ResponseResult(0,"成功", result);
    }
    //任意两个节点的所有最短路径查询
    public ResponseResult shortestPath(Map<String, Object> data) {
        Long user_id = Long.parseLong(data.get("user_id").toString());
        Long friend_uid = Long.parseLong(data.get("frienduid").toString());

		List<Object> result = relationshipRepository.shortestPath(user_id, friend_uid);

        return new ResponseResult(0,"请求成功！", result);
    }

    //通过工作查询任意两个节点最短路径
    public ResponseResult shortestPathByJob(Map<String,Object> data){
        Long user_id = Long.parseLong(data.get("user_id").toString());
        String jobname = data.get("job").toString();
        List<Object> result = relationshipRepository.shortestPathByJob(user_id, jobname);
        if (result.get(0) == null) {
            return new ResponseResult(0, "未找到！", null);
        }
		//删除黑名单的路线
		int split = result.size()/2;
        List<Object> pre = result.subList(0, split);
		List<Object> end = result.subList(split, result.size());
		//遍历反向关系，如果关系中有1,则把正向同等位置的关系删除
        for(int i=0; i<end.size(); i++) {
            if (pre.get(i).toString().contains("blacklist=1") |
                    end.get(i).toString().contains("blacklist=1")) {
                pre.set(i,null);
            }
        }
        pre.removeAll(Collections.singleton(null));


        String patten = "(?<=user_id=)([0-9]*)";

        Pattern r = Pattern.compile(patten);
        Matcher m = r.matcher(pre.toString());
        HashSet<Long> set = new HashSet<>();
        while (m.find()) {
            set.add(Long.valueOf(m.group()));
        }
        List<Long> userIds = new ArrayList<Long>();
        userIds.addAll(set);
        List<Map<String,Object>> userInfo = userDao.getUserInfoWithList(userIds);

		return  new ResponseResult(0, "请求成功！", pre, userInfo);
    }
}
