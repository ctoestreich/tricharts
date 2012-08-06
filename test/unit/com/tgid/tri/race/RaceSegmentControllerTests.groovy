package com.tgid.tri.race



import org.junit.*
import grails.test.mixin.*

@TestFor(RaceSegmentController)
@Mock(RaceSegment)
class RaceSegmentControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/raceSegment/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.raceSegmentInstanceList.size() == 0
        assert model.raceSegmentInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.raceSegmentInstance != null
    }

    void testSave() {
        controller.save()

        assert model.raceSegmentInstance != null
        assert view == '/raceSegment/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/raceSegment/show/1'
        assert controller.flash.message != null
        assert RaceSegment.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/raceSegment/list'

        populateValidParams(params)
        def raceSegment = new RaceSegment(params)

        assert raceSegment.save() != null

        params.id = raceSegment.id

        def model = controller.show()

        assert model.raceSegmentInstance == raceSegment
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/raceSegment/list'

        populateValidParams(params)
        def raceSegment = new RaceSegment(params)

        assert raceSegment.save() != null

        params.id = raceSegment.id

        def model = controller.edit()

        assert model.raceSegmentInstance == raceSegment
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/raceSegment/list'

        response.reset()

        populateValidParams(params)
        def raceSegment = new RaceSegment(params)

        assert raceSegment.save() != null

        // test invalid parameters in update
        params.id = raceSegment.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/raceSegment/edit"
        assert model.raceSegmentInstance != null

        raceSegment.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/raceSegment/show/$raceSegment.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        raceSegment.clearErrors()

        populateValidParams(params)
        params.id = raceSegment.id
        params.version = -1
        controller.update()

        assert view == "/raceSegment/edit"
        assert model.raceSegmentInstance != null
        assert model.raceSegmentInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/raceSegment/list'

        response.reset()

        populateValidParams(params)
        def raceSegment = new RaceSegment(params)

        assert raceSegment.save() != null
        assert RaceSegment.count() == 1

        params.id = raceSegment.id

        controller.delete()

        assert RaceSegment.count() == 0
        assert RaceSegment.get(raceSegment.id) == null
        assert response.redirectedUrl == '/raceSegment/list'
    }
}
