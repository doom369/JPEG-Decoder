package com.ddumanskiy.segments;

import com.ddumanskiy.utils.JpegInputStream;

import java.io.IOException;

import static com.ddumanskiy.utils.BitUtil.first4Bits;
import static com.ddumanskiy.utils.BitUtil.last4Bits;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class SOFSegment {

    private int precision;
    
    private int width;
    
    private int height;

    private SOFComponent[] components;

    private int maxV;

    private int maxH;

    public static SOFSegment decode(JpegInputStream jis) throws IOException {
        //skip length bytes as we know how many to read
        jis.skip(2);

        int precision = jis.read();
        int height = jis.readShort();
        int width = jis.readShort();
        int componentNumber = jis.read();

        SOFComponent[] components = new SOFComponent[componentNumber];
        int maxH = 0;
        int maxV = 0;
        for (int i = 0; i < componentNumber; i++) {
            int id = jis.read();
            int hv = jis.read();
            int h = first4Bits(hv);
            int v = last4Bits(hv);
            int quantumTableId = jis.read();
            components[i] = new SOFComponent(id, h, v, quantumTableId);
            maxH = Math.max(maxH, h);
            maxV = Math.max(maxV, v);
        }

        return new SOFSegment(precision, width, height, components, maxH, maxV);
    }

    public SOFSegment(int precision, int width, int height, SOFComponent[] components, int maxH, int maxV) {
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

    public int getPrecision() {
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
