package alumno

class Student {

    static constraints = {
        firstName()
        lastName()
        grade()
        phoneNumber(nullable: true)
        email(nullable: true)
        grade(min:9, max:13) //13 in case parent didn't enter grade
      }

    String firstName
    String lastName
    Integer grade
    String phoneNumber
    String email
    boolean deleted
    static transients = [ 'deleted' ]

    static belongsTo = [
            registration: Registration
    ]

    @Override
    public String toString ( ) {
        "$id - ${firstName} ${lastName}, ${grade}th grade, ${phoneNumber}, ${email}"
    }

}
