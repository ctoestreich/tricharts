import com.tgid.tri.auth.Role
import com.tgid.tri.auth.User
import com.tgid.tri.auth.UserRole
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult
import org.joda.time.Duration
import com.tgid.tri.race.*
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import com.tgid.tri.auth.State

class BootStrap {

    def segmentService
    def stateService

    def init = { servletContext ->
        createInitialRacesAndResults()
    }

    private void createInitialRacesAndResults() {
        segmentService.seedSegments()
        stateService.seedStates()

        //create dome dummy users to test
        createUser(new User(username: 'acetrike@yahoo.com', password: 'acetrike', firstName: 'Christian', lastName: 'Oestreich', stateCode: 'MN', dob: new Date(79, 4, 23), enabled: true), true)
        createUser(new User(username: 'mitchtalbot@yahoo.com', password: 'mitchtalbot', firstName: 'Mitchel', lastName: 'Talbot', stateCode: 'MN', dob: new Date(79, 4, 18), enabled: true), false)
        createUser(new User(username: 'bugurlu@hotmail.com', password: 'bugurlu', firstName: 'Bulent', lastName: 'Ugurlu', stateCode: 'MN', dob: new Date(70, 10, 18), enabled: true), true)
        createUser(new User(username: 'kwschulz@gmail.com', password: 'kwschulz', firstName: 'Ken', lastName: 'Schulz', stateCode: 'CT', dob: new Date(77, 5, 18), enabled: true), true)
        createUser(new User(username: 'patrick.parish@gmail.com', password: 'pparish', firstName: 'Patrick', lastName: 'Parish', stateCode: 'MN', dob: new Date(85, 5, 18), enabled: true), false)
        createUser(new User(username: 'derek.haag@gmail.com', password: 'dhaag', firstName: 'Derek', lastName: 'Haag', stateCode: 'MN', dob: new Date(78, 8, 18), enabled: true), false)
    }

    private void createUser(User user, boolean isAdmin = false) {
        if(!User.findByUsername(user.username)) {
            user.save(flush: true)
            def role_user = Role.findOrSaveWhere(authority: 'ROLE_USER')
            def role_admin = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
            new UserRole(user: user, role: role_user).save(flush:  true)
            if(isAdmin) {
                new UserRole(user: user, role: role_admin).save(flush:  true)
            }
        }
    }
}