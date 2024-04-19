package com.fuswx.spzx.manager.controller;

import com.fuswx.spzx.manager.service.FileUploadService;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Resource
    private FileUploadService fileUploadService;

    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestParam("file") MultipartFile file){
        //1、获取上传的文件
        //2、调用service的方法上传，返回minio路径
        String url = fileUploadService.upload(file);
        return Result.build(url, ResultCodeEnum.SUCCESS);
    }
}
