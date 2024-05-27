package com.test.FWD.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.FWD.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//ai读产品
@RestController
public class AiChatController {
    @Autowired
    private ChatService chatService;

    @RequestMapping("/createChatCompletion")
    public String createChatCompletion(HttpServletRequest request, HttpServletResponse response) {
        return chatService.createChatCompletion(request,response);
    }



    //暂时不知道怎么设计，不是特别懂这个部分的流程

}
