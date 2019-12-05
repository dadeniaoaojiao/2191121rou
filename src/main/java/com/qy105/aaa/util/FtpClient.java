package com.qy105.aaa.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

public class FtpClient {

    public static Boolean uploadFile(String host, int port, String userName, String password, String basePath, String filePath, String fileName, InputStream input) throws IOException {

        FTPClient ftpClient = new FTPClient();
        //1,连接ftp服务
        ftpClient.connect(host, port);
        //2, 登录ftp
        ftpClient.login(userName, password);
        //230表示登录成功，
        int replyCode = ftpClient.getReplyCode();
        //3, 此方法200到300之间都返回true,表示连接和登录成功，返回false表示登录失败
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            ftpClient.disconnect();
            return false;
        }

        String tmpPath = "";
        //4, 检测目标目录是否存在，如果返回true不需要创建目录，否正需要创建目录
        //filePath
        String storePath = basePath + "/" + filePath;
        if (!ftpClient.changeWorkingDirectory(storePath)) {
            //["2019","11","25"]
            String[] split = filePath.split("/");
            tmpPath = basePath;
            for (String dir : split) {
                if (null == dir || "".equals(dir)) {
                    continue;
                }
                tmpPath += "/" + dir;
                //检测目录是否存在
                if (!ftpClient.changeWorkingDirectory(tmpPath)) {
                    //makeDirectory是创建目录
                    if (!ftpClient.makeDirectory(tmpPath)) {
                        return false;
                    }
                }

            }
            if (!ftpClient.changeWorkingDirectory(tmpPath)){

            }

        }
        //5, 把文件以字符流的形式上传
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        //6,真正的上传文件，true表示上传成功，false表示上传失败
        if (!ftpClient.storeFile(fileName, input)) {
            return false;
        }
        //7,关闭输入流
        input.close();
        //退出ftp登录
        ftpClient.logout();

        ftpClient.disconnect();
        return true;
    }
}
