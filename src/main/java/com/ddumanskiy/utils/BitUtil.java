package com.ddumanskiy.utils;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class BitUtil {

    public static int bitSetToInt(byte[] bits, int from, int count) {
        int value = 0;
        int diff = count - 1;
        for (int i = 0; i < count; i++) {
            if (getBit(bits, from + i) == 128) {
                value |= 1 << diff - i;
            }
        }
        return value;
    }

    //return 0 or 1
    //public static int getBit(byte[] bits, int pos) {
    //    return (bits[pos / 8] >> (7 - (pos % 8))) & 1;
    //}

    //returns 128 or 0
    public static int getBit(byte[] bits, int pos) {
        return (bits[pos / 8] << (pos % 8)) & 128;
    }

    //todo name not exactly true
    public static int first4Bits(int oneInt) {
        return oneInt >> 4;
    }

    //todo name not exactly true
    public static int last4Bits(int oneInt) {
        return oneInt & 0x0F;
    }

}
