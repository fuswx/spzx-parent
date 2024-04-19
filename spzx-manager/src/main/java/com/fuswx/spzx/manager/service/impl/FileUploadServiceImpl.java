package com.fuswx.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.fuswx.spzx.common.exception.FuswxException;
import com.fuswx.spzx.manager.properties.MinioProperties;
import com.fuswx.spzx.manager.service.FileUploadService;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import io.minio.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Resource
    private MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file) {
        try {
            //创建MinioClient对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getEndpointUrl())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecureKey())
                    .build();
            //创建bucket
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            }else {
                System.out.println("Bucket '" + minioProperties.getBucketName() + "' 已经存在");
            }

            //获取上传的文件名称
            //优化1：每个上传文件名称是唯一的  uuid生成文件名称
            //优化2：根据当前日期对上传文件进行分组 20230910
            //20230910/saddasdasdasddd-01.jpg
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String fileName = dateDir + "/" + uuid + "-" + file.getOriginalFilename();
            //文件上传
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());

            //获取上传文件在minio的路径
            //http://127.0.0.1:9000/spzx-bucket/Uninstall_Tool_v3.5.6.5591_破解版单文件.exe
            return minioProperties.getEndpointUrl() + "/" +minioProperties.getBucketName() + "/" + fileName;
        } catch (Exception e){
            e.printStackTrace();
            throw new FuswxException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
