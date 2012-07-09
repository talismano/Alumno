package alumno

class Parent {

    static constraints = {
        firstName(nullable: true)
        lastName(nullable: true)
        phoneNumber(nullable: true)
        email(nullable: true)
    }

    String firstName
    String lastName
    String phoneNumber
    String email

    static belongsTo = [
            household: Household
    ]

    @Override
    public String toString ( ) {
        "$id - ${firstName} ${lastName}"
    }
}
