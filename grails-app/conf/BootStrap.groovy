
import alumno.User
import alumno.UserRole

class BootStrap {
    def springSecurityService


    def init = { servletContext ->

        def adminUser = User.findByUsername('admin') ?: new User(
                username: 'admin',
                password: 'lghsdirectory',
                enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) {
            UserRole.create adminUser, ROLE_ADMIN
        }

    }
    def destroy = {
    }
}
