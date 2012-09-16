package alumno

/**
 * Created with IntelliJ IDEA.
 * User: gmorris
 * Date: 9/4/12
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */

import org.grails.plugins.excelimport.AbstractExcelImporter
import org.grails.plugins.excelimport.ExpectedPropertyType
import org.grails.plugins.excelimport.ExcelImportService

class OnlineImportXLS extends AbstractExcelImporter {

    static Map CONFIG_REGISTRATION_COLUMN_MAP = [sheet:'registration', startRow: 0, columnMap:[ 'A' : 'dbID','B':'dateCreated', 'C':'ipAddress']]

    static Map propertyRegistrationConfigurationMap = [
            dbID:([expectedType: ExpectedPropertyType.IntType, defaultValue:null]),
            dateCreated:([expectedType: ExpectedPropertyType.DateJavaType, defaultValue:null]),
            ipAddress:([expectedType: ExpectedPropertyType.IntType, defaultValue:null])]

    static Map CONFIG_STUDENT_COLUMN_MAP = [sheet:'students', startRow: 0, columnMap:[ 'A' : 'dbID','B':'lastName', 'C':'firstName', 'D':'grade',
            'E':'phoneNumber', 'F':'email', 'G':'registrationDbID']]

    static Map propertyStudentConfigurationMap = [
            dbID:([expectedType: ExpectedPropertyType.IntType, defaultValue:null]),
            lastName:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            firstName:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            grade:([expectedType: ExpectedPropertyType.IntType, defaultValue:null]),
            phoneNumber:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            email:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            registrationDbID:([expectedType: ExpectedPropertyType.IntType, defaultValue:null])]

    static Map CONFIG_HOUSEHOLD_COLUMN_MAP = [sheet:'households', startRow: 0, columnMap:[ 'A' : 'dbID','B':'address', 'C':'city', 'E':'zip',
            'F':'safeHouse', 'G':'phoneNumber', 'H':'registrationDbID']]

    static Map propertyHouseholdConfigurationMap = [
            dbID:([expectedType: ExpectedPropertyType.IntType, defaultValue:null]),
            address:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            city:([expectedType: ExpectedPropertyType.IntType, defaultValue:null]),
            zip:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            safeHouse:([expectedType: ExpectedPropertyType.IntType, defaultValue:null]),
            phoneNumber:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            registrationDbID:([expectedType: ExpectedPropertyType.IntType, defaultValue:null])]

    static Map CONFIG_PARENT_COLUMN_MAP = [sheet:'parents', startRow: 0, columnMap:[ 'A' : 'dbID','B':'lastName', 'C':'firstName', 'D':'phoneNumber',
            'E':'email', 'F':'householdDbID']]

    static Map propertyParentConfigurationMap = [
            dbID:([expectedType: ExpectedPropertyType.IntType, defaultValue:null]),
            lastName:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            firstName:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            phoneNumber:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            email:([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
            householdDbID:([expectedType: ExpectedPropertyType.IntType, defaultValue:null])]


    public OnlineImportXLS(fileName){
        super(fileName)
    }

    List<Map> getRegistrations(){
        List registrationList = ExcelImportService.service.convertColumnMapConfigManyRows(workbook,CONFIG_REGISTRATION_COLUMN_MAP,null,null,propertyRegistrationConfigurationMap)
        return registrationList
    }

    List<Map> getStudents(){
        List studentList = ExcelImportService.service.convertColumnMapConfigManyRows(workbook,CONFIG_STUDENT_COLUMN_MAP,null,null,propertyStudentConfigurationMap)
        return studentList
    }

    List<Map> getHouseholds(){
        List householdList = ExcelImportService.service.convertColumnMapConfigManyRows(workbook,CONFIG_HOUSEHOLD_COLUMN_MAP,null,null,propertyHouseholdConfigurationMap)
        return householdList
    }

    List<Map> getParents(){
        List parentList = ExcelImportService.service.convertColumnMapConfigManyRows(workbook,CONFIG_PARENT_COLUMN_MAP,null,null,propertyParentConfigurationMap)
        return parentList
    }
}