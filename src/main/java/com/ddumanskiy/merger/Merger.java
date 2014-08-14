package com.ddumanskiy.merger;

import com.ddumanskiy.MCUBlockHolder;

/**
 * User: ddumanskiy
 * Date: 8/14/2014
 * Time: 12:47 AM
 */
public abstract class Merger {

    int width;
    int height;
    int[] fullBlock;

    public Merger(int width, int height) {
        this.width = width;
        this.height = height;
        this.fullBlock = new int[width * height];
    }

    public abstract void merge(MCUBlockHolder holder);

    public int[] getFullBlock() {
        return fullBlock;
    }

}
