package com.tgid.tri.auth

class Racer {

    Long racerID

    static belongsTo = [user: User]

    static constraints = {
        racerID nullable: false
    }
}
