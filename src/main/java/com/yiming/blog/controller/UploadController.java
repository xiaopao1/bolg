package com.yiming.blog.controller;

import com.qiniu.common.QiniuException;
import com.yiming.blog.utils.QiniuUtils;
import com.yiming.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;
    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        byte[] bytes = file.getBytes();
        boolean upload = qiniuUtils.upload(file, originalFilename);
        if (upload) {
        return Result.success(QiniuUtils.url + originalFilename);
        }
        return Result.fail(20001,"upload failed");
    }
}
