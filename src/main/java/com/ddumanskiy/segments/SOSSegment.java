package com.ddumanskiy.segments;

import com.ddumanskiy.utils.BitUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static com.ddumanskiy.utils.ByteBufferUtil.readSizeAndDataAndWrap;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class SOSSegment {

    private SOSComponent[] components;

    private byte[] imageData;

    public static SOSSegment decode(ByteBuffer bb) throws IOException {
        ByteBuffer sosData = readSizeAndDataAndWrap(bb);

        //HexDump.dump(markerData, 0, System.out, 0);

        byte componentsNumber = sosData.get();

        SOSComponent[] sosComponents = new SOSComponent[componentsNumber];
        for (int i = 0; i < componentsNumber; i++) {
            byte id = sosData.get();
            byte acdc = sosData.get();
            sosComponents[i] = new SOSComponent(id, BitUtil.first4Bits(acdc), BitUtil.last4Bits(acdc));
        }

        byte[] imageData = SOSSegment.readImageData(bb);

        return new SOSSegment(imageData, sosComponents);
    }

    public static byte[] readImageData(ByteBuffer bb) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(bb.remaining());
        while (bb.hasRemaining()) {
            int b = Byte.toUnsignedInt(bb.get());
            if (b == 0xff) {
                int marker = Byte.toUnsignedInt(bb.get());
                if (marker == 0x00) {
                    bytes.write(b);
                } else if (marker == 0xD9) {
                    if (bb.hasRemaining()) {
                        throw new IllegalStateException("Found end that is not end.");
                    }
                    break;
                } else {
                    throw new IllegalStateException("Something wrong.");
                }
            } else {
                bytes.write(b);
            }
        }

        return bytes.toByteArray();
    }

    public SOSSegment(byte[] imageData, SOSComponent... components) {
        this.imageData = imageData;
        this.components = components;
    }

    public SOSComponent[] getComponents() {
        return components;
    }

    public byte[] getImageData() {
        return imageData;
    }

    @Override
    public String toString() {
        return "SOSStructure{" +
                "components=" + Arrays.toString(components) +
                '}';
    }
}
