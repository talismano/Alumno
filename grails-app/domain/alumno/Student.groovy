package alumno

class Student {

    static constraints = {
        phoneNumber(nullable: true)
        email(nullable: true)
      }

    String firstName
    String lastName
    Integer grade
    String phoneNumber
    String email

    static belongsTo = [
            registration: Registration
    ]
}
