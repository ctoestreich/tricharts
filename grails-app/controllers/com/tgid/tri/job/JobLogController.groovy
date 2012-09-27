package com.tgid.tri.job

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class JobLogController {

    def scaffold = JobLog

}
