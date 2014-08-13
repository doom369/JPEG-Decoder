package com.ddumanskiy;

import com.ddumanskiy.segments.SOFSegment;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

import static com.ddumanskiy.utils.YUVconvertorUtil.convertYUVtoRGB;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class BlockMerger {

    private int width;
    private int height;
    private int factorV;
    private int factorH;
    private int componentCount;

    private int x;
    private int y;
    private int lastHeight;
    private int incrBlock;

    private BufferedImage bi;
    private DataBuffer image;

    public BlockMerger( int width, int height, SOFSegment sofSegment) {
        this.width = width;
        this.height = height;
        this.componentCount = sofSegment.getComponentCount();
        if (componentCount == 1) {
            this.factorV = 1;
            this.factorH = 1;
        } else {
            this.factorH = sofSegment.getMaxH();
            this.factorV = sofSegment.getMaxV();
        }
        bi  = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image = bi.getRaster().getDataBuffer();
    }

    /**
     * Put single MCU to image to correct position of byte buffer that is used in BufferedImage.
     */
    public void mergeMCUtoEndImage(MCUBlockHolder holder) {
        int mcuCounter = 0;
        while (mcuCounter < holder.yComponentsZZ.length){
            int offsetX = 0;
            int offsetY = 0;
            int cy = 0;

            if (x >= width) {
                x = 0;
                y += incrBlock;
            }

            for (int factorVIndex = 0; factorVIndex < factorV; factorVIndex++) {
                offsetX = 0;
                int cx = 0;

                for (int factorHIndex = 0; factorHIndex < factorH; factorHIndex++) {
                    int[][] blockY = holder.yComponentsZZ[mcuCounter % 4];

                    for (int yIndex = 0; yIndex < blockY.length; yIndex++) {
                        for (int xIndex = 0; xIndex < blockY[yIndex].length; xIndex++) {
                            if (x + xIndex < width && y + yIndex < height) {
                                int rColor = convertYUVtoRGB(blockY[yIndex][xIndex],
                                        componentCount == 1 ? 0 : holder.crComponentZZ[(yIndex + cy) / 2][(xIndex + cx) / 2],
                                        componentCount == 1 ? 0 : holder.cbComponentZZ[(yIndex + cy) / 2][(xIndex + cx) / 2]);
                                image.setElem((y + yIndex) * width + x + xIndex, rColor);
                            }
                        }
                    }

                    offsetX += blockY[0].length;
                    x += blockY[0].length;
                    offsetY = blockY.length;
                    mcuCounter++;
                    cx += 8;
                }
                y += offsetY;
                x -= offsetX;
                lastHeight += offsetY;
                cy  += 8;
            }
            y -= lastHeight;
            incrBlock = lastHeight;
            lastHeight = 0;
            x += offsetX;
        }
    }

    public BufferedImage getBi() {
        return bi;
    }
}
