import com.tgid.tri.auth.Role
import grails.util.Environment

class BootStrap {

    def segmentService
    def stateService
    def bootStrapService

    def init = { servletContext ->

        Role.findOrSaveWhere(authority: 'ROLE_USER')
        Role.findOrSaveWhere(authority: 'ROLE_ADMIN')

        createInitialRacesAndResults()
    }

    private void createInitialRacesAndResults() {
        segmentService.seedSegments()
        stateService.seedStates()
        if(Environment.current == Environment.DEVELOPMENT) {
            bootStrapService.createDefaultUsers()
            bootStrapService.seedResults()
        }
    }
}