package com.tgid.tri.common

class CoursePatternLocalMap {

    String mapKey
    CoursePatternLocal coursePatternLocal

    static constraints = {
        mapKey blank: false, nullable: false, maxSize: 300
        coursePatternLocal nullable: false
    }

    static mapping = {
        autoTimestamp true
        version true
        mapKey index: 'CoursePatternMapKey_Index', unique: true
        sort 'mapKey'
    }

    @Override
    String toString() {
        "$mapKey = $coursePatternLocal"
    }
}
