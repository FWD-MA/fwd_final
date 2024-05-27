package com.test.FWD.controller;

import com.test.FWD.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AiPosterController {
    @Autowired
    private ChatService chatService;
    @RequestMapping("/createPosterCompletion")
    public String createPosterCompletion(HttpServletRequest request, HttpServletResponse response) {
        return chatService.createPosterCompletion(request, response);
    }



}
