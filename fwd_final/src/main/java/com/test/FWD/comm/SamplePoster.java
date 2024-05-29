package com.test.FWD.comm;

import com.test.FWD.annotation.PosterBackground;
import com.test.FWD.annotation.PosterFontCss;
import com.test.FWD.annotation.PosterImageCss;
import com.test.FWD.core.abst.AbstractDefaultPoster;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import java.awt.image.BufferedImage;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class SamplePoster extends AbstractDefaultPoster {

    /**
     * 背景图
     */
    @PosterBackground(width = 720,height = 1280)
    private BufferedImage backgroundImage;

    /**
     * 头像
     */
    @PosterImageCss(position = {27,27},width = 36, height = 36, circle = true)
    private BufferedImage head;

    /**
     * 昵称
     */
    @PosterFontCss(position = {50,1140}, size = 40,color = {255,255,255})
    private String nickName;

    /**
     * 广告语
     */
    @PosterFontCss(position = {50,1034},center = true, size = 60, color = {255,255,255}, canNewLine={0,200,1})
    private String slogan;

    /**
     * 主图
     */
    @PosterImageCss(position = {490,900},width = 180,height = 180)
    private BufferedImage mainImage;

    @Tolerate
    public SamplePoster() {}
}
