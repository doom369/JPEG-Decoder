package com.ddumanskiy;

import com.ddumanskiy.huffman.HuffmanDecoder;
import com.ddumanskiy.segments.*;
import com.ddumanskiy.utils.ArraysUtil;
import com.ddumanskiy.utils.DCT;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.ddumanskiy.Markers.*;
import static com.ddumanskiy.utils.ArraysUtil.multiply;
import static com.ddumanskiy.utils.ByteBufferUtil.readSizeAndData;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class JpegDecoder {

    public static BufferedImage decode(Path jpegImageFilePath) throws IOException {
        DCT dct = new DCT();
        byte[] allBytes = Files.readAllBytes(jpegImageFilePath);
        SegmentHolder segmentHolder = readAllSegments(allBytes);


        HuffmanDecoder huffmanDecoder = new HuffmanDecoder(
                segmentHolder.sosSegment.getImageData(),
                segmentHolder.dhtSegment.getDcTables(),
                segmentHolder.dhtSegment.getAcTables(),
                segmentHolder.sofSegment.getComponents(),
                segmentHolder.sosSegment.getComponents()
        );

        MCUBlockHolder holder;
        BlockMerger merger = new BlockMerger(segmentHolder.sofSegment.getWidth(), segmentHolder.sofSegment.getHeight(), segmentHolder.sofSegment);
        try {
            while (true) {
                holder = huffmanDecoder.decode();

                multiplyAll(segmentHolder.dqtSegment, holder, segmentHolder.sofSegment.getComponentCount());

                fillInZigZagOrder(holder, segmentHolder.sofSegment.getComponentCount());

                inverseDCTAll(dct, holder, segmentHolder.sofSegment.getComponentCount());

                merger.generateRGBMatrix(holder);
            }
        } catch (IllegalStateException e) {
            //end
        }

        return merger.getBi();

    }

    private static void fillInZigZagOrder(MCUBlockHolder holder, int compNum) {
        for (int i = 0; i < 4; i++) {
            ArraysUtil.fillInZigZagOrder(holder.yComponentsZZ[i], holder.yComponents[i]);
        }
        if (compNum == 3) {
            ArraysUtil.fillInZigZagOrder(holder.cbComponentZZ, holder.cbComponent);
            ArraysUtil.fillInZigZagOrder(holder.crComponentZZ, holder.crComponent);
        }
    }

    private static void inverseDCTAll(DCT dct, MCUBlockHolder holder, int compNum) {
        dct.inverseDCT(holder.yComponentsZZ);
        if (compNum == 3) {
            dct.inverseDCT(holder.cbComponentZZ);
            dct.inverseDCT(holder.crComponentZZ);
        }
    }

    private static void multiplyAll(DQTSegment dqtSegment, MCUBlockHolder holder, int compNum) {
        multiply(dqtSegment.getDqtTables()[0], holder.yComponents);
        if (compNum == 3) {
            multiply(dqtSegment.getDqtTables()[1], holder.cbComponent);
            multiply(dqtSegment.getDqtTables()[1], holder.crComponent);
        }
    }

    /**
     * Read all segments from a file and creates appropriate objects.
     *
     * @param allBytes - image file as bytes array.
     * @throws IOException
     */
    private static SegmentHolder readAllSegments(byte[] allBytes) throws IOException {
        SegmentHolder segmentHolder = new SegmentHolder();

        ByteBuffer buf = ByteBuffer.wrap(allBytes);

        while (buf.hasRemaining()) {
            short marker = buf.getShort();

            switch (marker) {
                case SOI :
                    //System.out.println("SOI");
                    break;

                case SOF0 :
                    //System.out.println("SOF0");
                    segmentHolder.sofSegment = SOFSegment.decode(buf);
                    break;

                case SOF1 :
                case SOF2 :
                    //System.out.println("SOF1");
                    readSizeAndData(buf);
                    break;

                case DHT :
                    //System.out.println("DHT");
                    DHTTable dhtTable = DHTSegment.decode(buf);
                    segmentHolder.dhtSegment.add(dhtTable);
                    break;

                case DQT :
                    //System.out.println("DQT");
                    DQTTable dqtTable = DQTSegment.decode(buf);
                    segmentHolder.dqtSegment.add(dqtTable);
                    break;

                case DRI :
                    //System.out.println("DRI");
                    readSizeAndData(buf);
                    break;

                case SOS :
                    //System.out.println("SOS");
                    segmentHolder.sosSegment = SOSSegment.decode(buf);
                    break;

               case COM :
                    //System.out.println("COM");
                    readSizeAndData(buf);
                    break;

                case EOI :
                    //System.out.println("EOI");
                    break;

                case RST0 :
                case RST1 :
                case RST2 :
                case RST3 :
                case RST4 :
                case RST5 :
                case RST6 :
                case RST7 :
                    readSizeAndData(buf);
                    break;

                case APP0 :
                case APP1 :
                case APP2 :
                case APP3 :
                case APP4 :
                case APP5 :
                case APP6 :
                case APP7 :
                case APP8 :
                case APP9 :
                case APPA :
                case APPB :
                case APPC :
                case APPD :
                case APPE :
                case APPF :
                    readSizeAndData(buf);
                    break;
            }
        }

        return segmentHolder;
    }

    /*
    public static BufferedImage makeImageFromRGBMatrix(int[][] rgbMatrix, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bi.setRGB(x, y, rgbMatrix[x][y]);
            }
        }
        return bi;
    }
    */


}