import com.tgid.tri.auth.Role
import com.tgid.tri.auth.User
import com.tgid.tri.auth.UserRole

class BootStrap {

    def init = { servletContext ->
        def role1 = new Role(authority: 'ROLE_USER').save(failOnError: true, flush: true)
        def role2 = new Role(authority: 'ROLE_FACEBOOK').save(failOnError: true, flush: true)
        def user = new User(username: 'acetrike@yahoo.com', password: 'acetrike').save(failOnError: true, flush: true)
        new UserRole(user: user, role: role1).save(failOnError: true, flush: true)
        new UserRole(user: user, role: role2).save(failOnError: true, flush: true)
    }
    def destroy = {
    }
}
