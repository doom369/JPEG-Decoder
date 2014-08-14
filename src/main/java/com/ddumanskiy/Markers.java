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
    public static final int SOI =  0xFFD8;

    //Start Of Frame (Baseline DCT)
    public static final int SOF0 =  0xFFC0;

    public static final int SOF1 =  0xFFC1;

    //Start Of Frame (Progressive DCT)
    public static final int SOF2 =  0xFFC2;

    //Define Huffman Table(s)
    public static final int DHT =  0xFFC4;

    //Define Quantization Table(s)
    public static final int DQT =  0xFFDB;

    //Define Restart Interval
    public static final int DRI =  0xFFDD;

    //Start Of Scan
    public static final int SOS =  0xFFDA;

    //Restart
    public static final int RST0 =  0xFFD0;
    public static final int RST1 =  0xFFD1;
    public static final int RST2 =  0xFFD2;
    public static final int RST3 =  0xFFD3;
    public static final int RST4 =  0xFFD4;
    public static final int RST5 =  0xFFD5;
    public static final int RST6 =  0xFFD6;
    public static final int RST7 =  0xFFD7;

    //Application-specific
    public static final int APP0 =  0xFFE0;
    public static final int APP1 =  0xFFE1;
    public static final int APP2 =  0xFFE2;
    public static final int APP3 =  0xFFE3;
    public static final int APP4 =  0xFFE4;
    public static final int APP5 =  0xFFE5;
    public static final int APP6 =  0xFFE6;
    public static final int APP7 =  0xFFE7;
    public static final int APP8 =  0xFFE8;
    public static final int APP9 =  0xFFE9;
    public static final int APPA =  0xFFEA;
    public static final int APPB =  0xFFEB;
    public static final int APPC =  0xFFEC;
    public static final int APPD =  0xFFED;
    public static final int APPE =  0xFFEE;
    public static final int APPF =  0xFFEF;

    //Comment
    public static final int COM =  0xFFFE;

    //End Of Image
    public static final int EOI =  0xFFD9;
    
    
}
