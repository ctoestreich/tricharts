package com.tgid.tri.data

class ImportLog {

    String importName
    String description
    Boolean complete = false
    Boolean error = false
    Date createDate = new Date()

    static constraints = {
        importName blank: false
        description blank: true, nullable: true, maxSize: 1000
    }

    static mapping = {
        autoTimestamp true
        version false
        sort createDate: 'desc'
    }
}
