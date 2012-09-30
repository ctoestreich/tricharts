package com.tgid.tri.auth

class LoginHistory {

    Date loginDate = new Date()
    Boolean success = true

    static belongsTo = [user: User]

    static constraints = {
        user nullable:  false
        loginDate nullable:  false
        success nullable: false
    }

    static mapping ={
        user index: 'UserLogin_Index'
    }
}
