package com.tgid.tri.race

import grails.plugins.springsecurity.Secured

@Secured(["ROLE_ADMIN"])
class CoursePatternController {

    def scaffold = CoursePattern
}
