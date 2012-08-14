package com.tgid.tri.results

import com.tgid.tri.exception.RaceResultException
import com.tgid.tri.exception.SegmentResultException
import com.tgid.tri.auth.User

class RaceResultService {

    def RaceResult createRaceResult(RaceResult raceResult) throws RaceResultException, SegmentResultException {

        if(raceResult.validate()){
            raceResult.save(flush:true)
        }
        else{
            log.error(raceResult.errors.toString())
            def message = "Could not create result ${raceResult}"
            throw new RaceResultException(
                    message : message,
                    problem : raceResult
            )
        }
        raceResult
    }

    def void deleteRaceResult(long raceResultId, User user){
        def raceResult = RaceResult.findByIdAndUser(raceResultId, user)
        try {
            raceResult.delete()
        } catch(Exception exception){
            log.error(exception)
            def message = "Could not delete result ${raceResult} (${exception.message})"
            throw new RaceResultException(
                    message : message,
                    problem : raceResult
            )
        }
    }
}
