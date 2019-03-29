package com.relationship.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.relationship.utils.message.ResponseResult;
import com.relationship.utils.upload.Upload;

//文件上传服务
@Service
public class UploadService {
    //上传图片
    public ResponseResult uploadIdCardImage(MultipartFile image) {
	ResponseResult upload = new Upload().uploadIdCardImg(image);
	return upload;
    }


    public ResponseResult fileUpload(String type, MultipartFile file, MultipartFile file2) {
        ResponseResult result = new ResponseResult();
        if (type.equals("0")){  //上传身份证正反面
            result = new Upload().uploadIdCardImg(file);
        } else if (type.equals("1")) { //聊天图片
            result = new Upload().uploadIdCardImg(file);
        } else if (type.equals("2")) { //头像
            result = new Upload().uploadLogo(file);
        } else if (type.equals("3")) {
            result = new Upload().uploadIdCardImg(file);
        } else if (type.equals("4")) {
            result = new Upload().uploadIdCardImg(file);
        }
        return result;
    }
}
