package com.tgid.tri

import com.tgid.tri.auth.User
import org.joda.time.Duration

class Race {

    String name
    Date date
    Duration duration
    RaceType raceType = RaceType.Triathlon
    User user

    static hasMany = [segments: Segment]

    static constraints = {
        user nullable: false
        raceType nullable: false, blank: false
        date nullable: false
        duration nullable: false
    }
}
