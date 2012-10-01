package alumno



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ImportDataService)
class ImportDataServiceTests {

    void testStrip408AreaCode() {
        def importData = new ImportDataService()
        def fourOhEightString = "(408) 555-1212"
        def sixFiveOhString = "(650) 555-1212"
        def basicString = "555-1212"
        assert basicString == importData.strip408AreaCode(fourOhEightString)
        assert sixFiveOhString == importData.strip408AreaCode(sixFiveOhString)
    }

    void testConvertToStandardPhoneFormat() {
        def importData = new ImportDataService()
        def niceString = "(408) 555-1212"
        def bunchedString = "4085551212"
        def dotString = "408.555.1212"
        def dashString = "408-555-1212"
        def noParensDashString = "408 555-1212"
        def basicString = "555-1212"
        def dotStringNoAreaCode = "555.1212"
        def bunchedStringNoAreaCode = "5551212"
        assert niceString == importData.convertToStandardPhoneFormat(niceString)
        assert niceString == importData.convertToStandardPhoneFormat(bunchedString)
        assert niceString == importData.convertToStandardPhoneFormat(dotString)
        assert niceString == importData.convertToStandardPhoneFormat(dashString)
        assert niceString == importData.convertToStandardPhoneFormat(noParensDashString)
        assert basicString == importData.convertToStandardPhoneFormat(basicString)
        assert basicString == importData.convertToStandardPhoneFormat(dotStringNoAreaCode)
        assert basicString == importData.convertToStandardPhoneFormat(bunchedStringNoAreaCode)
    }

    void testConvertToFirstCaps() {
        def importData = new ImportDataService()
        def nickname = "AJ Smith"
        def capsLockParent = "ANDREW SMITH"
        def hipsterParent = "andrew smith"
        def sloppyParent = "ANdrew SMith"
        def dutchParent = "Andrew van Smith"
        def prettyOuput = "Andrew Smith"
        assert nickname == importData.convertToFirstCaps(nickname)
        assert dutchParent == importData.convertToFirstCaps(dutchParent)
        assert prettyOuput == importData.convertToFirstCaps(capsLockParent)
        assert prettyOuput == importData.convertToFirstCaps(hipsterParent)
        assert prettyOuput != importData.convertToFirstCaps(sloppyParent)  // don't handle this case yet
    }
}
