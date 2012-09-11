package com.tgid.tri.auth

/**
 */
class Country {

    String countryID
    String countryID3
    String countryName

    static constraints = {
        countryID blank: false
        countryID3 blank: false
        countryName blank: false
    }
}
