package com.tgid.tri.data

import com.tgid.tri.race.Race

class ResultsImport {

    Race race
    JobStatus jobStatus = JobStatus.Idle
    Long recordsProcessed = 0
    Long recordsErrored = 0

    static constraints = {
    }
}
