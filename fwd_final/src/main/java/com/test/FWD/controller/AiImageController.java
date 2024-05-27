package com.test.FWD.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.FWD.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class AiImageController {
    @Autowired
    private ChatService chatService;

    @RequestMapping("/createImageCompletion")
    public String createImageCompletion(HttpServletRequest request, HttpServletResponse response) {
        return chatService.createImageCompletion(request, response,"1024*1024");
    }
}
