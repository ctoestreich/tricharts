package com.tgid.tri.results

import com.tgid.tri.auth.User
import com.tgid.tri.race.Race
import org.joda.time.Duration
import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

class RaceResult {

    List segmentResult = LazyList.decorate(new ArrayList(), FactoryUtils.instantiateFactory(SegmentResult.class))
    static hasMany = [segmentResults: SegmentResult]

    Race race
    Duration duration
    Integer placeAgeGroup
    Integer placeOverall

    static belongsTo = [user: User]

    static transients = ['date']

    static mapping ={
        segmentResults cascade:"all-delete-orphan"
    }

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
