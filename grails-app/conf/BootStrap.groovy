import com.tgid.tri.auth.Role
import grails.util.Environment

class BootStrap {

    def segmentService
    def stateService
    def bootStrapService
    def countryService
    def coursePatternService

    def init = { servletContext ->

        Role.findOrSaveWhere(authority: 'ROLE_USER')
        Role.findOrSaveWhere(authority: 'ROLE_ADMIN')

        createInitialRacesAndResults()
    }

    private void createInitialRacesAndResults() {
        coursePatternService.seedPatternMaps()
        segmentService.seedSegments()
        countryService.seedCountries()
        stateService.seedStates()
        if(Environment.current != Environment.PRODUCTION) {
            bootStrapService.createDefaultUsers()
            //bootStrapService.seedResults()
        }
    }
}