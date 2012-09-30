package com.tgid.tri.auth

import com.tgid.tri.race.Race
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class User {

    transient springSecurityService

    String firstName
    String lastName
    Date dob
    String username
    String password
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    GenderType genderType

    static hasMany = [races: Race, racers: Racer, states: State, logins: LoginHistory]

    static constraints = {
        username blank: false, unique: true, email: true
        password blank: false
        firstName blank: false
        lastName blank: false
        dob blank: false, max: new Date()
        genderType nullable: true
    }

    static mapping = {
        password column: '`password`'
        logins cascade: "all-delete-orphan"
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if(isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }

    @Override
    public String toString() {
        return "$firstName $lastName"
    }
}
