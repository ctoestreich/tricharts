import com.tgid.tri.auth.Role
import com.tgid.tri.auth.User
import com.tgid.tri.auth.UserRole
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult
import org.joda.time.Duration
import com.tgid.tri.race.*

class BootStrap {

    def init = { servletContext ->
        //def role2 = new Role(authority: 'ROLE_FACEBOOK').save(failOnError: true, flush: true)
        def user = new User(username: 'acetrike@yahoo.com', password: 'acetrike', enabled: true, firstName: 'Christian', lastName: 'Oestreich', dob: new Date(79, 4, 23)).save()
        def role_user = new Role(authority: 'ROLE_USER').save()
        def role_admin = new Role(authority: 'ROLE_ADMIN').save()
        new UserRole(user: user, role: role_user).save()
        new UserRole(user: user, role: role_admin).save()
        //new UserRole(user: user, role: role2).save(failOnError: true, flush: true)

        def halfMileSwim = new Segment(distance: 0.5, distanceType: DistanceType.Miles, segmentType: SegmentType.Swim).save()
        def fifteenMileBike = new Segment(distance: 15, distanceType: DistanceType.Miles, segmentType: SegmentType.Bike).save()
        def fiveKilometerRun = new Segment(distance: 5, distanceType: DistanceType.Kilometers, segmentType: SegmentType.Run).save()
        def t1 = new Segment(distance: 400, distanceType: DistanceType.Meters, segmentType: SegmentType.T1).save()
        def t2 = new Segment(distance: 400, distanceType: DistanceType.Meters, segmentType: SegmentType.T2).save()
        def oneMileRun = new Segment(distance: 1, distanceType: DistanceType.Miles, segmentType: SegmentType.Run).save()
        def marathonRun = new Segment(distance: 26.2, distanceType: DistanceType.Miles, segmentType: SegmentType.Run).save()
        def halfMarathonRun = new Segment(distance: 13.1, distanceType: DistanceType.Miles, segmentType: SegmentType.Run).save()
        def tenKilometerRun = new Segment(distance: 10, distanceType: DistanceType.Kilometers, segmentType: SegmentType.Run).save()
        def eightKilometerRun = new Segment(distance: 8, distanceType: DistanceType.Kilometers, segmentType: SegmentType.Run).save()
        def twentyFiveKilometerRun = new Segment(distance: 25, distanceType: DistanceType.Kilometers, segmentType: SegmentType.Run).save()

        def race = new Race(name: 'Graniteman Triathlon', date: new Date(112, 6, 17), raceType: RaceType.Triathlon,
                            distanceType: DistanceType.Miles, participantsOverall: 316, participantsAgeGroup: 34,
                            raceCategoryType: RaceCategoryType.Sprint).save()

        def swimRaceSegment = new RaceSegment(race: race, segment: halfMileSwim).save()
        def t1Segment = new RaceSegment(race: race, segment: t1).save()
        def fifteenMileBikeSegment = new RaceSegment(race: race, segment: fifteenMileBike).save()
        def t2Segment = new RaceSegment(race: race, segment: t2).save()
        def fiveKilometerRunSegment = new RaceSegment(race: race, segment: fiveKilometerRun).save()

        def swimResults = new SegmentResult(raceSegment: swimRaceSegment, duration: Duration.standardSeconds(11 * 60 + 33), placeOverall: 18, placeAgeGroup: 3)
        def t1Results = new SegmentResult(raceSegment: t1Segment, duration: Duration.standardSeconds(23))
        def bikeResults = new SegmentResult(raceSegment: fifteenMileBikeSegment, duration: Duration.standardSeconds(40 * 60 + 15), placeOverall: 25, placeAgeGroup: 4)
        def t2Results = new SegmentResult(raceSegment: t2Segment, duration: Duration.standardSeconds(52))
        def runResults = new SegmentResult(raceSegment: fiveKilometerRunSegment, duration: Duration.standardSeconds(60 * 20 + 17), placeOverall: 27, placeAgeGroup: 5)

        def raceResults = new RaceResults(race: race, placeAgeGroup: 3, placeOverall: 12, user: user, duration: Duration.standardSeconds(83 * 60 + 17)).save()
        raceResults.addToSegmentResults(swimResults)
        raceResults.addToSegmentResults(t1Results)
        raceResults.addToSegmentResults(bikeResults)
        raceResults.addToSegmentResults(t2Results)
        raceResults.addToSegmentResults(runResults)
        raceResults.save()

        def race2 = new Race(name: 'Rice Street Mile', date: new Date(112, 6, 26),
                             raceType: RaceType.Running, distanceType: DistanceType.Miles,
                             distance: 1, raceCategoryType: RaceCategoryType.OneMile).save()
        def oneMileRunSegment = new RaceSegment(race: race2, segment: oneMileRun).save()

        def rsmResults = new SegmentResult(raceSegment: oneMileRunSegment, duration: Duration.standardSeconds((60 * 5) + 28))
        def rsmRaceResults = new RaceResults(race: race2, placeAgeGroup: 20, placeOverall: 50, user: user, duration: Duration.standardSeconds((60 * 5) + 28)).save()
        rsmRaceResults.addToSegmentResults(rsmResults)
        rsmRaceResults.save()

        def race3 = new Race(name: 'TC 1 Mile', date: new Date(112, 5, 17), raceType: RaceType.Running,
                             distanceType: DistanceType.Miles, distance: 1,
                             raceCategoryType: RaceCategoryType.OneMile).save()
        def oneMileRunSegment2 = new RaceSegment(race: race3, segment: oneMileRun).save()

        def tcmResults = new SegmentResult(raceSegment: oneMileRunSegment2, duration: Duration.standardSeconds((60 * 5) + 31))
        def tcmRaceResults = new RaceResult(race: race3, placeAgeGroup: 22, placeOverall: 57, user: user, duration: Duration.standardSeconds((60 * 5) + 31)).save()
        tcmRaceResults.addToSegmentResults(tcmResults)
        tcmRaceResults.save()

        def torchlight = new Race(name: 'Torchlight 5k', date: new Date(112, 5, 12), raceType: RaceType.Running,
                             distanceType: DistanceType.Kilometers, distance: 5,
                             raceCategoryType: RaceCategoryType.FifteenKilometer).save()
        def torchlightSegment = new RaceSegment(race: torchlight, segment: fiveKilometerRun).save()
    }

    def destroy = {
    }
}
