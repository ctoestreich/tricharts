package com.tgid.tri.auth

import com.tgid.tri.race.RaceType

/**
 */
class UserSport {
    User user
    Set<RaceType> sports = [] as Set<RaceType>
    static hasMany = [sports: RaceType]
}
