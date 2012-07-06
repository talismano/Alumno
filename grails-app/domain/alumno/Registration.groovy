package alumno

class Registration {

    static constraints = {
    }

    Date dateCreated
    String ipAddress

    static hasMany = [
            students: Student,
            households: Household
    ]

}
