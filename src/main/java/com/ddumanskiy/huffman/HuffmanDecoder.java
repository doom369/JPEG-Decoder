package com.ddumanskiy.huffman;


import com.ddumanskiy.MCUBlockHolder;
import com.ddumanskiy.segments.SOFComponent;
import com.ddumanskiy.segments.SOSComponent;
import com.ddumanskiy.utils.ArraysUtil;
import com.ddumanskiy.utils.ByteArrayWrapper;

import java.util.Arrays;

import static com.ddumanskiy.utils.BitUtil.*;

/**
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
    private MCUBlockHolder mcuHolder;
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
        this.mcuHolder = new MCUBlockHolder();
        this.block = new int[ArraysUtil.SIZE * ArraysUtil.SIZE];
    }

    public MCUBlockHolder decode() {
        for (SOSComponent component : sosComponents) {
            SOFComponent sofComponent = sofComponents[component.getId() - 1];
            for (int i = 0; i < sofComponent.getVh(); i++) {
                int[] mcu = decode(dcTables[component.getDcDhtTableId()], acTables[component.getAcDhtTableId()]);
                mcuHolder.add(component.getId(), mcu, i);
            }
        }
        return mcuHolder;
    }

    private int[] decode(HuffmanTree dcTable, HuffmanTree acTable) {
        Arrays.fill(block, 0);
        block[0] = decodeDC(dcTable);
        decodeAC(acTable);
        return block;
    }

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

    public int decodeDC(HuffmanTree dcTable) {
        int value = findCode(dcTable);
        int result = decodeCode(imageData, bitCounter + 1, value);
        bitCounter += value;
        return result;
    }

    public void decodeAC(HuffmanTree acTable) {
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

    private static int decodeCode(byte[] bits, int from, int count) {
        if (count == 0) return 0;

        int val = bitArrayToInt(bits, from, count);
        if (getBit(bits, from) == 128) {
            return val;
        } else {
            return val - (1 << count) + 1;
        }
    }

}
