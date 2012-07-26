import com.tgid.tri.auth.Role

class BootStrap {

    def init = { servletContext ->
        new Role(authority: 'ROLE_USER').save(failOnError: true, flush: true)
        new Role(authority: 'ROLE_FACEBOOK').save(failOnError: true, flush: true)
    }
    def destroy = {
    }
}
