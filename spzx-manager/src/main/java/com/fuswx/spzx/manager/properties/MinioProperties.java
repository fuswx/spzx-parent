package com.fuswx.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "spzx.minio")
@Component
public class MinioProperties {
    private String endpointUrl;
    private String accessKey;
    private String secureKey;
    private String bucketName;
}
