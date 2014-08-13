package com.ddumanskiy.utils;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class YUVconvertorUtil {

    public static int convertYUVtoRGB(int y, int cr, int cb) {
        int r = (int) (y + 1.402 * cr) + 128;
        int g = (int) (y - 0.34414 * cb - 0.71414 * cr) + 128;
        int b = (int) (y + 1.772 * cb) + 128;

        r = r > 255 ? 255 : (r < 0 ? 0 : r);
        g = g > 255 ? 255 : (g < 0 ? 0 : g);
        b = b > 255 ? 255 : (b < 0 ? 0 : b);

        return  (0xff000000) | (r << 16) | (g << 8) | b;
    }

}
