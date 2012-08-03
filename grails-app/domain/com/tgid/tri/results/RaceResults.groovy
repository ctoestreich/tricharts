package com.tgid.tri.results

import com.tgid.tri.auth.User
import com.tgid.tri.race.Race
import org.joda.time.Duration

class RaceResults {

    static hasMany = [segmentResults: SegmentResults]

    Race race
    Duration duration
    Integer placeAgeGroup
    Integer placeOverall

    static belongsTo = [user: User]

    static transients = ['date']

    static constraints = {
        race nullable: false
        placeAgeGroup nullable: true
        placeOverall nullable: true
        duration nullable: true
    }

    @Override
    String toString() {
        "${race?.name} ${race?.date?.format('M/dd/yy')}"
    }

    transient Date getDate() {
        race?.date
    }
}
