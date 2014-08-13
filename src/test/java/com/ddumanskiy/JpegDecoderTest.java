package com.ddumanskiy;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class JpegDecoderTest {

    final String path = new File("src/test/resources").getAbsolutePath();

    @Test
    public void testInit2TestImage() throws IOException {
        BufferedImage bi = JpegDecoder.decode(Paths.get(path, "testImage2.jpg"));
        saveImage(path + "/result_testImage2.png", "png", bi);
    }

    @Test
    public void testInitTestImage() throws IOException {
        BufferedImage bi = JpegDecoder.decode(Paths.get(path, "testImage.jpg"));
        saveImage(path + "/result_testImage.png", "png", bi);
    }

    @Test
    public void test2ColorsDecodeTestImage() throws IOException {
        BufferedImage bi = JpegDecoder.decode(Paths.get(path, "test.jpg"));
        saveImage(path + "/result_test.png", "png", bi);
    }

    @Test
    public void testGoogle9DecodeTestImage() throws IOException {
        BufferedImage bi = JpegDecoder.decode(Paths.get(path, "google9.jpg"));
        saveImage(path + "/result_google9.png", "png", bi);
    }

    @Test
    public void testGoogleDecodeTestImage() throws IOException {
        BufferedImage bi = JpegDecoder.decode(Paths.get(path, "google.jpg"));
        saveImage(path + "/result_google.png", "png", bi);
    }

    public static void saveImage(String fullFilePath, String fileFormat, BufferedImage image) throws IOException {
        File imageFile = new File(fullFilePath);
        ImageIO.write(image, fileFormat, imageFile);
    }


}
