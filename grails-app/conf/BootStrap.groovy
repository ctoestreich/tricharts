import com.tgid.tri.auth.Role
import com.tgid.tri.auth.User
import com.tgid.tri.auth.UserRole
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult
import org.joda.time.Duration
import com.tgid.tri.race.*

class BootStrap {

    def segmentService
    def userService

    def init = { servletContext ->
        createInitialRacesAndResults()
    }

    private void createInitialRacesAndResults() {
        seedSegments()

        //create dome dummy users to test
        createUser(new User(username: 'acetrike@yahoo.com', password: 'acetrike', firstName: 'Christian', lastName: 'Oestreich', stateCode: 'MN', dob: new Date(79, 4, 23), enabled: true), true)
        createUser(new User(username: 'mitchtalbot@yahoo.com', password: 'mitchtalbot', firstName: 'Mitchel', lastName: 'Talbot', stateCode: 'MN', dob: new Date(79, 4, 18), enabled: true), false)
        createUser(new User(username: 'bugurlu@hotmail.com', password: 'bugurlu', firstName: 'Bulent', lastName: 'Ugurlu', stateCode: 'MN', dob: new Date(70, 10, 18), enabled: true), true)
        createUser(new User(username: 'kwschulz@gmail.com', password: 'kwschulz', firstName: 'Ken', lastName: 'Schulz', stateCode: 'CT', dob: new Date(77, 5, 18), enabled: true), true)
        createUser(new User(username: 'patrick.parish@gmail.com', password: 'pparish', firstName: 'Patrick', lastName: 'Parish', stateCode: 'MN', dob: new Date(85, 5, 18), enabled: true), false)
        createUser(new User(username: 'derek.haag@gmail.com', password: 'dhaag', firstName: 'Derek', lastName: 'Haag', stateCode: 'MN', dob: new Date(78, 8, 18), enabled: true), false)
        createUser(new User(username: 'user@gmail.com', password: 'user', firstName: 'Test', lastName: 'User', dob: new Date(80, 3, 1), enabled: true), false)
    }

    private void seedSegments() {
        segmentService.findOrSaveSegment(0.3f, DistanceType.Miles, SegmentType.Swim)
        segmentService.findOrSaveSegment(0.5f, DistanceType.Miles, SegmentType.Swim)
        segmentService.findOrSaveSegment(0.9f, DistanceType.Miles, SegmentType.Swim)
        segmentService.findOrSaveSegment(1, DistanceType.Miles, SegmentType.Swim)
        segmentService.findOrSaveSegment(1.2f, DistanceType.Miles, SegmentType.Swim)
        segmentService.findOrSaveSegment(2.4f, DistanceType.Miles, SegmentType.Swim)
        segmentService.findOrSaveSegment(2, DistanceType.Miles, SegmentType.Swim)
        segmentService.findOrSaveSegment(3, DistanceType.Miles, SegmentType.Swim)
        segmentService.findOrSaveSegment(4, DistanceType.Miles, SegmentType.Swim)
        segmentService.findOrSaveSegment(5, DistanceType.Miles, SegmentType.Swim)

        segmentService.findOrSaveSegment(11, DistanceType.Miles, SegmentType.Bike)
        segmentService.findOrSaveSegment(13, DistanceType.Miles, SegmentType.Bike)
        segmentService.findOrSaveSegment(13.5f, DistanceType.Miles, SegmentType.Bike)
        segmentService.findOrSaveSegment(15, DistanceType.Miles, SegmentType.Bike)
        segmentService.findOrSaveSegment(20, DistanceType.Kilometers, SegmentType.Bike)
        segmentService.findOrSaveSegment(25, DistanceType.Kilometers, SegmentType.Bike)
        segmentService.findOrSaveSegment(40, DistanceType.Kilometers, SegmentType.Bike)
        segmentService.findOrSaveSegment(100, DistanceType.Miles, SegmentType.Bike)
        segmentService.findOrSaveSegment(100, DistanceType.Kilometers, SegmentType.Bike)

        segmentService.findOrSaveSegment(0, DistanceType.Yards, SegmentType.T1)
        segmentService.findOrSaveSegment(0, DistanceType.Yards, SegmentType.T2)

        segmentService.findOrSaveSegment(1, DistanceType.Miles, SegmentType.Run)
        segmentService.findOrSaveSegment(3, DistanceType.Miles, SegmentType.Run)
        segmentService.findOrSaveSegment(3.1f, DistanceType.Miles, SegmentType.Run)
        segmentService.findOrSaveSegment(5, DistanceType.Kilometers, SegmentType.Run)
        segmentService.findOrSaveSegment(4, DistanceType.Miles, SegmentType.Run)
        segmentService.findOrSaveSegment(5, DistanceType.Miles, SegmentType.Run)
        segmentService.findOrSaveSegment(8, DistanceType.Kilometers, SegmentType.Run)
        segmentService.findOrSaveSegment(10, DistanceType.Kilometers, SegmentType.Run)
        segmentService.findOrSaveSegment(25, DistanceType.Kilometers, SegmentType.Run)
        segmentService.findOrSaveSegment(13.1f, DistanceType.Miles, SegmentType.Run)
        segmentService.findOrSaveSegment(26.2f, DistanceType.Miles, SegmentType.Run)
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