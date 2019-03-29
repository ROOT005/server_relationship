package com.relationship.service;

import com.relationship.repository.mysql.VerifacationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VerificationService {
    
    private  final VerifacationRepository verifacationRepository;
    
    public VerificationService(VerifacationRepository verifacationRepository) {
	this.verifacationRepository = verifacationRepository;
    }
    
    //身份验证
    public int identityVerfication(Map<String, Object> body) {
        
        String idImage = body.get("front_image").toString()+";"+body.get("back_image").toString();
        Long id = Long.parseLong(body.get("user_id").toString());
        String phoneNum = body.get("phone_num").toString();
        String name = body.get("name").toString();
        String idNum = body.get("id_num").toString();
        String cityId = body.get("city_id").toString();
        byte sex = Byte.valueOf(body.get("sex").toString());

	    return verifacationRepository.updateIdNum(id, idNum, name, idImage, cityId, sex);
    }

    public Map<String, Object> getIdentity(String userId) {
        List<Map<String, Object>> data = verifacationRepository.findbyId(Long.valueOf(userId));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("name", data.get(0).get("name"));
        result.put("id_num", data.get(0).get("idNum"));
        result.put("sex", data.get(0).get("sex")); //男0 女1
        result.put("city_id", data.get(0).get("cityId"));
        result.put("phone_num", data.get(0).get("phoneNum"));
        if (data.get(0).get("idImage") == null){
            result.put("front_image", null);
            result.put("back_image", null);
        } else {
            String[] idImageArray = data.get(0).get("idImage").toString().split(";");
            result.put("front_image", idImageArray[0]);
            result.put("back_image", idImageArray[1]);
        }
        result.put("id_num_verify", data.get(0).get("idNumVerify"));
        return result;
    }
    
    //教育信息验证
    public int educationVerfication(Map<String, Object> body){
        Long id = Long.parseLong(body.get("user_id").toString());
        Short verifyOrigin = Short.parseShort(body.get("verify_origin").toString());
        
        return verifacationRepository.updateEducationVerifyOrigin(id, verifyOrigin);
    }
}
