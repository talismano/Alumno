package alumno

/**
 * Created with IntelliJ IDEA.
 * User: gmorris
 * Date: 9/3/12
 * Time: 8:10 PM
 * To change this template use File | Settings | File Templates.
 */

import org.grails.plugins.excelimport.AbstractExcelImporter
import org.grails.plugins.excelimport.ExpectedPropertyType
import org.grails.plugins.excelimport.ExcelImportService

class StudentImportHighSchoolXLS extends AbstractExcelImporter {

    static Map CONFIG_STUDENT_COLUMN_MAP = [sheet:'Query1', startRow: 2, columnMap:[ 'C' : 'lastName','D':'firstName', 'E':'grade' ,
            'F' :'homePhone', 'G': 'parentNames', 'H': 'householdAddress', 'I': 'city', 'K': 'zip']]

    static Map propertyConfigurationMap = [
            lastName:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            firstName:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            grade:([expectedType: ExpectedPropertyType.IntType, defaultValue:13]),
            homePhone:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            parentNames:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            householdAddress:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            city:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            zip:([expectedType: ExpectedPropertyType.IntType, defaultValue:95030])]

    public StudentImportHighSchoolXLS(fileName){
        super(fileName)
    }

    List<Map> getStudents(){
        List studentList = ExcelImportService.service.convertColumnMapConfigManyRows(workbook,CONFIG_STUDENT_COLUMN_MAP,null,null,propertyConfigurationMap)
        return studentList
    }
}