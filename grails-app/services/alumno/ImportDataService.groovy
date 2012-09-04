package alumno

import alumno.StudentImportHighSchoolXLS

class ImportDataService {

    def importHighSchoolData() {
        StudentImportHighSchoolXLS importer = new StudentImportHighSchoolXLS('/lghsStudents.xlsx')
        def studentsMapList = importer.getStudents()
        studentsMapList.each { println it}
    }
}
