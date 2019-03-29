package com.relationship.controller;

import com.relationship.service.UserService;
import com.relationship.utils.message.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/*import com.relationship.domain.Req;*/

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService service;

    /*
    用户注册接口
     */
    @RequestMapping(value = "/register")
    public ResponseResult register(@RequestBody Map<String, Object> body) {
        ResponseResult resResult = new ResponseResult();
        try {
            String phoneNum = body.get("phone_num").toString();
            String veriCode = body.get("veri_code").toString();
            String passWord = body.get("password").toString();
            Map<String, String> result = service.registerUser(phoneNum, veriCode, passWord);
            if (result.containsKey("unique_id")) {
                resResult.setStatus(0);
                resResult.setMessage("注册成功");
                resResult.setData(result);
            } else {
                resResult.setStatus(1);
                resResult.setMessage(result.get("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resResult.setStatus(1);
            resResult.setMessage(e.getMessage());
        }
        return resResult;
    }

    /*
    用户登录接口
     */
    @RequestMapping(value = "/login")
    public ResponseResult login(@RequestBody Map<String, Object> body) {
        ResponseResult resResult = new ResponseResult();
        try {
            String userId = body.get("user_id").toString();
            String passWord = body.get("password").toString();
            Map<String, String> userInfo = service.login(userId, passWord);
            if (userInfo.get("user_id") == null){
                resResult.setStatus(1);
                resResult.setMessage("用户不存现在或密码错误");
            }else{
                resResult.setStatus(0);
                resResult.setMessage("登录成功");
                resResult.setData(userInfo);
            }

        } catch (Exception e) {
            resResult.setStatus(1);
            resResult.setMessage(e.getMessage());
        }
        return resResult;
    }

    /*
    设置用户信息
     */
    @RequestMapping(value = "/set_user_info")
    public ResponseResult setUserInfo(@RequestBody Map<String, String> body) {
        ResponseResult resResult = new ResponseResult();
        try {
            String result = service.setUserInfo(body);
            if (result.equals("0")) {
                resResult.setStatus(0);
                resResult.setMessage("保存成功");
            } else {
                resResult.setStatus(1);
                resResult.setMessage(result);
            }
        } catch (Exception e) {
            resResult.setStatus(1);
            resResult.setMessage(e.getMessage());
        }
        return resResult;
    }


    /*
    设置用户头像
     */
    @RequestMapping(value = "/set_user_logo")
    public ResponseResult setUserLogo(@RequestBody Map<String, String> body){
        ResponseResult resResult = new ResponseResult();
        try {
            String result = service.setUserLogo(body.get("user_id"), body.get("logo"));
            if (result.equals("0")) {
                resResult.setStatus(0);
                resResult.setMessage("保存成功");
            } else {
                resResult.setStatus(1);
                resResult.setMessage(result);
            }
        } catch (Exception e) {
            resResult.setStatus(1);
            resResult.setMessage(e.getMessage());
        }
        return resResult;
    }

    @RequestMapping(value = "/get_user_info")
    public ResponseResult getUserInfo(@RequestBody Map<String, String> body) {
        ResponseResult resResult = new ResponseResult();
        try {
            Map<String, Object> result = service.getUserInfo(Long.valueOf(body.get("user_id").toString()));
            if (!result.isEmpty()) {
                resResult.setStatus(0);
                resResult.setMessage("获取成功");
                resResult.setData(result);
            } else {
                resResult.setStatus(1);
                resResult.setMessage("获取失败");
            }
        } catch (Exception e) {
            resResult.setStatus(1);
            resResult.setMessage(e.getMessage());
        }
        return resResult;
    }

    @RequestMapping(value = "/set_user_password")
    public ResponseResult setUserPassword(@RequestBody Map<String, String> body, HttpServletResponse response){
        ResponseResult resResult = new ResponseResult();
        try {
            Map<String, String> result = service.setUserPassword(body.get("user_id").toString(),
                    body.get("old_password").toString(), body.get("new_password").toString());
            if (result.containsKey("msg")) {
                resResult.setStatus(1);
                resResult.setMessage(result.get("msg").toString());
            } else {
                resResult.setStatus(0);
                resResult.setMessage("密码修改成功");
                resResult.setData(result);
                response.setHeader("Authorization", result.get("token").toString());
            }
        } catch (Exception e) {
            resResult.setStatus(1);
            resResult.setMessage(e.getMessage());
        }
        return resResult;
    }

    @RequestMapping(value = "/reset_password")
    public ResponseResult resetPassword(@RequestBody Map<String, String> body, HttpServletResponse response) {
        ResponseResult resResult = new ResponseResult();
        try {
            String userId = "-1";
            String phoneNum = "-1";
            if (body.containsKey("user_id")){
                userId = body.get("user_id").toString();
            } else {
                if (body.containsKey("phone_num")){
                    phoneNum = body.get("phone_num").toString();
                } else {
                    resResult.setStatus(1);
                    resResult.setMessage("user_id和phone_num不能都为空！");
                    return resResult;
                }
            }
            Map<String, String> result = service.resetPassword(userId, phoneNum,
                    body.get("new_password").toString(), body.get("veri_code").toString());
            if (result.containsKey("error")) {
                resResult.setStatus(1);
                resResult.setMessage(result.get("error").toString());
            } else {
                resResult.setStatus(0);
                resResult.setMessage(result.get("msg").toString());
            }
        } catch (Exception e) {
            resResult.setStatus(1);
            resResult.setMessage(e.getMessage());
        }
        return resResult;
    }

    /*
   设置隐私信息
    */
    @RequestMapping(value = "/set_privacy_info")
    public ResponseResult setPrivacyInfo(@RequestBody Map<String, String> body) {
        ResponseResult resResult = new ResponseResult();
        try {
            String result = service.setPrivacyInfo(body);
            if (result.equals("0")) {
                resResult.setStatus(0);
                resResult.setMessage("保存成功");
            } else {
                resResult.setStatus(1);
                resResult.setMessage(result);
            }
        } catch (Exception e) {
            resResult.setStatus(1);
            resResult.setMessage(e.getMessage());
        }
        return resResult;
    }


    @RequestMapping(value = "/get_privacy_info")
    public ResponseResult getPrivacyInfo(@RequestBody Map<String, String> body) {
        ResponseResult resResult = new ResponseResult();
        try {
            Map<String, Object> result = service.getPrivacyInfo(Long.valueOf(body.get("user_id").toString()));
            if (!result.isEmpty()) {
                resResult.setStatus(0);
                resResult.setMessage("获取成功");
                resResult.setData(result);
            } else {
                resResult.setStatus(1);
                resResult.setMessage("获取失败");
            }
        } catch (Exception e) {
            resResult.setStatus(1);
            resResult.setMessage(e.getMessage());
        }
        return resResult;
    }
}
