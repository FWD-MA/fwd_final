package com.test.FWD.core.impl;

import com.test.FWD.annotation.PosterBackground;
import com.test.FWD.annotation.PosterFontCss;
import com.test.FWD.annotation.PosterImageCss;
import com.test.FWD.core.abst.Poster;
import com.test.FWD.core.decorators.BackgroundDecorator;
import com.test.FWD.core.decorators.ImageDecorator;
import com.test.FWD.core.decorators.TextDecorator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;


public class PosterDefaultImpl<E> implements com.test.FWD.core.impl.PosterTemplate<E> {


    @Override
    public Poster annotationDrawPoster(E content) throws IllegalAccessException{
        // 反射获取所有属性
        Field[] fields = content.getClass().getDeclaredFields();

        // 第一个注解为背景配置
        if (fields.length>0 && fields[0].getAnnotation(PosterBackground.class) != null){

            // 最后需要执行绘制方法的类
            Poster finalDraw;
            PosterBackground ann = fields[0].getAnnotation(PosterBackground.class);

            fields[0].setAccessible(true);
            Object o = fields[0].get(content);
            if (o instanceof BufferedImage){
                BufferedImage bg = (BufferedImage) o;
                finalDraw = new BackgroundDecorator().toBuilder()
                        .bgImage(bg)
                        .width(ann.width())
                        .height(ann.height())
                        .build();
            } else {
                throw new RuntimeException("背景注解标记的类型需要为BufferedImage, 并且不可为空.");
            }

            // 给背景绘制基本属性
            for (Field field: fields) {
                field.setAccessible(true);
                //获取属性值
                Object value = field.get(content);
                if (value instanceof String){
                    String str = (String)value;
                    PosterFontCss posterFontCss = field.getAnnotation(PosterFontCss.class);
                    finalDraw = drawTextImpl(finalDraw,str,posterFontCss);
                } else if (value instanceof BufferedImage){
                    BufferedImage image = (BufferedImage) value;
                    PosterImageCss posterImageCss = field.getAnnotation(PosterImageCss.class);
                    finalDraw = drawImageImpl(finalDraw,image,posterImageCss);
                }

            }
            return finalDraw;
        } else {
            throw new RuntimeException("绘制字段为空,或者第一个属性不是背景,并且没有标记背景注解");
        }

    }

    /**
     * 绘制文本装饰器
     * @param base 基础图片
     * @param text 要绘制的文本
     * @return poster
     */
    protected Poster drawTextImpl(Poster base, String text, PosterFontCss ann){

        if (ann==null){
            return base;
        }

        return new TextDecorator(base).toBuilder()
                .positionX(ann.position()[0])
                .positionY(ann.position()[1])
                .fontSize(ann.size())
                .canNewLine(ann.canNewLine()[0]==1)
                .width(ann.canNewLine()[1])
                .newLineLimit(ann.canNewLine()[2])
                .color(new Color(ann.color()[0],ann.color()[1],ann.color()[2]))
                .content(text)
                .build();
    }

    /**
     * 绘制图片装饰器
     * @param base 基础图片
     * @param image 要绘制的图片
     * @return poster
     */
    protected Poster drawImageImpl(Poster base, BufferedImage image, PosterImageCss ann){

        if (ann == null){
            return base;
        }

        return new ImageDecorator(base).toBuilder()
                .positionX(ann.position()[0])
                .positionY(ann.position()[1])
                .width(ann.width())
                .height(ann.height())
                .circle(ann.circle())
                .image(image)
                .build();
    }


}
