package com.ddumanskiy.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class JpegInputStream implements Closeable {

    private InputStream is;

    public JpegInputStream(InputStream is) {
        this.is = is;
    }

    //reads 2 bytes
    public int readShort() throws IOException {
        int byte0 = is.read();
        int byte1 = is.read();
        return (byte0 << 8) | byte1;
    }

    /**
     * real size is 2 bytes -2 (where 2 is length of 2 bytes of length field itself)
     */
    public int readSize() throws IOException {
        int byte0 = is.read();
        int byte1 = is.read();
        return ((byte0 << 8) | byte1) - 2;
    }

    public int read() throws IOException {
        return is.read();
    }

    public void skip(long n) throws IOException {
        is.skip(n);
    }

    @Override
    public void close() throws IOException {
        is.close();
    }

    public InputStream getIs() {
        return is;
    }
}
