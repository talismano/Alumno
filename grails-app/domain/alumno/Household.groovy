package alumno

import org.apache.commons.collections.list.LazyList
import org.apache.commons.collections.FactoryUtils


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
    String zip
    Boolean safehome
    String phoneNumber
    List parents = new ArrayList()
    boolean deleted
    static transients = [ 'deleted' ]

    static belongsTo = [
            registration: Registration
    ]

    static hasMany = [
            parents: Parent
    ]

    @Override
    public String toString ( ) {
        "$id - ${address}, ${city}"
    }

}
