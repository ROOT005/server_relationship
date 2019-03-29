/****文件上传处理*******
 *
 * 2018-6-13 by 赵腾飞
 *
 * */
package com.relationship.utils.upload;

import com.relationship.utils.message.ResponseResult;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class Upload {
    private final String FILE_PATH = "./resources/upload/";

    public ResponseResult uploadIdCardImg(MultipartFile image) {
        //String contentType = image.getContentType();

        //生成唯一文件名,(时间戳+随机数).图片类型
        //String fileName = image.getOriginalFilename();
        Random random = new Random();
        String prefix = "/id_card_image/";
        String suffix = image.getContentType().split("/")[1].toLowerCase();
        if (!suffix.equals("jpeg")) {
            return new ResponseResult(1, "非法文件类型 !", null);
        }
        String fileName = "";
        try {
            fileName = DigestUtils.md5DigestAsHex(image.getBytes()) + "." + suffix;
            uploadFile(image.getBytes(), FILE_PATH + prefix, fileName);
        } catch (Exception e) {
            return new ResponseResult(1, "返回失败 !", null);
        }

        return new ResponseResult(0, "上传成功！", prefix + fileName);
    }

    public ResponseResult uploadLogo(MultipartFile image) {
        //String contentType = image.getContentType();

        //生成唯一文件名,(时间戳+随机数).图片类型
        //String fileName = image.getOriginalFilename();
        Random random = new Random();
        String prefix = "/logo/";
        String suffix = image.getContentType().split("/")[1].toLowerCase();
        if (!suffix.equals("jpeg")) {
            return new ResponseResult(1, "非法文件类型 !", null);
        }
        String fileName = "";
        try {
            fileName = DigestUtils.md5DigestAsHex(image.getBytes()) + "." + suffix;
            uploadFile(image.getBytes(), FILE_PATH + prefix, fileName);
        } catch (Exception e) {
            return new ResponseResult(1, "返回失败 !", null);
        }

        return new ResponseResult(0, "上传成功！", prefix + fileName);
    }

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
