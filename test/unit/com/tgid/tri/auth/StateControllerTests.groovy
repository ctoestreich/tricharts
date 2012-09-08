package com.tgid.tri.auth



import org.junit.*
import grails.test.mixin.*

@TestFor(StateController)
@Mock(State)
class StateControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/state/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.stateInstanceList.size() == 0
        assert model.stateInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.stateInstance != null
    }

    void testSave() {
        controller.save()

        assert model.stateInstance != null
        assert view == '/state/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/state/show/1'
        assert controller.flash.message != null
        assert State.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/state/list'

        populateValidParams(params)
        def state = new State(params)

        assert state.save() != null

        params.id = state.id

        def model = controller.show()

        assert model.stateInstance == state
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/state/list'

        populateValidParams(params)
        def state = new State(params)

        assert state.save() != null

        params.id = state.id

        def model = controller.edit()

        assert model.stateInstance == state
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/state/list'

        response.reset()

        populateValidParams(params)
        def state = new State(params)

        assert state.save() != null

        // test invalid parameters in update
        params.id = state.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/state/edit"
        assert model.stateInstance != null

        state.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/state/show/$state.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        state.clearErrors()

        populateValidParams(params)
        params.id = state.id
        params.version = -1
        controller.update()

        assert view == "/state/edit"
        assert model.stateInstance != null
        assert model.stateInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/state/list'

        response.reset()

        populateValidParams(params)
        def state = new State(params)

        assert state.save() != null
        assert State.count() == 1

        params.id = state.id

        controller.delete()

        assert State.count() == 0
        assert State.get(state.id) == null
        assert response.redirectedUrl == '/state/list'
    }
}
