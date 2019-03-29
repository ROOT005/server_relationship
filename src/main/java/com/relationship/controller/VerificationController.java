package com.relationship.controller;

import com.relationship.service.VerificationService;
import com.relationship.utils.message.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class VerificationController {
    
    private VerificationService verificationService;
    
    public VerificationController(VerificationService verificationService) {
	this.verificationService=verificationService;
    }
    //身份验证
    @RequestMapping(value="/set_identity",  method=RequestMethod.POST)
    public ResponseResult identityVerification(@RequestBody Map<String, Object> body){
        
	int result = verificationService.identityVerfication(body);
	
	if (result == 1) {
	    return new ResponseResult(0, "插入成功！", null);
	}
	return new ResponseResult(1, "插入失败！", null);
    }

    //获取身份认证
    @RequestMapping(value = "/get_identity", method = RequestMethod.POST)
    public ResponseResult getIdentity(@RequestBody Map<String, String> body) {
        Map<String, Object> result = verificationService.getIdentity(body.get("user_id").toString());
        return new ResponseResult(0, "查询成功", result);
    }
    
    //教育信息认证
    @RequestMapping(value="/education", method=RequestMethod.POST)
    public ResponseResult educationVerification(@RequestBody Map<String, Object> body) {
        int result = verificationService.educationVerfication(body);
        if (result == 1) {
            return new ResponseResult(0, "插入成功！", null);
        }
        return new ResponseResult(1, "插入失败！", null);
    }
}
