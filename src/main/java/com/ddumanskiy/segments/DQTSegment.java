package com.ddumanskiy.segments;

import java.io.IOException;
import java.nio.ByteBuffer;

import static com.ddumanskiy.utils.BitUtil.first4Bits;
import static com.ddumanskiy.utils.BitUtil.last4Bits;
import static com.ddumanskiy.utils.ByteBufferUtil.readSizeAndDataAndWrap;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class DQTSegment {

    //todo seems 2 is a max?
    private int[][][] dqtTables = new int[2][][];

    public static DQTTable decode(ByteBuffer bb) throws IOException {
        ByteBuffer dqtData = readSizeAndDataAndWrap(bb);

        byte sizeAndId = dqtData.get();
        int sizeOfElement = first4Bits(sizeAndId);
        int id = last4Bits(sizeAndId);
        //for now ignore element size for simplicity. assume always 1 byte
        if (sizeOfElement > 1) {
            throw new IllegalStateException("Unexpected dqt element size.");
        }


        byte[] dqtDataArray = new byte[dqtData.remaining()];
        dqtData.get(dqtDataArray);
        //HexDump.dump(markerData, 0, System.out, 0);
        return new DQTTable(sizeOfElement, id, dqtDataArray);
    }

    public int[][][] getDqtTables() {
        return dqtTables;
    }

    public void add(DQTTable dqtTable) {
        dqtTables[dqtTable.getId()] = dqtTable.getData();
    }

}
