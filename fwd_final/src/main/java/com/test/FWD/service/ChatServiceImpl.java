package com.test.FWD.service;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.fastjson.JSONObject;

import com.google.common.annotations.VisibleForTesting;
import com.test.FWD.comm.SamplePoster;
import com.test.FWD.core.impl.PosterDefaultImpl;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;


@Service
public class ChatServiceImpl implements ChatService{


    public String test(HttpServletRequest request, HttpServletResponse response){

        String content = request.getParameter("content");
        return content;
    }
    @Override
    public String createImageCompletion(HttpServletRequest request, HttpServletResponse response,String size) {
        try{
            String labal = request.getParameter("labal");
            String prompt = request.getParameter("prompt");

            String selectedHoliday = request.getParameter("selectedHoliday");
            String selectedCountry = request.getParameter("selectedCountry");
            String targetObject = request.getParameter("targetObject");
            String style = request.getParameter("style");
            String otherRequirements = request.getParameter("otherRequirements");
            StringBuilder prompt1 = new StringBuilder();
            prompt1.append(selectedHoliday+"")
                    .append("," + selectedCountry)
                    .append("," + targetObject)
                    .append("," + style)
                    .append("," + otherRequirements);
            System.out.println(prompt1.toString());




            ImageSynthesis gen = new ImageSynthesis();
            ImageSynthesisParam param = ImageSynthesisParam.builder()
                            .model(ImageSynthesis.Models.WANX_V1)
                            .n(1)
                            .size(size)
                            .prompt(prompt+prompt1)
                            .apiKey("sk-eb07a7232b0e4dbaa7d1f4ceec13965b")
                            .build();
            ImageSynthesisResult result = gen.call(param);
            List <Map<String,String>> urlList = new ArrayList<>();
            urlList = result.getOutput().getResults();
            return urlList.get(0).get("url");
        }catch(ApiException|NoApiKeyException e){
            return e.getMessage();
        }

    }

    @Override
    public String createChatCompletion(HttpServletRequest request, HttpServletResponse response){
        try{
            String user = "user";
            String content = request.getParameter("otherRequirements");
            String selectedHoliday = request.getParameter("selectedHoliday");
            String selectedCountry = request.getParameter("selectedCountry");
            String targetObject = request.getParameter("targetObject");
            String style = request.getParameter("style");
            String otherRequirements = request.getParameter("otherRequirements");
            StringBuilder prompt = new StringBuilder();
            prompt.append((selectedHoliday == null || selectedHoliday == "")? "" :("我们在"+ selectedHoliday+"营销"+","))
                    .append( (selectedCountry == null || selectedCountry == "")? "":("面向的地区是" + selectedCountry+","))
                    .append( (targetObject == null || targetObject == "")? "" : ("目标对象是" + targetObject +","))
                    .append( (style == null || style == "")? "" : ( "风格是" + style+","))
                    .append( (otherRequirements == null||otherRequirements == "")? "" : "其他要求是" + otherRequirements);
            String labal = request.getParameter("labal");
            List<Message> messages = new ArrayList<>();
            Message systemMsg = Message.builder().role(user).content("你是富卫保险的营销助手，我需要你帮我生成一份文案来进行保险产品的推销，需要契合以下的一些关键词" ).build();
            messages.add(systemMsg);
            Message systemMsg2 = Message.builder().role(user).content(prompt == null ? " " :prompt.toString()).build();
            messages.add(systemMsg2);
            StringBuilder fileContentString = new StringBuilder();
            if (ServletFileUpload.isMultipartContent(request)){
                Part filePart1 = request.getPart("files[0]");
                InputStream fileContent = filePart1.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent));
                String line = null;

                while ((line = reader.readLine()) != null) {
                    // 处理读取的每一行
                    fileContentString.append(line);
                }
            }
            if(!fileContentString.toString().isEmpty()){
                String fileReadContent = "帮我详细解释一下这篇文案:"+fileContentString.toString();
                Message userMsg = Message.builder().role(user).content(fileReadContent).build();
                messages.add(userMsg);
            }

            Generation gen = new Generation();
            System.out.println(messages.toString());
            GenerationParam param = GenerationParam.builder()
                    .model("qwen-turbo")
                    .messages(messages)
                    .apiKey("sk-eb07a7232b0e4dbaa7d1f4ceec13965b")
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .topP(0.8)
                    .build();
            GenerationResult result = gen.call(param);
            System.out.println(result.getOutput().getChoices().get(0).getMessage().getContent());
            String context = result.getOutput().getChoices().get(0).getMessage().getContent();
            return context;
        }catch (ApiException | NoApiKeyException | InputRequiredException e){
            System.out.println(e.getMessage());
            return e.getMessage();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String createPosterCompletion(HttpServletRequest request, HttpServletResponse response){
        try{
            String content = request.getParameter("content");
            String selectedHoliday = request.getParameter("selectedHoliday");
            String selectedCountry = request.getParameter("selectedCountry");
            String targetObject = request.getParameter("targetObject");
            String style = request.getParameter("style");
            String otherRequirements = request.getParameter("otherRequirements");
            String solgan = sloganGen(selectedHoliday,selectedCountry,targetObject);
            URL imageUrl = new URL(createImageCompletion(request,response,"720*1280"));
            BufferedImage background = ImageIO.read(imageUrl);
            File codeImage = new File(ResourceLoader.class.getResource("/static/code.png").getFile());
            BufferedImage code = ImageIO.read(codeImage);
            //BufferedImage logo = ImageIO.read();
            int i = 0;
            for (; i < solgan.length(); i++) {
                if (solgan.charAt(i)=='，') break;
            }
            SamplePoster poster = SamplePoster.builder()
                    .backgroundImage(background)
                    .head(null)
                    .nickName(solgan.substring(i+1,solgan.length()-1))
                    .slogan(solgan.substring(1,i))
                    .mainImage(code)
                    .build();
            PosterDefaultImpl<SamplePoster> impl = new PosterDefaultImpl<>();
            BufferedImage AiPoster = impl.annotationDrawPoster(poster).draw(null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(AiPoster,"png",outputStream);
            //ImageIO.write(AiPoster,"png",new FileOutputStream("annTest.png"));

            String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
            ImageIO.write(AiPoster,"png",new FileOutputStream(path+"/PosterTest.png"));
            final String URL = "http://localhost:8080/";
            String url_path = File.separator+"PosterTest.png";

            String base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            return URL+url_path;


        }catch(MalformedURLException e){

            return null;
        }catch(IOException e) {
            return null;
        }catch(IllegalAccessException e) {
            return null;
        }

    }

    public String sloganGen(String selectedHoliday,String selectedCountry,String targetObject) {
        try{
            StringBuilder prompt = new StringBuilder();
            prompt.append("请生成一个12字以内在"+selectedCountry).append("的"+selectedHoliday+",").append("面对"+targetObject).append("的保险宣传标语");
            Generation gen = new Generation();
            QwenParam param = QwenParam.builder()
                    .model("qwen-turbo")
                    .apiKey("sk-eb07a7232b0e4dbaa7d1f4ceec13965b")
                    .prompt(prompt.toString())
                    .topP(0.8).build();
            GenerationResult result = gen.call(param);
            System.out.println(prompt.toString());
            return result.getOutput().getText();}
        catch(ApiException | NoApiKeyException | InputRequiredException e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
}
