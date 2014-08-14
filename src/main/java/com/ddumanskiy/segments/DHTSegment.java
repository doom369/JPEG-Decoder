package com.ddumanskiy.segments;

import com.ddumanskiy.huffman.HuffmanTree;
import com.ddumanskiy.utils.JpegInputStream;

import java.io.IOException;

import static com.ddumanskiy.utils.BitUtil.first4Bits;
import static com.ddumanskiy.utils.BitUtil.last4Bits;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class DHTSegment {

    public static final int DC_TABLE_CLASS = 0;
    public static final int AC_TABLE_CLASS = 1;

    public static final int HUFFMAN_TABLE_CODE_LENGTH_MAX = 16;

    //todo - clarify, look like 4 tables total is a max.
    private HuffmanTree[] dcTables = new HuffmanTree[2];
    private HuffmanTree[] acTables = new HuffmanTree[2];

    public static DHTTable decode(JpegInputStream jis) throws IOException {
        //skip length bytes as we know how many to read
        jis.skip(2);

        int classAndId = jis.read();
        int tableClass = first4Bits(classAndId);
        int id = last4Bits(classAndId);

        int[] codeLengths = new int[HUFFMAN_TABLE_CODE_LENGTH_MAX];

        for (int i = 0; i < HUFFMAN_TABLE_CODE_LENGTH_MAX; i++) {
            codeLengths[i] = jis.read();
        }

        HuffmanTree huffmanTree = new HuffmanTree();
        for (int i = 0; i < codeLengths.length; i++) {
            int codeLength = codeLengths[i];
            for (int j = 0; j < codeLength; j++) {
                int code = jis.read();
                huffmanTree.addLeaf(code);
            }
            if (i < codeLengths.length - 1) {
                huffmanTree.fillLevel();
            }
        }

        return new DHTTable(tableClass, id, huffmanTree);
    }

    public void add(DHTTable dhtTable) {
        if (dhtTable.getTableClass() == DHTSegment.DC_TABLE_CLASS) {
            dcTables[dhtTable.getId()] = dhtTable.getTree();
        } else if (dhtTable.getTableClass() == DHTSegment.AC_TABLE_CLASS){
            acTables[dhtTable.getId()] = dhtTable.getTree();
        } else {
            throw new IllegalStateException("Unexpected table id.");
        }
    }

    public HuffmanTree[] getDcTables() {
        return dcTables;
    }

    public HuffmanTree[] getAcTables() {
        return acTables;
    }
}
