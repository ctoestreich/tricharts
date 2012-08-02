package com.tgid.tri.results



import org.junit.*
import grails.test.mixin.*

@TestFor(RaceResultsController)
@Mock(RaceResults)
class RaceResultsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/raceResults/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.raceResultsInstanceList.size() == 0
        assert model.raceResultsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.raceResultsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.raceResultsInstance != null
        assert view == '/raceResults/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/raceResults/show/1'
        assert controller.flash.message != null
        assert RaceResults.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/raceResults/list'

        populateValidParams(params)
        def raceResults = new RaceResults(params)

        assert raceResults.save() != null

        params.id = raceResults.id

        def model = controller.show()

        assert model.raceResultsInstance == raceResults
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/raceResults/list'

        populateValidParams(params)
        def raceResults = new RaceResults(params)

        assert raceResults.save() != null

        params.id = raceResults.id

        def model = controller.edit()

        assert model.raceResultsInstance == raceResults
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/raceResults/list'

        response.reset()

        populateValidParams(params)
        def raceResults = new RaceResults(params)

        assert raceResults.save() != null

        // test invalid parameters in update
        params.id = raceResults.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/raceResults/edit"
        assert model.raceResultsInstance != null

        raceResults.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/raceResults/show/$raceResults.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        raceResults.clearErrors()

        populateValidParams(params)
        params.id = raceResults.id
        params.version = -1
        controller.update()

        assert view == "/raceResults/edit"
        assert model.raceResultsInstance != null
        assert model.raceResultsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/raceResults/list'

        response.reset()

        populateValidParams(params)
        def raceResults = new RaceResults(params)

        assert raceResults.save() != null
        assert RaceResults.count() == 1

        params.id = raceResults.id

        controller.delete()

        assert RaceResults.count() == 0
        assert RaceResults.get(raceResults.id) == null
        assert response.redirectedUrl == '/raceResults/list'
    }
}
