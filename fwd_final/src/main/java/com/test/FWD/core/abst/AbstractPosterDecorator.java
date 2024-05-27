package com.test.FWD.core.abst;

import lombok.AllArgsConstructor;
import lombok.Data;

/**

 */
@Data
@AllArgsConstructor
public abstract class AbstractPosterDecorator implements com.test.FWD.core.abst.Poster {

    /**
     * 海报对象
     */
    protected com.test.FWD.core.abst.Poster poster;

    protected int positionX;

    protected int positionY;

    protected int width;

    protected int height;

    public AbstractPosterDecorator(com.test.FWD.core.abst.Poster poster){
        this.poster = poster;
    }
}
