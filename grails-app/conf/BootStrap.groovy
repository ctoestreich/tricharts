import com.tgid.tri.auth.Role
import com.tgid.tri.auth.User
import com.tgid.tri.auth.UserRole
import com.tgid.tri.results.RaceResults
import com.tgid.tri.results.SegmentResults
import org.joda.time.Duration
import com.tgid.tri.race.*

class BootStrap {

    def init = { servletContext ->
        //def role2 = new Role(authority: 'ROLE_FACEBOOK').save(failOnError: true, flush: true)
        def user = new User(username: 'acetrike@yahoo.com', password: 'acetrike', enabled: true, firstName: 'Christian', lastName: 'Oestreich', dob: new Date(79, 4, 23)).save()
        def role = new Role(authority: 'ROLE_USER').save()
        new UserRole(user: user, role: role).save()
        //new UserRole(user: user, role: role2).save(failOnError: true, flush: true)

        def halfMileSwim = new Segment(distance: 0.5, distanceType: DistanceType.Miles, segmentType: SegmentType.Swim)
        def fifteenMileBike = new Segment(distance: 15, distanceType: DistanceType.Miles, segmentType: SegmentType.Bike)
        def fiveKilometerRun = new Segment(distance: 5, distanceType: DistanceType.Kilometers, segmentType: SegmentType.Run)

        def race = new Race(name: 'Graniteman Triathlon', date: new Date(112, 6, 17), raceType: RaceType.Triathlon, distanceType: DistanceType.Miles).save()
        race.addToSegments(halfMileSwim)
        race.addToSegments(fifteenMileBike)
        race.addToSegments(fiveKilometerRun)

        race.save()

        def swimResults = new SegmentResults(segment: halfMileSwim, duration: Duration.standardMinutes(13))
        def bikeResults = new SegmentResults(segment: fifteenMileBike, duration: Duration.standardMinutes(41))
        def runResults = new SegmentResults(segment: fiveKilometerRun, duration: Duration.standardSeconds(60 * 19 + 30))

        def raceResults = new RaceResults(race: race, placeAgeGroup: 3, placeOverall: 13, user: user).save()
        raceResults.addToSegmentResults(swimResults)
        raceResults.addToSegmentResults(bikeResults)
        raceResults.addToSegmentResults(runResults)
        raceResults.save()

        def oneMileRun = new Segment(distance: 1, distanceType: DistanceType.Miles, segmentType: SegmentType.Run)
        def race2 = new Race(name: 'Rice Street Mile', date: new Date(112, 5, 17), raceType: RaceType.Running, distanceType: DistanceType.Miles, distance: 1).save()
        race2.addToSegments(oneMileRun)
        race2.save()

        def rsmResults = new SegmentResults(segment: oneMileRun, duration: Duration.standardSeconds((60 * 5) + 28))
        def rsmRaceResults = new RaceResults(race: race2, placeAgeGroup: 20, placeOverall: 50, user: user).save()
        rsmRaceResults.addToSegmentResults(rsmResults)
        rsmRaceResults.save()

        //def oneMileRun = new Segment(order: 1, distance: 1, distanceType: DistanceType.Miles, segmentType: SegmentType.Run)
        def race3 = new Race(name: 'TC 1 Mile', date: new Date(112, 4, 17), raceType: RaceType.Running, distanceType: DistanceType.Miles, distance: 1).save()
        race3.addToSegments(oneMileRun)
        race3.save()

        def tcmResults = new SegmentResults(segment: oneMileRun, duration: Duration.standardSeconds((60 * 5) + 31))
        def tcmRaceResults = new RaceResults(race: race3, placeAgeGroup: 22, placeOverall: 57, user: user).save()
        tcmRaceResults.addToSegmentResults(tcmResults)
        tcmRaceResults.save()
    }
    def destroy = {
    }
}
