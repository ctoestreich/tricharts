package com.tgid.tri.results



import org.junit.*
import grails.test.mixin.*

@TestFor(SegmentResultController)
@Mock(SegmentResult)
class SegmentResultControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/segmentResult/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.segmentResultInstanceList.size() == 0
        assert model.segmentResultInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.segmentResultInstance != null
    }

    void testSave() {
        controller.save()

        assert model.segmentResultInstance != null
        assert view == '/segmentResult/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/segmentResult/show/1'
        assert controller.flash.message != null
        assert SegmentResult.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/segmentResult/list'

        populateValidParams(params)
        def segmentResult = new SegmentResult(params)

        assert segmentResult.save() != null

        params.id = segmentResult.id

        def model = controller.show()

        assert model.segmentResultInstance == segmentResult
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/segmentResult/list'

        populateValidParams(params)
        def segmentResult = new SegmentResult(params)

        assert segmentResult.save() != null

        params.id = segmentResult.id

        def model = controller.edit()

        assert model.segmentResultInstance == segmentResult
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/segmentResult/list'

        response.reset()

        populateValidParams(params)
        def segmentResult = new SegmentResult(params)

        assert segmentResult.save() != null

        // test invalid parameters in update
        params.id = segmentResult.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/segmentResult/edit"
        assert model.segmentResultInstance != null

        segmentResult.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/segmentResult/show/$segmentResult.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        segmentResult.clearErrors()

        populateValidParams(params)
        params.id = segmentResult.id
        params.version = -1
        controller.update()

        assert view == "/segmentResult/edit"
        assert model.segmentResultInstance != null
        assert model.segmentResultInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/segmentResult/list'

        response.reset()

        populateValidParams(params)
        def segmentResult = new SegmentResult(params)

        assert segmentResult.save() != null
        assert SegmentResult.count() == 1

        params.id = segmentResult.id

        controller.delete()

        assert SegmentResult.count() == 0
        assert SegmentResult.get(segmentResult.id) == null
        assert response.redirectedUrl == '/segmentResult/list'
    }
}
