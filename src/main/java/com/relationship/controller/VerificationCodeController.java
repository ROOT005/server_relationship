package com.relationship.controller;

import com.relationship.service.UserService;
import com.relationship.service.VerificationCodeService;
import com.relationship.utils.message.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService service;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/sendverificationcode")
    public ResponseResult sendVerificationCode(@RequestBody Map<String, Object> body) {
        ResponseResult resResult = new ResponseResult();
        try {
            String phoneNum = body.get("phone_num").toString();
            String isReg = body.get("is_reg").toString();
            if (!isReg.equals("1")) {
                if (!userService.checkPhoneNum(phoneNum)) {
                    resResult.setStatus(1);
                    resResult.setMessage("此手机号未注册！");
                    return resResult;
                }
            } else {
                if (userService.checkPhoneNum(phoneNum)) {
                    resResult.setStatus(1);
                    resResult.setMessage("此手机号已注册！");
                    return resResult;
                }
            }
            Map<String, String> hashMap = service.sendVerificationCode(phoneNum);
            if (hashMap.get("code").equals("0")){
                resResult.setStatus(0);
                resResult.setMessage(hashMap.get("msg"));
            } else {
                resResult.setStatus(1);
                resResult.setMessage(hashMap.get("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resResult.setStatus(1);
            resResult.setMessage(e.getMessage());
        }
        return resResult;
    }
}
