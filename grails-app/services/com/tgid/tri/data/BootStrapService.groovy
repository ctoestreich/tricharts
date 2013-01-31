package com.tgid.tri.data

import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult
import org.joda.time.Duration
import com.tgid.tri.auth.*
import com.tgid.tri.race.*

class BootStrapService {

    def segmentService
    def userService

    void createDefaultUsers() {
        //create dome dummy users to test
        createUser(new User(username: 'acetrike@yahoo.com', password: 'acetrike', firstName: 'Christian', lastName: 'Oestreich', dob: new Date(79, 4, 23), enabled: true, genderType: GenderType.Male), true)
//        createUser(new User(username: 'acetrike@gmail.com', password: 'acetrike', firstName: 'Christian', lastName: 'Oestreich', dob: new Date(79, 4, 23), enabled: true, genderType: GenderType.Male), false)
        createUser(new User(username: 'mitchtalbot@yahoo.com', password: 'mitchtalbot', firstName: 'Mitchel', lastName: 'Talbot', dob: new Date(79, 4, 18), enabled: true, genderType: GenderType.Male), false)
        createUser(new User(username: 'bugurlu@hotmail.com', password: 'bugurlu', firstName: 'Bulent', lastName: 'Ugurlu', dob: new Date(70, 10, 18), enabled: true, genderType: GenderType.Male), true)
//        createUser(new User(username: 'kwschulz@gmail.com', password: 'kwschulz', firstName: 'Ken', lastName: 'Schulz', dob: new Date(77, 5, 18), enabled: true), true)
        createUser(new User(username: 'patrick.parish@gmail.com', password: 'pparish', firstName: 'Patrick', lastName: 'Parish', dob: new Date(85, 5, 18), enabled: true, genderType: GenderType.Male), false)
        createUser(new User(username: 'derek.haag@gmail.com', password: 'dhaag', firstName: 'Derek', lastName: 'Haag', dob: new Date(78, 8, 18), enabled: true, genderType: GenderType.Male), false)
    }

    void createUser(User user, boolean isAdmin = false) {
        try {
            if(!User.findByUsername(user.username)) {
                user.save(flush: true)
                def role_user = Role.findOrSaveWhere(authority: 'ROLE_USER')
                def role_admin = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
                new UserRole(user: user, role: role_user).save(flush: true)
                user.addToStates(State.findByAbbrev('MN'))
                if(isAdmin) {
                    new UserRole(user: user, role: role_admin).save(flush: true)
                }
                userService.lookupAthlinkRacers(user).each {
                    user.addToRacers(new Racer(racerID: it?.racerID))
                }
                user.save(flush: true)
            }
        } catch(Exception e) {
            log.error e
        }
    }

    /**
     * Year	Name	Race Name	Date	AG#	Ovr#	Time	Swim Dist	Swim Time	Swim Pace	T1	Bike Dist	Bike Time	Bike Pace	T2	Run Dist	Run Time	Run Pace	Results

     AG	Christian	Minneman	7/2/2011	4	24	1:05:46	0.3	0:07:14	0:01:23	1:56.0	13	0:34:56	22.3	0:01:05	3	0:20:37	0:06:55	http://www.pigmantri.com/jmsracing/results11/mnman11b.html



     AG	Christian	Minneapolis	7/9/2011	3	10	1:13:27	0.4	0:10:29	0:01:28	1:28.7	15	0:40:01	22.5	0:01:18	3	0:20:10	0:06:43	http://www.peaktiming.com/2011ltf/categoryresults.asp?EventId=3&DivId=16&CatId=4
     AG	Christian	Twin Cities	7/24/2011	1	4	1:06:26	0.3	0:04:03	0:00:42	1:01.0	13.74	0:39:10	21	0:00:49	3.1	0:21:26	0:06:55	http://www.pigmantri.com/jmsracing/results11/twinctri11d.html
     AG	Christian	Maple Grove	8/27/2011	1	7	1:10:26	0.3	0:09:03	0:01:43	1:12.0	14	0:38:41	21.4	0:00:48	3.1	0:20:43	0:06:40	http://www.mtecresults.com/runner/show?rid=318&race=465
     2012
     AG	Christian	Manitou	6/10/2012	4	20	1:12:22	0.5	0:12:57	0:01:29	1:57.0	13.5	0:36:04	22.5	0:00:50	3.1	0:20:36	0:06:39	http://www.pigmantri.com/jmsracing/results12/man12b.html
     AG	Christian	Minnetonka	6/16/2012	3	20	1:11:57	0.5	0:11:26	0:01:25	1:35.0	15	0:38:51	22.4	0:00:39	3	0:19:28	0:06:30	http://www.mtecresults.com/race/show/837
     E	Christian	Minneman	6/30/2012	4	15	1:10:36	0.5	0:11:32	0:01:19	1:43.0	13.3	0:35:24	22.5	0:01:01	3.1	0:20:58	0:06:46	http://www.pigmantri.com/jmsracing/results12/mnman12d.html
     AG	Christian	Graniteman	7/14/2012	3	12	1:13:17	0.5	0:11:33	0:01:26	0:23.0	15	0:40:15	22.4	0:00:52	3.1	0:20:17	0:06:33	http://live.mtecresults.com/race/show/942
     */
    private createMinneman2011() {
        def race = new Race(name: 'Minneman', date: new Date(111, 6, 2), raceType: RaceType.Triathlon,
                            distanceType: DistanceType.Miles,
                            distance: 17.24,
                            raceCategoryType: RaceCategoryType.Sprint, statusType: StatusType.Approved,
                            resultsUrl: 'http://www.pigmantri.com/jmsracing/results11/mnman11b.html').save()

        def swimRaceSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0.3, DistanceType.Miles, SegmentType.Swim))
        def t1Segment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0, DistanceType.Yards, SegmentType.T1))
        def fifteenMileBikeSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(13, DistanceType.Miles, SegmentType.Bike))
        def t2Segment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0, DistanceType.Yards, SegmentType.T2)).save()
        def fiveKilometerRun = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(3, DistanceType.Miles, SegmentType.Run))

        def christian = User.findByUsername('acetrike@yahoo.com')

        createTriathlon(race, christian, swimRaceSegment, t1Segment, fifteenMileBikeSegment, t2Segment, fiveKilometerRun,
                        65 * 60 + 46, 7 * 60 + 14, 116, 34 * 60 + 56, 65, 20 * 60 + 37,
                        18, 2, 32, 8, 32, 5,
                        24, 4,
                        342, 175, 16)
    }

    private void createManitou2011() {
        def race = new Race(name: 'Manitou', date: new Date(111, 5, 12), raceType: RaceType.Triathlon,
                            distanceType: DistanceType.Miles,
                            distance: 17.24,
                            raceCategoryType: RaceCategoryType.Sprint, statusType: StatusType.Approved,
                            resultsUrl: 'http://www.pigmantri.com/jmsracing/results11/man11b.html').save()

        def swimRaceSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0.5, DistanceType.Miles, SegmentType.Swim))
        def t1Segment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0, DistanceType.Yards, SegmentType.T1))
        def fifteenMileBikeSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(13.5, DistanceType.Miles, SegmentType.Bike))
        def t2Segment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0, DistanceType.Yards, SegmentType.T2)).save()
        def fiveKilometerRun = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(5, DistanceType.Kilometers, SegmentType.Run))

        def christian = User.findByUsername('acetrike@yahoo.com')
        def mitch = User.findByUsername('mitchtalbot@yahoo.com')

        createTriathlon(race, mitch, swimRaceSegment, t1Segment, fifteenMileBikeSegment, t2Segment, fiveKilometerRun,
                        82 * 60 + 4, 18 * 60 + 40, 128, 37 * 60 + 8, 53, 23 * 60 + 07,
                        32, 15, 42, 5, 87, 10,
                        70, 7,
                        251, 165, 22)

        createTriathlon(race, christian, swimRaceSegment, t1Segment, fifteenMileBikeSegment, t2Segment, fiveKilometerRun,
                        60 * 74 + 7, 14 * 60 + 14, 131, 36 * 60 + 23, 53, 20 * 60 + 30,
                        25, 2, 32, 3, 33, 6,
                        13, 2,
                        251, 165, 22)
    }

    private createManitou2012() {
        def race = new Race(name: 'Manitou', date: new Date(112, 5, 10), raceType: RaceType.Triathlon,
                            distanceType: DistanceType.Miles,
                            distance: 17.24,
                            raceCategoryType: RaceCategoryType.Sprint, statusType: StatusType.Approved,
                            resultsUrl: 'http://www.pigmantri.com/jmsracing/results12/man12b.html').save()

        def swimRaceSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0.5, DistanceType.Miles, SegmentType.Swim))
        def t1Segment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0, DistanceType.Yards, SegmentType.T1))
        def fifteenMileBikeSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(13.5, DistanceType.Miles, SegmentType.Bike))
        def t2Segment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0, DistanceType.Yards, SegmentType.T2)).save()
        def fiveKilometerRun = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(5, DistanceType.Kilometers, SegmentType.Run))

        def christian = User.findByUsername('acetrike@yahoo.com')

        createTriathlon(race, christian, swimRaceSegment, t1Segment, fifteenMileBikeSegment, t2Segment, fiveKilometerRun,
                        60 * 72 + 22, 12 * 60 + 57, 117, 36 * 60 + 04, 50, 20 * 60 + 36,
                        17, 3, 26, 5, 39, 7,
                        20, 4,
                        308, 180, 30)
    }

    private void createStCroixValley() {
        def race = new Race(name: 'St Croix Valley', date: new Date(110, 8, 4), raceType: RaceType.Triathlon,
                            distanceType: DistanceType.Miles,
                            distance: 17.24,
                            raceCategoryType: RaceCategoryType.Sprint, statusType: StatusType.Approved,
                            resultsUrl: 'http://www.onlineraceresults.com/race/view_plain_text.php?race_id=15976').save()

        def swimRaceSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0.33, DistanceType.Miles, SegmentType.Swim))
        def t1Segment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0, DistanceType.Yards, SegmentType.T1))
        def fifteenMileBikeSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(10, DistanceType.Miles, SegmentType.Bike))
        def t2Segment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0, DistanceType.Yards, SegmentType.T2)).save()
        def fourKilometerRun = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(4, DistanceType.Miles, SegmentType.Run))

        def christian = User.findByUsername('acetrike@yahoo.com')
        def mitch = User.findByUsername('mitchtalbot@yahoo.com')

        createTriathlon(race, mitch, swimRaceSegment, t1Segment, fifteenMileBikeSegment, t2Segment, fourKilometerRun,
                        60 * 79 + 19, 11 * 60 + 2, 57, 29 * 60 + 15, 57, 28 * 60 + 10,
                        59, 19, 5, 1, 31, 14,
                        13, 7,
                        344, 201, 80)

        createTriathlon(race, christian, swimRaceSegment, t1Segment, fifteenMileBikeSegment, t2Segment, fourKilometerRun,
                        60 * 81, 8 * 60 + 56, 75, 30 * 60 + 24, 73, 29 * 60 + 14,
                        11, 2, 25, 12, 52, 21,
                        13, 9,
                        344, 201, 80)
    }

    private void createTriathlon(Race race, User user, RaceSegment swimSegment, RaceSegment t1Segment, RaceSegment bikeSegment, RaceSegment t2Segment, RaceSegment runSegment,
                                 long raceSeconds, long swimSeconds, long t1Seconds, long bikeSeconds, long t2Seconds, long runSeconds,
                                 int swimPlaceOverall, int swimPlaceAgeGroup, int bikePlaceOverall, int bikePlaceAgeGroup, int runPlaceOverall, int runPlaceAgeGroup,
                                 int placeOverall, int placeAgeGroup,
                                 int participantsOverall, int participantsGender, int participantsAgeGroup
    ) {
        def swimResults = new SegmentResult(raceSegment: swimSegment, duration: Duration.standardSeconds(swimSeconds), placeOverall: swimPlaceOverall, placeAgeGroup: swimPlaceAgeGroup)
        def t1Results = new SegmentResult(raceSegment: t1Segment, duration: Duration.standardSeconds(t1Seconds))
        def bikeResults = new SegmentResult(raceSegment: bikeSegment, duration: Duration.standardSeconds(bikeSeconds), placeOverall: bikePlaceOverall, placeAgeGroup: bikePlaceAgeGroup)
        def t2Results = new SegmentResult(raceSegment: t2Segment, duration: Duration.standardSeconds(t2Seconds))
        def runResults = new SegmentResult(raceSegment: runSegment, duration: Duration.standardSeconds(runSeconds), placeOverall: runPlaceOverall, placeAgeGroup: runPlaceAgeGroup)

        def raceResult = new RaceResult(age: 33,race: race, placeAgeGroup: placeAgeGroup, placeOverall: placeOverall, user: user,
                                        participantsAgeGroup: participantsAgeGroup, participantsGender: participantsGender, participantsOverall: participantsOverall,
                                        duration: Duration.standardSeconds(raceSeconds)).save()
        raceResult.addToSegmentResults(swimResults)
        raceResult.addToSegmentResults(t1Results)
        raceResult.addToSegmentResults(bikeResults)
        raceResult.addToSegmentResults(t2Results)
        raceResult.addToSegmentResults(runResults)
        raceResult.save()
    }

    public void seedResults() {
        //runs
        createRiceStreetMile()
        createTcOneMile()
        createTorchlight()
        createVictoryDay()
        //tris
        createStCroixValley()
        createManitou2011()
        createMinneman2011()
        createManitou2012()
        createGraniteman()
    }

    private void createGraniteman() {
        def race = new Race(name: 'Graniteman Triathlon', date: new Date(112, 6, 17), raceType: RaceType.Triathlon,
                            distanceType: DistanceType.Miles,
                            distance: 17.24,
                            raceCategoryType: RaceCategoryType.Sprint, statusType: StatusType.Approved,
                            resultsUrl: 'http://live.mtecresults.com/race/show/942').save()

        def swimRaceSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0.5, DistanceType.Miles, SegmentType.Swim))
        def t1Segment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0, DistanceType.Yards, SegmentType.T1))
        def fifteenMileBikeSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(15, DistanceType.Miles, SegmentType.Bike))
        def t2Segment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(0, DistanceType.Yards, SegmentType.T2)).save()
        def fiveKilometerRunSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(5, DistanceType.Kilometers, SegmentType.Run))

        def swimResults = new SegmentResult(raceSegment: swimRaceSegment, duration: Duration.standardSeconds(11 * 60 + 33), placeOverall: 18, placeAgeGroup: 3)
        def t1Results = new SegmentResult(raceSegment: t1Segment, duration: Duration.standardSeconds(23))
        def bikeResults = new SegmentResult(raceSegment: fifteenMileBikeSegment, duration: Duration.standardSeconds(40 * 60 + 15), placeOverall: 25, placeAgeGroup: 4)
        def t2Results = new SegmentResult(raceSegment: t2Segment, duration: Duration.standardSeconds(52))
        def runResults = new SegmentResult(raceSegment: fiveKilometerRunSegment, duration: Duration.standardSeconds(60 * 20 + 17), placeOverall: 27, placeAgeGroup: 5)

        def raceResult = new RaceResult(age: 31,race: race, placeAgeGroup: 3, placeOverall: 12, user: User.findByUsername('acetrike@yahoo.com'), duration: Duration.standardSeconds(83 * 60 + 17)).save()
        raceResult.addToSegmentResults(swimResults)
        raceResult.addToSegmentResults(t1Results)
        raceResult.addToSegmentResults(bikeResults)
        raceResult.addToSegmentResults(t2Results)
        raceResult.addToSegmentResults(runResults)
        raceResult.save()
    }

    private void createRiceStreetMile() {
        def riceStreetMile = new Race(state: State.findByAbbrev('MN'), name: 'Rice Street Mile', date: new Date(112, 6, 26),
                                      raceType: RaceType.Running, distanceType: DistanceType.Miles,
                                      distance: 1, raceCategoryType: RaceCategoryType.OneMile, statusType: StatusType.Approved).save()
        def oneMileRunSegment = RaceSegment.findOrSaveWhere(race: riceStreetMile, segment: segmentService.findOrSaveSegment(1, DistanceType.Miles, SegmentType.Run))

        def rsmResults = new SegmentResult(raceSegment: oneMileRunSegment, duration: Duration.standardSeconds((60 * 5) + 28))
        def rsmRaceResult = new RaceResult(age: 30,race: riceStreetMile, placeAgeGroup: 20, placeOverall: 50, user: User.findByUsername('acetrike@yahoo.com'), duration: Duration.standardSeconds((60 * 5) + 28)).save()
        rsmRaceResult.addToSegmentResults(rsmResults)
        rsmRaceResult.save()
    }

    private void createTcOneMile() {
        def twinCitiesMile = new Race(state: State.findByAbbrev('MN'), name: 'Medtronic TC1 Mile', date: new Date(112, 5, 17), raceType: RaceType.Running,
                                      distanceType: DistanceType.Miles, distance: 1,
                                      raceCategoryType: RaceCategoryType.OneMile, statusType: StatusType.Approved).save()
        def raceSegment = RaceSegment.findOrSaveWhere(race: twinCitiesMile, segment: segmentService.findOrSaveSegment(1, DistanceType.Miles, SegmentType.Run))

        def segmentResult = new SegmentResult(raceSegment: raceSegment, duration: Duration.standardSeconds((60 * 5) + 31))
        def raceResult = new RaceResult(age: 34,race: twinCitiesMile, placeAgeGroup: 29, placeGender: 179, placeOverall: 192, user: User.findByUsername('acetrike@yahoo.com'), duration: Duration.standardSeconds((60 * 5) + 31)).save()
        raceResult.addToSegmentResults(segmentResult)
        raceResult.save()
    }

    private void createVictoryDay() {
        def race = new Race(state: State.findByAbbrev('MN'), name: 'MDRA Victory 5K', date: new Date(111, 8, 5), raceType: RaceType.Running,
                            distanceType: DistanceType.Kilometers, distance: 5,
                            raceCategoryType: RaceCategoryType.FiveKilometer, statusType: StatusType.Approved).save()
        def raceSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(5, DistanceType.Kilometers, SegmentType.Run))
        def segmentResult = new SegmentResult(raceSegment: raceSegment, duration: Duration.standardSeconds((60 * 19) + 05))
        def raceResult = new RaceResult(age: 35,race: race, placeAgeGroup: 2, placeGender: 20, placeOverall: 20, user: User.findByUsername('acetrike@yahoo.com'), duration: Duration.standardSeconds((60 * 19) + 05)).save()
        raceResult.addToSegmentResults(segmentResult)
        raceResult.save()

        race = new Race(name: 'MDRA Victory 10K', date: new Date(111, 8, 5), raceType: RaceType.Running,
                        distanceType: DistanceType.Kilometers, distance: 10,
                        raceCategoryType: RaceCategoryType.TenKilometer, statusType: StatusType.Approved).save()
        raceSegment = RaceSegment.findOrSaveWhere(race: race, segment: segmentService.findOrSaveSegment(10, DistanceType.Kilometers, SegmentType.Run))
        segmentResult = new SegmentResult(raceSegment: raceSegment, duration: Duration.standardSeconds((60 * 45) + 52))
        raceResult = new RaceResult(age: 36,race: race, placeAgeGroup: 24, placeGender: 150, placeOverall: 154, user: User.findByUsername('bugurlu@hotmail.com'), duration: Duration.standardSeconds((60 * 45) + 52)).save()
        raceResult.addToSegmentResults(segmentResult)
        raceResult.save()
    }

    private void createTorchlight() {
        def torchlight = new Race(name: 'Lifetime Torchlight 5K - USATF Minnesota 5K Championship', date: new Date(112, 6, 18), raceType: RaceType.Running,
                                  distanceType: DistanceType.Kilometers, distance: 5,
                                  raceCategoryType: RaceCategoryType.FiveKilometer, statusType: StatusType.Approved).save()
        def raceSegment = RaceSegment.findOrSaveWhere(race: torchlight, segment: segmentService.findOrSaveSegment(5, DistanceType.Kilometers, SegmentType.Run))
        def segmentResult = new SegmentResult(raceSegment: raceSegment, duration: Duration.standardSeconds((60 * 19) + 32))
        def raceResult = new RaceResult(age: 37,race: torchlight, placeAgeGroup: 16, placeGender: 90, placeOverall: 116, user: User.findByUsername('acetrike@yahoo.com'), duration: Duration.standardSeconds((60 * 19) + 32)).save()
        raceResult.addToSegmentResults(segmentResult)
        raceResult.save()
    }
}
