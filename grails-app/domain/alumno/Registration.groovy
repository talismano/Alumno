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

    @Override
    public String toString ( ) {
        "$id - Created on ${dateCreated?.format('MM-dd, HH:mm:ss')?:'Unknown'} - IP Address:${ipAddress}"
    }


}
