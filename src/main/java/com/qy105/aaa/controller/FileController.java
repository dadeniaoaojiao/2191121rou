package com.qy105.aaa.controller;

import com.qy105.aaa.service.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;



@Controller
public class FileController {
    @Autowired
    private FtpService ftpService;
    @PostMapping("/upload")
    public String upload(@RequestParam("uploadFile") MultipartFile file, HttpSession session, ModelMap modelMap) {

        Map<String, Object> resultMap = ftpService.uploadFile(file);
        if (!(Boolean) resultMap.get("result")) {
            // 说明上传失败！！需要跳转到错误页面
            return "error";
        } else {
            return "success";
        }
    }
}
