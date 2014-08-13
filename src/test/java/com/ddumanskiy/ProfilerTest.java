package com.ddumanskiy;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This test is just for running within profiler.
 *
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 11:46 AM
 */
public class ProfilerTest {

    private static final int ITERATIONS = 100;

    @Test
    public void run100Times() throws IOException {
        Path testImagePath= Paths.get(new File("src/test/resources").getAbsolutePath(), "testImage.jpg");

        //required to be sure JIT will not kick off all code.
        int counter = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            BufferedImage image = JpegDecoder.decode(testImagePath);
            counter += image.getHeight();
        }
    }

}
