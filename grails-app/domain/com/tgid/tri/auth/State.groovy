package com.tgid.tri.auth

class State {
    //"StateProvID":"US_MT ","CountryID":"US","Abbrev":"MT","StateProvName":"Montana"
    String provID
    String countryID
    String abbrev
    String name

    static constraints = {
        name blank: false, nullable: false
        abbrev blank: false, nullable: false
        countryID blank: false, nullable: false
        provID blank: false, nullable: false, unique: true
    }

    @Override
    public String toString() {
       "$name"
    }
}
