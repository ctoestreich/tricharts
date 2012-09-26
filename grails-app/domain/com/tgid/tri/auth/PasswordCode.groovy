package com.tgid.tri.auth

class PasswordCode {

    String username
    String token = UUID.randomUUID().toString().replaceAll('-', '')
    Date dateCreated = new Date()
    Date dateFulfilled

    static mapping = {
        version false
        username index: 'UserNamePassword_Index', email: true
    }

    static constraints = {
        dateFulfilled nullable: true
    }
}
