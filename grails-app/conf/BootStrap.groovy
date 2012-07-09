
import alumno.User
import alumno.UserRole
import alumno.Role

class BootStrap {
    def springSecurityService


    def init = { servletContext ->

        def adminUser = User.findByUsername('admin') ?: new User(
                username: 'admin',
                password: 'lghsdirectory',
                enabled: true).save(failOnError: true)

        def adminRole = Role.findByAuthority("ROLE_ADMIN") ?: new Role(authority: "ROLE_ADMIN").save()

        if (!adminUser.authorities.contains(ROLE_ADMIN)) {
            UserRole.create adminUser, adminRole
        }

    }
    def destroy = {
    }
}
