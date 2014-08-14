package com.ddumanskiy.segments;

import com.ddumanskiy.utils.BitUtil;
import com.ddumanskiy.utils.JpegInputStream;

import java.io.IOException;
import java.util.Arrays;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class SOSSegment {

    private SOSComponent[] components;

    public static SOSSegment decode(JpegInputStream jis) throws IOException {
        int size = jis.readSize();

        int componentsNumber = jis.read();

        SOSComponent[] sosComponents = new SOSComponent[componentsNumber];
        for (int i = 0; i < componentsNumber; i++) {
            int id = jis.read();
            int acdc = jis.read();
            sosComponents[i] = new SOSComponent(id, BitUtil.first4Bits(acdc), BitUtil.last4Bits(acdc));
            size -= 2;
        }

        //todo dont use right now rest of bytes, so skip
        size = size - 1;
        jis.skip(size);

        return new SOSSegment(sosComponents);
    }

    public SOSSegment(SOSComponent... components) {
        this.components = components;
    }

    public SOSComponent[] getComponents() {
        return components;
    }

    @Override
    public String toString() {
        return "SOSStructure{" +
                "components=" + Arrays.toString(components) +
                '}';
    }
}
