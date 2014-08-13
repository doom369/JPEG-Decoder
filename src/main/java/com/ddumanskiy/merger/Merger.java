package com.ddumanskiy.merger;

import com.ddumanskiy.MCUBlockHolder;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

/**
 * User: ddumanskiy
 * Date: 8/14/2014
 * Time: 12:47 AM
 */
public abstract class Merger {

    int width;
    int height;
    BufferedImage bi;
    DataBuffer image;

    public Merger(int width, int height) {
        this.width = width;
        this.height = height;
        bi  = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image = bi.getRaster().getDataBuffer();
    }

    public abstract void merge(MCUBlockHolder holder);

    public BufferedImage getBi() {
        return bi;
    }

}
