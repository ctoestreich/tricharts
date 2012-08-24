package com.tgid.tri.race

/**
 */
class RaceCategory {

    String description

    static constraints = {
        description maxSize: 350
    }

    static mapping = {
        id generator: 'assigned'
    }

    @Override
    public String toString() {
        description
    }
}
