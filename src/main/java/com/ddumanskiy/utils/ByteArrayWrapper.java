package com.ddumanskiy.utils;

import java.util.Arrays;

/**
 * The only purpose of this class is to avoid unnecessary byte buffer cloning. See SOSSegment.readImageData().
 * Initially I was using ByteArrayOutputStream. But it makes copy of byte array when ByteArrayOutputStream.toByteArray();
 * So this class decreases required app. footprint.
 *
 * So in other words - buf array contains only required "working" bytes without any control pair bytes like "0xFFD9", "0xFF00"
 *
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 13:23 AM
 */
public class ByteArrayWrapper {

    private final byte[] buf;

    //length of buf without 0xFFD9 and 0xFF00 bytes.
    private int count;

    /**
     * Size of jpeg image data. It includes all bytes like 0xFFD9 and 0xFF00;
     *
     * @param size - expected size of buffer
     */
    public ByteArrayWrapper(int size) {
        this.buf = new byte[size];
        this.count = 0;
    }


    public void write(int b) {
        buf[count++] = (byte) b;
    }

    public byte[] getBuf() {
        return buf;
    }

    public int getLength() {
        return count;
    }

    public byte[] toByteArray() {
        return Arrays.copyOf(buf, count);
    }
}
