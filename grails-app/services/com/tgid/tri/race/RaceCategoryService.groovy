package com.tgid.tri.race

class RaceCategoryService {

    def importRaceCategory(Map category) {
        if(!com.tgid.tri.race.RaceCategory.get(category?.RaceCatID)) {
            def raceCategory = new com.tgid.tri.race.RaceCategory(description: category?.RaceCatDesc)
            raceCategory.id = category?.RaceCatID
            if(raceCategory.validate()) {
                raceCategory.save(flush: true)
            } else {
                raceCategory?.errors?.allErrors?.each {
                    log.error it
                }
            }
        }
    }
}
