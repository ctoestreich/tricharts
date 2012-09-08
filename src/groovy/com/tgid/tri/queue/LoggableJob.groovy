package com.tgid.tri.queue

import com.tgid.tri.job.JobLog

/**
 */
class LoggableJob {

    def withLog(String jobName, String description, Closure closure = {}){
        def jobLog = new JobLog(jobName: jobName, description: description).save()
        closure.call()
        jobLog.complete = true
        jobLog.save(flush:  true)
    }
}
