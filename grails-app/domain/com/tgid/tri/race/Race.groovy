package com.tgid.tri.race

class Race {

    String name
    Date date
    RaceType raceType
    DistanceType distanceType
    Float distance = 0.00
    Integer participantsOverall
    Integer participantsAgeGroup

    static hasMany = [segments: Segment]

    static constraints = {
        name nullable: false
        date nullable: false
        raceType nullable: false, blank: false
        distanceType nullable: true
        distance nullable: true
        participantsAgeGroup nullable: true
        participantsOverall nullable: true
    }

    static mapping ={
        sort 'date'
    }

    @Override
    String toString() {
        "${name ?: 'Unknown'}  ${date?.format("yyyy")}"
    }
}
