package com.tgid.tri.results

import com.tgid.tri.auth.User
import com.tgid.tri.race.Race
import org.joda.time.Duration

class RaceResult {

    static hasMany = [segmentResults: SegmentResult]

    Race race
    Duration duration
    Integer placeAgeGroup
    Integer placeGender
    Integer placeOverall
    Integer participantsAgeGroup
    Integer participantsGender
    Integer participantsOverall

    static belongsTo = [user: User]

    static transients = ['date']

    static mapping = {
        segmentResults cascade: "all-delete-orphan"
    }

    static constraints = {
        race nullable: false
        duration nullable: true
        placeAgeGroup nullable: true
        placeOverall nullable: true
        placeGender nullable: true
        participantsAgeGroup nullable: true
        participantsOverall nullable: true
        participantsGender nullable: true
    }

    @Override
    String toString() {
        "${race?.name} ${race?.date?.format('M/dd/yy')}"
    }

    transient Date getDate() {
        race?.date
    }
}
