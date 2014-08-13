package com.ddumanskiy;

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
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 20)
public class PeformanceTest {

    @Benchmark
    public void testPerformance() throws IOException {
        Path testImagePath= Paths.get(new File("src/test/resources").getAbsolutePath(), "testImage.jpg");
        BufferedImage bi = JpegDecoder.decode(testImagePath);
    }

    //@Benchmark
    public void testPerfomanceImageIO() throws IOException {
        File file = new File(Paths.get(new File("src/test/resources").getAbsolutePath(), "test.jpg").toString());
        BufferedImage bi = ImageIO.read(file);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().build();

        new Runner(opt).run();
    }

}
