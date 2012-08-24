package com.tgid.tri.race

/**
 */
class CoursePattern {

    Long parentID
    String name
    Long weight

    static mapping = {
        id generator: 'assigned'
        sort weight: "desc"
    }

    static constraints = {
        name maxSize: 350
    }

    @Override
    public String toString() {
        name
    }
}
