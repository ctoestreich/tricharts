package com.tgid.tri.common

import com.tgid.tri.race.Segment

/**
 */
class SegmentComparator implements Comparator {

    @Override int compare(Object o1, Object o2) {
        Segment p1 = (Segment) o1
        Segment p2 = (Segment) o2
        if(p1.distance == p2.distance && p1.distanceType == p2.distanceType)
            return 0
        else
            return -1
    }
}
