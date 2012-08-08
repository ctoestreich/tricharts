package com.tgid.tri.race

class Race {

    String name
    Date date
    RaceType raceType
    DistanceType distanceType
    RaceCategoryType raceCategoryType
    Float distance = 0.00

    static hasMany = [segments: RaceSegment]

    static constraints = {
        name nullable: false
        date nullable: false
        raceType nullable: false, blank: false
        distanceType nullable: true
        distance nullable: true
        raceCategoryType nullable: true
    }

    static mapping ={
        sort 'date'
    }

    @Override
    String toString() {
        "${name ?: 'Unknown'}  ${date?.format("yyyy")}"
    }
}
