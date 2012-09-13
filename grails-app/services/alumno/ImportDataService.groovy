package alumno

import alumno.StudentImportHighSchoolXLS
import alumno.OnlineImportXLS

class ImportDataService {

    static Map CITY_LIST = [1:' ', 2:'MS', 3:'BC', 4:'CM', 5:'CO', 6:'CP', 7:'FE', 8:'GL', 9:'HC', 10:'LH', 11:'LA',
            12:'MP', 13:'NW', 14:'RD', 15:'RN', 16:'SA', 17:'SJ', 18:'SN', 19:'SC', 20:'SR', 21:'SV', 22:'SQ', 23:'SU']

    def importHighSchoolData() {
        StudentImportHighSchoolXLS importer = new StudentImportHighSchoolXLS('/lghsStudents.xlsx')
        def studentsMapList = importer.getStudents()
        for (it in studentsMapList) {
            Registration theReg = new Registration()
            theReg.setIpAddress(-1)
            theReg.save(flush: true)
            Student theStudent = new Student()
            theStudent.setLastName(it.lastName)
            theStudent.setFirstName(it.firstName)
            theStudent.setGrade(it.grade.toInteger())
            theReg.addToStudents(theStudent)
            theStudent.save()
            Household theHousehold = new Household()
            theHousehold.setAddress(it.householdAddress)
            theHousehold.setCity(CITY_LIST[it.city])
            theHousehold.setZip(it.zip)
            theHousehold.setState('CA')
            theHousehold.setSafehome(false)
            theReg.addToHouseholds(theHousehold)
            theHousehold.save(flush: true)
            Parent theParent = new Parent()
            theParent.setLastName(it.parentNames)
            theHousehold.addToParents(theParent)
            theParent.save()
            theHousehold.save()
            theReg.save()
        }
        //studentsMapList.each { println it}
    }

    def importOnlineData() {
        OnlineImportXLS importer = new OnlineImportXLS('/wildcats-6.xlsx')
        def registrationsMapList = importer.getRegistrations()
//        registrationsMapList.each { println it}
        def studentsMapList = importer.getStudents()
//        studentsMapList.each { println it}
        def householdsMapList = importer.getHouseholds()
 //       householdsMapList.each { println it}
        def parentsMapList = importer.getParents()
 //       parentsMapList.each { println it}

        registrationsMapList.each() {
            Registration theReg = new Registration()
            theReg.setIpAddress(it.ipAddress)
            theReg.setDateCreated(it.dateCreated)
            def regOnlineID = it.dbID
            theReg.save(flush: true)
            def listOfStudentsForThisReg = studentsMapList.findAll {studentMap -> studentMap.registrationDbID==regOnlineID}
            if (listOfStudentsForThisReg.size() > 0) {
                listOfStudentsForThisReg.each() { studentMap ->
                    Student theStudent = new Student()
                    def studentLastName = studentMap.lastName ?: " "
                    theStudent.setLastName(studentLastName)
                    def studentFirstName = studentMap.firstName ?: " "
                    theStudent.setFirstName(studentFirstName)
                    def studentGrade = studentMap.grade ?: 13.0
                    theStudent.setGrade(studentGrade.toInteger())
                    theStudent.setPhoneNumber(studentMap.phoneNumber)
                    theStudent.setEmail(studentMap.email)
                    theReg.addToStudents(theStudent)
                    theStudent.save(flush: true)
                }
                def listOfHouseholdsForThisReg = householdsMapList.findAll {householdMap -> householdMap.registrationDbID==regOnlineID}
                listOfHouseholdsForThisReg.each() { householdMap ->
                    Household theHousehold = new Household()
                    theHousehold.setAddress(householdMap.address)
                    theHousehold.setCity(CITY_LIST[householdMap.city])
                    theHousehold.setZip(householdMap.zip)
                    theHousehold.setState('CA')
                    theHousehold.setSafehome(householdMap.safeHouse!=0.0)
                    theHousehold.setPhoneNumber(householdMap.phoneNumber)
                    theReg.addToHouseholds(theHousehold)
                    theHousehold.save(flush: true)
                    def householdOnlineID = householdMap.dbID
                    def listOfParentsForThisHousehold= parentsMapList.findAll {parentMap -> parentMap.householdDbID==householdOnlineID}
                    listOfParentsForThisHousehold.each() { parentMap ->
                        Parent theParent = new Parent()
                        theParent.setLastName(parentMap.lastName)
                        theParent.setFirstName(parentMap.firstName)
                        theParent.setPhoneNumber(parentMap.phoneNumber)
                        theParent.setEmail(parentMap.email)
                        theHousehold.addToParents(theParent)
                        theParent.save()
                    }
                    theHousehold.save()
                }
            }
            theReg.save(flush: true)
        }
    }
}
