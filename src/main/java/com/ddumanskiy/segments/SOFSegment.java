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
public class SOFSegment {

    private byte precision;
    
    private int width;
    
    private int height;

    private SOFComponent[] components;

    private int maxV;

    private int maxH;

    public static SOFSegment decode(ByteBuffer bb) throws IOException {
        ByteBuffer sofData = readSizeAndDataAndWrap(bb);

        byte precision = sofData.get();
        int height = Short.toUnsignedInt(sofData.getShort());
        int width = Short.toUnsignedInt(sofData.getShort());
        byte componentNumber = sofData.get();

        SOFComponent[] components = new SOFComponent[componentNumber];
        int maxH = 0;
        int maxV = 0;
        for (int i = 0; i < componentNumber; i++) {
            byte id = sofData.get();
            byte hv = sofData.get();
            int h = first4Bits(hv);
            int v = last4Bits(hv);
            byte quantumTableId = sofData.get();
            components[i] = new SOFComponent(id, h, v, quantumTableId);
            maxH = Math.max(maxH, h);
            maxV = Math.max(maxV, v);
        }

        return new SOFSegment(precision, width, height, components, maxH, maxV);
    }

    public SOFSegment(byte precision, int width, int height, SOFComponent[] components, int maxH, int maxV) {
        this.precision = precision;
        this.width = width;
        this.height = height;
        this.components = components;
        this.maxH = maxH;
        this.maxV = maxV;
    }

    @Override
    public String toString() {
        return "SOF0Structure [precision=" + precision + ", width=" + width + ", height=" + height
                + ", " + componentsToString() + "]";
    }

    public byte getPrecision() {
        return precision;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SOFComponent[] getComponents() {
        return components;
    }

    public int getComponentCount() {
        return components.length;
    }

    public int getMaxV() {
        return maxV;
    }

    public int getMaxH() {
        return maxH;
    }

    private String componentsToString() {
        StringBuilder sb = new StringBuilder();
        for (SOFComponent component : components) {
            sb.append(component).append("; ");
        }
        return sb.toString();
    }

}
