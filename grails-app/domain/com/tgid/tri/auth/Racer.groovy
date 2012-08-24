package com.tgid.tri.auth

class Racer {

    Long racerID
    Date lastImport

    static belongsTo = [user: User]

    static constraints = {
        racerID nullable: false
        lastImport nullable: true
    }

    @Override
    public String toString() {
        return "${user} (${racerID})"
    }
}
