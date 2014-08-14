package com.ddumanskiy.utils;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class BitUtil {

    //todo name not exactly true
    public static int first4Bits(int oneInt) {
        return oneInt >> 4;
    }

    //todo name not exactly true
    public static int last4Bits(int oneInt) {
        return oneInt & 0x0F;
    }

}
