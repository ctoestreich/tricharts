package com.tgid.tri

import org.joda.time.Duration
import com.tgid.tri.auth.User

class Race {

    String name
    Date date
    Duration duration
    RaceType raceType = RaceType.Triathlon

    static belongsTo = [user: User]
    //User user

    static hasMany = [segments: Segment]

    static constraints = {
        //user nullable: false
        raceType nullable: false, blank: false
        date nullable: false
        duration nullable: false
    }

    @Override
    public String toString() {
        "$name ${date.year + 1900}"
    }
}
