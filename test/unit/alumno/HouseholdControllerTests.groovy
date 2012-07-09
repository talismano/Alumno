package alumno



import org.junit.*
import grails.test.mixin.*

@TestFor(HouseholdController)
@Mock(Household)
class HouseholdControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/household/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.householdInstanceList.size() == 0
        assert model.householdInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.householdInstance != null
    }

    void testSave() {
        controller.save()

        assert model.householdInstance != null
        assert view == '/household/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/household/show/1'
        assert controller.flash.message != null
        assert Household.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/household/list'


        populateValidParams(params)
        def household = new Household(params)

        assert household.save() != null

        params.id = household.id

        def model = controller.show()

        assert model.householdInstance == household
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/household/list'


        populateValidParams(params)
        def household = new Household(params)

        assert household.save() != null

        params.id = household.id

        def model = controller.edit()

        assert model.householdInstance == household
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/household/list'

        response.reset()


        populateValidParams(params)
        def household = new Household(params)

        assert household.save() != null

        // test invalid parameters in update
        params.id = household.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/household/edit"
        assert model.householdInstance != null

        household.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/household/show/$household.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        household.clearErrors()

        populateValidParams(params)
        params.id = household.id
        params.version = -1
        controller.update()

        assert view == "/household/edit"
        assert model.householdInstance != null
        assert model.householdInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/household/list'

        response.reset()

        populateValidParams(params)
        def household = new Household(params)

        assert household.save() != null
        assert Household.count() == 1

        params.id = household.id

        controller.delete()

        assert Household.count() == 0
        assert Household.get(household.id) == null
        assert response.redirectedUrl == '/household/list'
    }
}
