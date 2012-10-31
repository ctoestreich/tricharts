import com.tgid.tri.auth.UserSport
import com.tgid.tri.auth.User
import com.tgid.tri.race.RaceType

databaseChangeLog = {

    changeSet(author: "coestre (generated)", id: "1348848599558-1") {
        grailsChange {
            change {
                User.list().each { user ->
                    UserSport userSport = new UserSport(user: user)
                    userSport.sports.addAll([RaceType.Triathlon, RaceType.Running])
                    userSport.save(failOnError: true, flush: true)
                }
            }
        }
    }
}
