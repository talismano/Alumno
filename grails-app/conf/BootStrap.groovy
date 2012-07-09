class BootStrap {
    def springSecurityService


    def init = { servletContext ->

        def adminUser = SecUser.findByUsername('admin') ?: new SecUser(
                username: 'admin',
                password: 'lghsdirectory',
                enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) {
            SecUserSecRole.create adminUser, adminRole
        }


    }
    def destroy = {
    }
}
