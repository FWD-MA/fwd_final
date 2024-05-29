package com.test.FWD.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.FWD.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;



@RestController
public class AiImageController {
    @Autowired
    private ChatService chatService;

    @RequestMapping("/createImageCompletion")
    public String createImageCompletion(HttpServletRequest request, HttpServletResponse response) {
        return chatService.createImageCompletion(request, response,"1024*1024",null);
    }

    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        String fileName = "PosterTest.png";
        if (fileName != null) {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) path = new File("");
            System.out.println("path:"+path.getAbsolutePath());

            //如果上传目录为/static/File/，则可以如下获取：
            File upload = new File(path.getAbsolutePath(),"static");
            if(!upload.exists()) upload.mkdirs();
            String realPath=upload.getAbsolutePath();
            System.out.println(realPath);
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" +  fileName);// 设置文件名,默认同名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }




}
