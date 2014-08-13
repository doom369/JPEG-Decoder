package com.ddumanskiy;

/**
 * JPEG header bytes. See http://www.digicamsoft.com/itu/itu-t81-36.html for more info
 * or http://en.wikipedia.org/wiki/JPEG
 *
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public final class Markers {

    //Start Of Image
    public static final short SOI = (short) 0xFFD8;

    //Start Of Frame (Baseline DCT)
    public static final short SOF0 = (short) 0xFFC0;

    public static final short SOF1 = (short) 0xFFC1;

    //Start Of Frame (Progressive DCT)
    public static final short SOF2 = (short) 0xFFC2;

    //Define Huffman Table(s)
    public static final short DHT = (short) 0xFFC4;

    //Define Quantization Table(s)
    public static final short DQT = (short) 0xFFDB;

    //Define Restart Interval
    public static final short DRI = (short) 0xFFDD;

    //Start Of Scan
    public static final short SOS = (short) 0xFFDA;

    //Restart
    public static final short RST0 = (short) 0xFFD0;
    public static final short RST1 = (short) 0xFFD1;
    public static final short RST2 = (short) 0xFFD2;
    public static final short RST3 = (short) 0xFFD3;
    public static final short RST4 = (short) 0xFFD4;
    public static final short RST5 = (short) 0xFFD5;
    public static final short RST6 = (short) 0xFFD6;
    public static final short RST7 = (short) 0xFFD7;

    //Application-specific
    public static final short APP0 = (short) 0xFFE0;
    public static final short APP1 = (short) 0xFFE1;
    public static final short APP2 = (short) 0xFFE2;
    public static final short APP3 = (short) 0xFFE3;
    public static final short APP4 = (short) 0xFFE4;
    public static final short APP5 = (short) 0xFFE5;
    public static final short APP6 = (short) 0xFFE6;
    public static final short APP7 = (short) 0xFFE7;
    public static final short APP8 = (short) 0xFFE8;
    public static final short APP9 = (short) 0xFFE9;
    public static final short APPA = (short) 0xFFEA;
    public static final short APPB = (short) 0xFFEB;
    public static final short APPC = (short) 0xFFEC;
    public static final short APPD = (short) 0xFFED;
    public static final short APPE = (short) 0xFFEE;
    public static final short APPF = (short) 0xFFEF;

    //Comment
    public static final short COM = (short) 0xFFFE;

    //End Of Image
    public static final short EOI = (short) 0xFFD9;
    
    
}
