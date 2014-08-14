package com.ddumanskiy;

import com.ddumanskiy.huffman.HuffmanDecoder;
import com.ddumanskiy.merger.ColorBlockMerger;
import com.ddumanskiy.merger.GrayBlockMerger;
import com.ddumanskiy.merger.Merger;
import com.ddumanskiy.segments.*;
import com.ddumanskiy.utils.ArraysUtil;
import com.ddumanskiy.utils.DCT;
import com.ddumanskiy.utils.JpegInputStream;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.ddumanskiy.Markers.*;
import static com.ddumanskiy.utils.ArraysUtil.multiply;


/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class JpegDecoder {

    public static BufferedImage decode(Path jpegImageFilePath) throws IOException {
        //buffering full byte array for speedup.
        int fileSize = (int) Files.size(jpegImageFilePath);

        try (JpegInputStream jis = new JpegInputStream(new BufferedInputStream(Files.newInputStream(jpegImageFilePath), fileSize))) {

            SegmentHolder segmentHolder = readAllSegments(jis);


            HuffmanDecoder huffmanDecoder = new HuffmanDecoder(
                    jis,
                    segmentHolder.dhtSegment.getDcTables(),
                    segmentHolder.dhtSegment.getAcTables(),
                    segmentHolder.sofSegment.getComponents(),
                    segmentHolder.sosSegment.getComponents()
            );

            MCUBlockHolder holder = new MCUBlockHolder();
            Merger merger;
            if (segmentHolder.sofSegment.getComponentCount() == 1) {
                merger = new GrayBlockMerger(segmentHolder.sofSegment.getWidth(), segmentHolder.sofSegment.getHeight());
            } else {
                merger = new ColorBlockMerger(segmentHolder.sofSegment.getWidth(), segmentHolder.sofSegment.getHeight(), segmentHolder.sofSegment);
            }

            try {
                while (true) {
                    huffmanDecoder.decode(holder);

                    multiplyAll(segmentHolder.dqtSegment, holder, segmentHolder.sofSegment.getComponentCount());

                    fillInZigZagOrder(holder, segmentHolder.sofSegment.getComponentCount());

                    inverseDCTAll(holder, segmentHolder.sofSegment.getComponentCount());

                    merger.merge(holder);
                }
            } catch (IllegalStateException e) {
                //end
            }

            return makeImageFromRGBMatrix(merger.getFullBlock(), segmentHolder.sofSegment.getWidth(), segmentHolder.sofSegment.getHeight());
        }

    }

    private static void fillInZigZagOrder(MCUBlockHolder holder, int compNum) {
        ArraysUtil.fillInZigZagOrder(holder.yComponentsZZ, holder.yComponents);
        if (compNum == 3) {
            ArraysUtil.fillInZigZagOrder(holder.cbComponentZZ, holder.cbComponent);
            ArraysUtil.fillInZigZagOrder(holder.crComponentZZ, holder.crComponent);
        }
    }

    private static void inverseDCTAll(MCUBlockHolder holder, int compNum) {
        DCT.inverseDCT(holder.yComponentsZZ);
        if (compNum == 3) {
            DCT.inverseDCT(holder.cbComponentZZ);
            DCT.inverseDCT(holder.crComponentZZ);
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
     * @param jis - image file as byte stream.
     * @throws IOException
     */
    private static SegmentHolder readAllSegments(JpegInputStream jis) throws IOException {
        SegmentHolder segmentHolder = new SegmentHolder();

        //while we din't find real jpeg image data
        while (segmentHolder.sosSegment == null) {
            int marker = jis.readShort();

            switch (marker) {
                case SOI :
                    //System.out.println("SOI");
                    break;

                case SOF0 :
                    //System.out.println("SOF0");
                    segmentHolder.sofSegment = SOFSegment.decode(jis);
                    break;

                case SOF1 :
                case SOF2 :
                    //System.out.println("SOF1");
                    jis.skip(jis.readSize());
                    break;

                case DHT :
                    //System.out.println("DHT");
                    DHTTable dhtTable = DHTSegment.decode(jis);
                    segmentHolder.dhtSegment.add(dhtTable);
                    break;

                case DQT :
                    //System.out.println("DQT");
                    DQTTable dqtTable = DQTSegment.decode(jis);
                    segmentHolder.dqtSegment.add(dqtTable);
                    break;

                case DRI :
                    //System.out.println("DRI");
                    jis.skip(jis.readSize());
                    break;

                case SOS :
                    //System.out.println("SOS");
                    segmentHolder.sosSegment = SOSSegment.decode(jis);
                    break;

               case COM :
                    //System.out.println("COM");
                    jis.skip(jis.readSize());
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
                    jis.skip(jis.readSize());
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
                    jis.skip(jis.readSize());
                    break;
                default :
                    System.out.println("Should be never reached.");
                    break;
            }
        }

        return segmentHolder;
    }


    public static BufferedImage makeImageFromRGBMatrix(int[] rgbMatrix, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final int[] a = ((DataBufferInt) bi.getRaster().getDataBuffer()).getData();
        System.arraycopy(rgbMatrix, 0, a, 0, rgbMatrix.length);
        return bi;
    }


}