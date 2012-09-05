package alumno

import alumno.StudentImportHighSchoolXLS

class ImportDataService {

    def importHighSchoolData() {
        StudentImportHighSchoolXLS importer = new StudentImportHighSchoolXLS('/lghsStudents.xlsx')
        def studentsMapList = importer.getStudents()
        studentsMapList.each { println it}
    }

    def importOnlineData() {
        OnlineImportXLS importer = new OnlineImportXLS('/wildcats-6.xlsx')
        def registrationsMapList = importer.getRegistrations()
        registrationsMapList.each { println it}
        def studentsMapList = importer.getStudents()
        studentsMapList.each { println it}
        def householdsMapList = importer.getHouseholds()
        householdsMapList.each { println it}
        def parentsMapList = importer.getParents()
        parentsMapList.each { println it}
    }
}
