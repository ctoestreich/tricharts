package com.tgid.tri.results



import org.junit.*
import grails.test.mixin.*

@TestFor(RaceResultController)
@Mock(RaceResult)
class RaceResultControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/raceResult/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.raceResultInstanceList.size() == 0
        assert model.raceResultInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.raceResultInstance != null
    }

    void testSave() {
        controller.save()

        assert model.raceResultInstance != null
        assert view == '/raceResult/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/raceResult/show/1'
        assert controller.flash.message != null
        assert RaceResult.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/raceResult/list'

        populateValidParams(params)
        def raceResult = new RaceResult(params)

        assert raceResult.save() != null

        params.id = raceResult.id

        def model = controller.show()

        assert model.raceResultInstance == raceResult
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/raceResult/list'

        populateValidParams(params)
        def raceResult = new RaceResult(params)

        assert raceResult.save() != null

        params.id = raceResult.id

        def model = controller.edit()

        assert model.raceResultInstance == raceResult
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/raceResult/list'

        response.reset()

        populateValidParams(params)
        def raceResult = new RaceResult(params)

        assert raceResult.save() != null

        // test invalid parameters in update
        params.id = raceResult.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/raceResult/edit"
        assert model.raceResultInstance != null

        raceResult.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/raceResult/show/$raceResult.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        raceResult.clearErrors()

        populateValidParams(params)
        params.id = raceResult.id
        params.version = -1
        controller.update()

        assert view == "/raceResult/edit"
        assert model.raceResultInstance != null
        assert model.raceResultInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/raceResult/list'

        response.reset()

        populateValidParams(params)
        def raceResult = new RaceResult(params)

        assert raceResult.save() != null
        assert RaceResult.count() == 1

        params.id = raceResult.id

        controller.delete()

        assert RaceResult.count() == 0
        assert RaceResult.get(raceResult.id) == null
        assert response.redirectedUrl == '/raceResult/list'
    }
}
