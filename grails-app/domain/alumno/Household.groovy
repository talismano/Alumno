package alumno


class Household {

    static constraints = {
        address(nullable: true)
        city(nullable: true)
        state(nullable: true)
        zip(nullable: true)
        phoneNumber(nullable: true)
    }

    String address
    String city
    String state
    Integer zip
    Boolean safehome
    String phoneNumber

    static belongsTo = [
            registration: Registration
    ]

    static hasMany = [
            parents: Parent
    ]
}
