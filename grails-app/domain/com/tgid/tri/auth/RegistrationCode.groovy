package com.tgid.tri.auth

class RegistrationCode {

    String username
    String token = UUID.randomUUID().toString().replaceAll('-', '')
    Date dateCreated

    static mapping = {
        version false
        username index: 'UserName_Index'
    }
}