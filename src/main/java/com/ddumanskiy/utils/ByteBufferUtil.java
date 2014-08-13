package com.ddumanskiy.utils;

import java.nio.ByteBuffer;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class ByteBufferUtil {

    public static byte[] readSizeAndData(ByteBuffer bb) {
        int size = Short.toUnsignedInt(bb.getShort());
        //System.out.println("Size : " + size);
        byte[] markerData = new byte[size - 2];
        bb.get(markerData);
        return markerData;
    }

    public static ByteBuffer readSizeAndDataAndWrap(ByteBuffer bb) {
        return ByteBuffer.wrap(readSizeAndData(bb));
    }

}
