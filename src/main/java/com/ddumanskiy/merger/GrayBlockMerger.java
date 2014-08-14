package com.ddumanskiy.merger;

import com.ddumanskiy.MCUBlockHolder;

import static com.ddumanskiy.utils.YUV.toGray;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 1:33 PM
 */
public class GrayBlockMerger extends Merger {

    private int x;
    private int y;

    public GrayBlockMerger(int width, int height) {
        super(width, height);
    }

    /**
     * Put single MCU to image to correct position of byte buffer that is used in BufferedImage.
     */
    @Override
    public void merge(MCUBlockHolder holder) {
        for (int[][] blockY : holder.yComponentsZZ){

            if (x >= width) {
                x = 0;
                y += 8;
            }

            for (int yIndex = 0; yIndex < 8; yIndex++) {
                for (int xIndex = 0; xIndex < 8; xIndex++) {
                    if (x + xIndex < width && y + yIndex < height) {
                         fullBlock[(y + yIndex) * width + x + xIndex] = toGray(blockY[yIndex][xIndex]);
                    }
                }
            }

            x += 8;
        }
    }
}
