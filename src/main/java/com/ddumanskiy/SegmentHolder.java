package com.ddumanskiy;

import com.ddumanskiy.segments.DHTSegment;
import com.ddumanskiy.segments.DQTSegment;
import com.ddumanskiy.segments.SOFSegment;
import com.ddumanskiy.segments.SOSSegment;

/**
 * Just a holder for all read JPEG headers.
 *
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class SegmentHolder {

    SOFSegment sofSegment = null;
    SOSSegment sosSegment = null;
    DHTSegment dhtSegment = new DHTSegment();
    DQTSegment dqtSegment = new DQTSegment();

}
