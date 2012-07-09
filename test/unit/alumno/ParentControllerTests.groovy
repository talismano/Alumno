package alumno



import org.junit.*
import grails.test.mixin.*

@TestFor(ParentController)
@Mock(Parent)
class ParentControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/parent/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.parentInstanceList.size() == 0
        assert model.parentInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.parentInstance != null
    }

    void testSave() {
        controller.save()

        assert model.parentInstance != null
        assert view == '/parent/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/parent/show/1'
        assert controller.flash.message != null
        assert Parent.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/parent/list'


        populateValidParams(params)
        def parent = new Parent(params)

        assert parent.save() != null

        params.id = parent.id

        def model = controller.show()

        assert model.parentInstance == parent
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/parent/list'


        populateValidParams(params)
        def parent = new Parent(params)

        assert parent.save() != null

        params.id = parent.id

        def model = controller.edit()

        assert model.parentInstance == parent
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/parent/list'

        response.reset()


        populateValidParams(params)
        def parent = new Parent(params)

        assert parent.save() != null

        // test invalid parameters in update
        params.id = parent.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/parent/edit"
        assert model.parentInstance != null

        parent.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/parent/show/$parent.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        parent.clearErrors()

        populateValidParams(params)
        params.id = parent.id
        params.version = -1
        controller.update()

        assert view == "/parent/edit"
        assert model.parentInstance != null
        assert model.parentInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/parent/list'

        response.reset()

        populateValidParams(params)
        def parent = new Parent(params)

        assert parent.save() != null
        assert Parent.count() == 1

        params.id = parent.id

        controller.delete()

        assert Parent.count() == 0
        assert Parent.get(parent.id) == null
        assert response.redirectedUrl == '/parent/list'
    }
}
