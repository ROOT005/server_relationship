package com.relationship.service;

import com.google.gson.Gson;
import com.relationship.domain.VerificationCodeEntity;
import com.relationship.repository.mysql.VerificationCodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class VerificationCodeService {
    @Autowired
    VerificationCodeDao verificationCodeDao;

    private static String url = "http://106.ihuyi.com/webservice/sms.php?method=Submit";
    private static String apiId = "cf_57407966";
    private static String apiKey = "e4bfc480419da57b836ec07e2abdfe89";

    public Map<String, String> sendVerificationCode(String phoneNum){
        int code = (int)((Math.random() * 9 + 1) * 100000);
        String content = new String("您的验证码是：" + code + "。请不要把验证码泄露给其他人。");

        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //添加参数
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("account", apiId);
        params.add("password", apiKey);
        params.add("mobile", phoneNum);
        params.add("content", content);
        params.add("format", "json");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        //执行请求
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        //获取返回值
        String body = resp.getBody();
        Gson gson = new Gson();
        @SuppressWarnings("unchecked")
        Map<String, Object> resMap = gson.fromJson(body, Map.class);
        Map<String, String> hashMap = new LinkedHashMap<String, String>();
        if (String.valueOf(resMap.get("code")).equals("2.0")){
            VerificationCodeEntity entity = new VerificationCodeEntity();
            entity.setCode(String.valueOf(code));
            entity.setPhoneNum(phoneNum);
            verificationCodeDao.save(entity);
            hashMap.put("code", "0");
            hashMap.put("msg", resMap.get("msg").toString());
            return hashMap;
        } else {
            if(String.valueOf(resMap.get("code")).equals("0")){
                hashMap.put("code", "2");
            } else {
                hashMap.put("code", resMap.get("code").toString());
            }
            hashMap.put("msg", resMap.get("msg").toString());
            return hashMap;
        }
    }
}
