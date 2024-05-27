package com.test.FWD.core.abst;

import java.awt.image.BufferedImage;

/**
 * 默认空白的 poster\
 */
public abstract class AbstractDefaultPoster implements com.test.FWD.core.abst.Poster {

    @Override
    public BufferedImage draw(BufferedImage image) {
        return image;
    }

}
