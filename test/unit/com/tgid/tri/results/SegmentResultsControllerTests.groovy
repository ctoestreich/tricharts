package com.tgid.tri.results

import grails.test.mixin.*

@TestFor(SegmentResultsController)
@Mock(SegmentResult)
class SegmentResultsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/segmentResults/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.segmentResultsInstanceList.size() == 0
        assert model.segmentResultsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.segmentResultsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.segmentResultsInstance != null
        assert view == '/segmentResults/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/segmentResults/show/1'
        assert controller.flash.message != null
        assert SegmentResult.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/segmentResults/list'

        populateValidParams(params)
        def segmentResults = new SegmentResult(params)

        assert segmentResults.save() != null

        params.id = segmentResults.id

        def model = controller.show()

        assert model.segmentResultsInstance == segmentResults
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/segmentResults/list'

        populateValidParams(params)
        def segmentResults = new SegmentResult(params)

        assert segmentResults.save() != null

        params.id = segmentResults.id

        def model = controller.edit()

        assert model.segmentResultsInstance == segmentResults
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/segmentResults/list'

        response.reset()

        populateValidParams(params)
        def segmentResults = new SegmentResult(params)

        assert segmentResults.save() != null

        // test invalid parameters in update
        params.id = segmentResults.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/segmentResults/edit"
        assert model.segmentResultsInstance != null

        segmentResults.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/segmentResults/show/$segmentResults.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        segmentResults.clearErrors()

        populateValidParams(params)
        params.id = segmentResults.id
        params.version = -1
        controller.update()

        assert view == "/segmentResults/edit"
        assert model.segmentResultsInstance != null
        assert model.segmentResultsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/segmentResults/list'

        response.reset()

        populateValidParams(params)
        def segmentResults = new SegmentResult(params)

        assert segmentResults.save() != null
        assert SegmentResult.count() == 1

        params.id = segmentResults.id

        controller.delete()

        assert SegmentResult.count() == 0
        assert SegmentResult.get(segmentResults.id) == null
        assert response.redirectedUrl == '/segmentResults/list'
    }
}
