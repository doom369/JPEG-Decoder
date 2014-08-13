package com.ddumanskiy.huffman;


import com.ddumanskiy.MCUBlockHolder;
import com.ddumanskiy.segments.SOFComponent;
import com.ddumanskiy.segments.SOSComponent;
import com.ddumanskiy.utils.ArraysUtil;
import com.ddumanskiy.utils.ByteArrayWrapper;

import java.util.Arrays;

import static com.ddumanskiy.utils.BitUtil.*;

/**
 * Decodes input jpeg image byte array and produces minimum coded unit (MCU) per 1 HuffmanDecoder.decode() call.
 * For jpeg with 3 sosComponents MCU consists of 4 Y 8x8 arrays, 1 Cr 8x8 array and 1 Cb 8x8 array.
 *
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class HuffmanDecoder {

    private final int length;
    private HuffmanTree[] dcTables;
    private HuffmanTree[] acTables;
    private int bitCounter;
    private SOFComponent[] sofComponents;
    private SOSComponent[] sosComponents;
    private int[] block;
    private byte[] imageData;

    public HuffmanDecoder(ByteArrayWrapper imageData, HuffmanTree[] dcTables, HuffmanTree[] acTables, SOFComponent[] sofComponents, SOSComponent[] sosComponents) {
        this.imageData = imageData.getBuf();
        this.length = imageData.getLength() * 8 - 1;
        this.dcTables = dcTables;
        this.acTables = acTables;
        this.bitCounter = -1;
        this.sofComponents = sofComponents;
        this.sosComponents = sosComponents;
        //will be used for reusage
        this.block = new int[ArraysUtil.SIZE * ArraysUtil.SIZE];
    }

    /**
     * Decodes input byte array producing 1 MCU for call.
     * Throws IllegalStateException when end of input is reached (right now I don't see better way to do that).
     *
     */
    public void decode(MCUBlockHolder holder) {
        for (SOSComponent component : sosComponents) {
            SOFComponent sofComponent = sofComponents[component.getId() - 1];
            for (int i = 0; i < sofComponent.getVh(); i++) {
                int[] mcu = decode(dcTables[component.getDcDhtTableId()], acTables[component.getAcDhtTableId()]);
                holder.add(component.getId(), mcu, i);
            }
        }
    }

    /**
     * Decodes single 8x8 array either Y, either Cr, either Cb.
     */
    private int[] decode(HuffmanTree dcTable, HuffmanTree acTable) {
        Arrays.fill(block, 0);
        block[0] = decodeDC(dcTable);
        decodeAC(acTable);
        return block;
    }

    /**
     * Finds huffman code in huffman tree. Algorythm is pretty simple :
     * - Read 1 bit;
     * - go down (to left node in case read bit is 0 and to right node in case read bit is 1) through huffman tree starting from root;
     * - if current node has filled code (code > -1) we found huffman code. otherwise - repeat.
     *
     */
    private int findCode(HuffmanTree table) {
        HuffmanTree.Node start = table.root;

        while (bitCounter < length) {
            bitCounter++;

            int i = getBit(imageData, bitCounter);
            start = i == 128 ? start.node1 : start.node0;

            if (start.code > -1) {
                return start.code;
            }
        }

        throw new IllegalStateException("end of stream");
    }

    /**
     * Decodes single DC value. Algorithm is next :
     * - find huffman code for current bit position
     * -
     *    a) if code is 0 return 0
     *    b) if code starts from 1 return code itself
     *    c) if code starts from 0 calculate returned value by: code - 2^length_in_bits_of_code + 1
     *
     */
    public int decodeDC(HuffmanTree dcTable) {
        int value = findCode(dcTable);
        int result = decodeCode(imageData, bitCounter + 1, value);
        bitCounter += value;
        return result;
    }

    /**
     * Decodes rest of 63 AC values
     */
    private void decodeAC(HuffmanTree acTable) {
        for (int k = 1; k < ArraysUtil.SIZE * ArraysUtil.SIZE; k++) {
            int code = findCode(acTable);
            int zerosNumber = first4Bits(code);
            int bitCount = last4Bits(code);

            if (bitCount != 0) {
                k += zerosNumber;
                block[k] = decodeCode(imageData, bitCounter + 1, bitCount);
                bitCounter += bitCount;
            } else {
                if (zerosNumber != 0x0F) {
                    return;
                }
                k += 0x0F;
            }
        }
    }

    private static int decodeCode(byte[] bits, int from, int bitCount) {
        if (bitCount == 0) return 0;

        int val = bitArrayToInt(bits, from, bitCount);
        if (getBit(bits, from) == 128) {
            return val;
        } else {
            return val - (1 << bitCount) + 1;
        }
    }

}
