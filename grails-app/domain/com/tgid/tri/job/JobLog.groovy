package com.tgid.tri.job

class JobLog {

    String jobName
    String description
    Boolean complete = false

    static constraints = {
        jobName blank: false
        description blank: true, nullable: true
    }

    static mapping = {
        autoTimestamp true
        sort dateCreated: 'desc'
    }
}
