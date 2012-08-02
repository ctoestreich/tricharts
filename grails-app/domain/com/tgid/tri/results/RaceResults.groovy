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

    static constraints = {
        race nullable: false
        placeAgeGroup nullable: true
        placeOverall nullable: true
        duration nullable: true
    }

    transient Date getDate() {
        return race.date
    }
}
