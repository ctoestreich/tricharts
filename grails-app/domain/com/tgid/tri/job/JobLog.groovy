package com.tgid.tri.job

class JobLog {

    String jobName
    String description
    Boolean complete = false
    Date createDate = new Date()

    static constraints = {
        jobName blank: false
        description blank: true, nullable: true
    }

    static mapping = {
        autoTimestamp true
        sort createDate: 'desc'
    }
}
