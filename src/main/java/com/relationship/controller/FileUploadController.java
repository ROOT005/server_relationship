package com.relationship.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.relationship.service.UploadService;
import com.relationship.utils.message.ResponseResult;

@RestController
@RequestMapping("/api")
public class FileUploadController {
    
    private UploadService uploadService;
    
    public FileUploadController(UploadService uploadService) {
	this.uploadService = uploadService;
    }
    
    //身份信息上传
    @RequestMapping(value="/idcard_image_upload",  method=RequestMethod.POST)
    public ResponseResult idCardImageUpload(@RequestParam("image") MultipartFile image, @RequestParam("type") String type) {
	ResponseResult result = uploadService.uploadIdCardImage(image);
	String temp = type;
	return result;
    }


    @RequestMapping(value="/file_upload", method = RequestMethod.POST)
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, @RequestParam("type") String type,
                                     @RequestParam(value = "file2", required = false) MultipartFile file2) {
        ResponseResult result = uploadService.fileUpload(type, file, file2);
        return result;
    }
    
}
