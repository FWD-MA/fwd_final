package com.test.FWD.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Service
public interface ChatService {
    String createChatCompletion(HttpServletRequest request, HttpServletResponse response);
    String createImageCompletion(HttpServletRequest request, HttpServletResponse response,String size,String image);
    String createPosterCompletion(HttpServletRequest request, HttpServletResponse response);
    String sloganGen(String selectedHoliday,String selectedCountry,String targetObject);

//    String chatdetail(HttpServletRequest request, HttpServletResponse response);
}
