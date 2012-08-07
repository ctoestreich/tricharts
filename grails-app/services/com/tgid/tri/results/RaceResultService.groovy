package com.tgid.tri.results

import com.tgid.tri.exception.RaceResultException
import com.tgid.tri.exception.SegmentResultException

class RaceResultService {

    def RaceResult createRaceResult(RaceResult raceResult) throws RaceResultException, SegmentResultException {

        raceResult.segmentResults.each {
            println it.validate();
        }

        if(raceResult.validate()){
            raceResult.save(flush:true)
        }
        else{
            def message = "Could not create result ${raceResult}"
            throw new RaceResultException(
                    message : message,
                    problem : raceResult
            )
        }
        raceResult
    }
}
