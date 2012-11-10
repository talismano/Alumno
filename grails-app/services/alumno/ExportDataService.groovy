package alumno

import org.grails.plugins.csv.CSVWriter

class ExportDataService {

    def cityList = ["":"Los Gatos", "MS":"Monte Sereno", "BC":"Boulder Creek", "CM":"Campbell", "CO":"Corralitos",
            "CAP":"Capitola", "FE":"Felton", "GL":"Gilroy", "HC":"Holy City", "LH":"La Honda", "LA":"los Altos",
            "MP":"Menlo Park", "NW":"Newark", "RD":"Redwood Estates", "RN":"Reno", "SA":"Salinas", "SJ":"San Jose",
            "SN":"Santa Clara", "SC":"Santa Cruz", "SR":"Saratoga", "SV":"Scotts Valley", "SQ":"Soquel", "SU":"Sunnyvale",
            "BL":"Ben Lomond"]

    def createDistributionData() {
        def sw = new StringWriter()
        def b = new CSVWriter(sw, {
            col1:"Student Last Name" { it.val1 }
            col2:"Student First Name" { it.val2 }
            col3:"Student Grade" {it.val3}
            col4:"Parent Last Name" {it.val4}
            col5:"Parent First Name" {it.val5}
            col6:"Parent Address" {it.val6}
            col7:"Parent City" {it.val7}
            col8:"Parent State" {it.val8}
            col9:"Parent Zip" {it.val9}
        })
        def listOfStudents = Student.listOrderByLastName()
        listOfStudents.each{ student ->
            def registration =  student.getRegistration()
            if (registration.getIpAddress() != -1)  {
                def household = registration.getHouseholds()?.getAt(0)
                def parent = household.getParents()?.getAt(0)
                def cityName = cityList[household?.city]
                b << [val1: student.lastName, val2: student.firstName, val3: student.grade, val4: parent?.lastName, val5: parent?.firstName, val6:household?.address, val7:cityName?:"Los Gatos", val8:"CA", val9:household?.zip]
            }
        }
        def target ="LGHS Distribution.csv"
        File file= new File(target)
        file.write(sw.toString())
    }
}
