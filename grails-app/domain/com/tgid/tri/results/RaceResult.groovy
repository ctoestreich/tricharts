package com.tgid.tri.results

import com.tgid.tri.auth.User
import com.tgid.tri.race.Race
import org.joda.time.Duration
import com.tgid.tri.auth.GenderType

class RaceResult {

    static hasMany = [segmentResults: SegmentResult]

    Race race
    Long athlinkEntryID
    Duration duration
    Integer placeAgeGroup
    Integer placeGender
    Integer placeOverall
    Integer participantsAgeGroup
    Integer participantsGender
    Integer participantsOverall
    String bibNumber
    Integer age
    String ageGroup
    GenderType genderType

    static belongsTo = [user: User]

    static transients = ['date', 'result']

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
        athlinkEntryID nullable: true
        bibNumber nullable: true
        age nullable: true
        ageGroup nullable: true
        genderType nullable: true
    }

    @Override
    String toString() {
        "${race?.name} ${race?.date?.format('M/dd/yy')}"
    }

    transient Date getDate() {
        race?.date
    }

    transient SegmentResult getResult() {
        if(segmentResults.size() > 0)
            return segmentResults?.toList()?.first()
        else
            return null
    }
}
