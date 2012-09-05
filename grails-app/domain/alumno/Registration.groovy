package alumno

import org.apache.commons.collections.list.LazyList
import org.apache.commons.collections.FactoryUtils

class Registration {

    static constraints = {
    }

    Date dateCreated
    String ipAddress
    List students = new ArrayList()
    List households = new ArrayList()

    static hasMany = [
            students: Student,
            households: Household
    ]

    static mapping = {
        students cascade:"all-delete-orphan"
        households cascase:"all-delete-orphan"
    }

    def getStudentsList() {
        return LazyList.decorate(
                students,
                FactoryUtils.instantiateFactory(Student.class))
    }

    def getHouseholdsList() {
        return LazyList.decorate(
                households,
                FactoryUtils.instantiateFactory(Household.class))
    }

    @Override
    public String toString ( ) {
        "$id - Created on ${dateCreated?.format('MM-dd, HH:mm:ss')?:'Unknown'} - IP Address:${ipAddress}"
    }

}
