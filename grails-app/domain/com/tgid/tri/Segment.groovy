package com.tgid.tri

import org.joda.time.Duration

class Segment {

    Duration duration
    SegmentType segmentType
    static belongsTo = [race: Race]

    static constraints = {
    }
}
