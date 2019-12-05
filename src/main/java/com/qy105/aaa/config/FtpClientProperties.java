package com.qy105.aaa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "ftp.client")
public class FtpClientProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String encoding;
    private Integer connectTimeout;
    private String basePath;
    private String httpPath;
}
