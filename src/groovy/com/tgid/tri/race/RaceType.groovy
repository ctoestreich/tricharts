package com.tgid.tri.race

/**
 */
enum RaceType implements Serializable {

    Triathlon('Triathlon'), Running('Running'), Biking('Biking'), Swimming('Swimming'), Duathlon('Duathlon'), Aquathon('Aquathon')

    String raceType

    RaceType(String raceType) {
        this.raceType = raceType
    }

    static RaceType getRaceType(String value) {
        RaceType.values().find { it.raceType == value }
    }

    @Override
    String toString() {
        this.raceType
    }
}
