package com.qy105.aaa.service;

import com.qy105.aaa.config.FtpClientProperties;
import com.qy105.aaa.util.FileNameUtil;
import com.qy105.aaa.util.FtpClient;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class FtpService {

    private static Logger logger = LoggerFactory.getLogger(FtpService.class);

    @Autowired
    private FtpClientProperties ftpClientProperties;

    public Map<String, Object> uploadFile(MultipartFile file) {

        Map<String, Object> resultMap = new HashMap<>(2);
        //获取文件原始名
        String oldFileName = file.getOriginalFilename();
        //获取原始文件后缀
        String substring = oldFileName.substring(oldFileName.lastIndexOf("."));

        //生成一个新的文件名
        String newFileName = FileNameUtil.getNewFileName() + substring;
        //创建文件目录
        String filePath = new DateTime().toString("yyyy/MM/dd");

        //使用工具类进行ftp上传
        try {
            Boolean aBoolean = FtpClient.uploadFile(ftpClientProperties.getHost(), ftpClientProperties.getPort(),
                    ftpClientProperties.getUsername(), ftpClientProperties.getPassword(),
                    ftpClientProperties.getBasePath(), filePath, newFileName, file.getInputStream());
            resultMap.put("result", aBoolean);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return resultMap;
    }

}
