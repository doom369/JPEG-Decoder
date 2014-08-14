package com.ddumanskiy;

import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;


/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 20)
@CompilerControl(CompilerControl.Mode.INLINE)
public class PeformanceTest {

    @Benchmark
    public BufferedImage colorImage16x16_ImageIO() throws IOException {
        File file = new File(Paths.get(new File("src/test/resources").getAbsolutePath(), "google.jpg").toString());
        return ImageIO.read(file);
    }

    @Benchmark
    public BufferedImage colorImage887x707_ImageIO() throws IOException {
        File file = new File(Paths.get(new File("src/test/resources").getAbsolutePath(), "testImage.jpg").toString());
        return ImageIO.read(file);
    }

    @Benchmark
    public BufferedImage grayImage800x533_ImageIO() throws IOException {
        File file = new File(Paths.get(new File("src/test/resources").getAbsolutePath(), "test.jpg").toString());
        return ImageIO.read(file);
    }

    @Benchmark
    public BufferedImage colorImage887x707_Apache() throws Exception {
        File file = new File(Paths.get(new File("src/test/resources").getAbsolutePath(), "testImage.jpg").toString());
        org.apache.commons.imaging.formats.jpeg.decoder.JpegDecoder decoder = new org.apache.commons.imaging.formats.jpeg.decoder.JpegDecoder();
        return decoder.decode(new ByteSourceFile(file));
    }

    @Benchmark
    public BufferedImage colorImage887x707_JpegDecoder() throws IOException {
        Path testImagePath= Paths.get(new File("src/test/resources").getAbsolutePath(), "testImage.jpg");
        return JpegDecoder.decode(testImagePath);
    }

    @Benchmark
    public BufferedImage colorImage16x16_JpegDecoder() throws IOException {
        Path testImagePath = Paths.get(new File("src/test/resources").getAbsolutePath(), "google.jpg");
        return JpegDecoder.decode(testImagePath);
    }

    @Benchmark
    public BufferedImage grayImage800x533_JpegDecoder() throws IOException {
        Path testImagePath= Paths.get(new File("src/test/resources").getAbsolutePath(), "test.jpg");
        return JpegDecoder.decode(testImagePath);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().build();

        new Runner(opt).run();
    }

}
