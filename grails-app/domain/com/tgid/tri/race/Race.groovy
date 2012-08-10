package com.tgid.tri.race

class Race {

    String name
    Date date
    RaceType raceType
    DistanceType distanceType
    RaceCategoryType raceCategoryType
    Float distance = 0.00
    StatusType statusType = StatusType.Pending

    static hasMany = [segments: RaceSegment]

    static constraints = {
        name nullable: false
        date nullable: false
        raceType nullable: false, blank: false
        distanceType nullable: true
        distance nullable: true
        raceCategoryType nullable: true
        statusType nullable: false
    }

    static mapping = {
        sort 'date'
        raceCategoryType index: 'RaceCategory_Index'
        raceType index: 'RaceType_Index'
    }

    @Override
    String toString() {
        "${name ?: 'Unknown'} ${(raceCategoryType) ?: ''} ${date?.format("yyyy")}"
    }
}
