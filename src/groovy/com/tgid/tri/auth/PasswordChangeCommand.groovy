package com.tgid.tri.auth

/**
 */
@grails.validation.Validateable
class PasswordChangeCommand {

    String password
    String passwordAgain

    def clear() {
        password = ''
        passwordAgain = ''
    }

    static constraints = {
        password(blank: false)
        passwordAgain(blank: false)
        password validator: { val, obj ->
            if(val != obj.passwordAgain) return 'not.match'
        }
        passwordAgain validator: { val, obj ->
            if(val != obj.password) return 'not.match'
        }
    }
}
