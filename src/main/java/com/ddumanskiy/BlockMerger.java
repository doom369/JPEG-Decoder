package com.ddumanskiy;

import com.ddumanskiy.segments.SOFSegment;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import static com.ddumanskiy.utils.YUVconvertorUtil.convertYUVtoRGB;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class BlockMerger {

    int width;
    int height;
    int factorV;
    int factorH;
    int componentCount;

    int x;
    int y;
    int lastHeight;
    int incrBlock;
    int mcuCounter;
    int cx;
    int cy;

    BufferedImage bi;
    WritableRaster raster;

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
       raster = bi.getRaster();
    }

    private int[] pixel = new int[1];
    public void generateRGBMatrix(MCUBlockHolder holder) {
        mcuCounter = 0;
        while (mcuCounter < holder.yComponents.length){
            int offsetX = 0;
            int offsetY = 0;
            cy = 0;

            if (x >= width) {
                x = 0;
                y += incrBlock;
            }

            for (int factorVIndex = 0; factorVIndex < factorV; factorVIndex++) {
                offsetX = 0;
                cx = 0;

                for (int factorHIndex = 0; factorHIndex < factorH; factorHIndex++) {
                    int[][] blockY = holder.yComponents[mcuCounter % 4];

                    for (int yIndex = 0; yIndex < blockY.length; yIndex++) {
                        for (int xIndex = 0; xIndex < blockY[yIndex].length; xIndex++) {
                            if (x + xIndex < width && y + yIndex < height) {

                                //int cIndex = (mcuCounter / 4);
                                int rColor = convertYUVtoRGB(blockY[yIndex][xIndex],
                                        componentCount == 1 ? 0 : holder.crComponent[(yIndex + cy) / 2][(xIndex + cx) / 2],
                                        componentCount == 1 ? 0 : holder.cbComponent[(yIndex + cy) / 2][(xIndex + cx) / 2]);
                                //bi.setRGB(x + xIndex, y + yIndex, rColor);
                                pixel[0] = rColor;
                                raster.setDataElements(x + xIndex, y + yIndex, pixel);
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
