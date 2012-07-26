package com.tgid.tri



import org.junit.*
import grails.test.mixin.*

@TestFor(SegmentController)
@Mock(Segment)
class SegmentControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/segment/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.segmentInstanceList.size() == 0
        assert model.segmentInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.segmentInstance != null
    }

    void testSave() {
        controller.save()

        assert model.segmentInstance != null
        assert view == '/segment/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/segment/show/1'
        assert controller.flash.message != null
        assert Segment.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/segment/list'

        populateValidParams(params)
        def segment = new Segment(params)

        assert segment.save() != null

        params.id = segment.id

        def model = controller.show()

        assert model.segmentInstance == segment
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/segment/list'

        populateValidParams(params)
        def segment = new Segment(params)

        assert segment.save() != null

        params.id = segment.id

        def model = controller.edit()

        assert model.segmentInstance == segment
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/segment/list'

        response.reset()

        populateValidParams(params)
        def segment = new Segment(params)

        assert segment.save() != null

        // test invalid parameters in update
        params.id = segment.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/segment/edit"
        assert model.segmentInstance != null

        segment.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/segment/show/$segment.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        segment.clearErrors()

        populateValidParams(params)
        params.id = segment.id
        params.version = -1
        controller.update()

        assert view == "/segment/edit"
        assert model.segmentInstance != null
        assert model.segmentInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/segment/list'

        response.reset()

        populateValidParams(params)
        def segment = new Segment(params)

        assert segment.save() != null
        assert Segment.count() == 1

        params.id = segment.id

        controller.delete()

        assert Segment.count() == 0
        assert Segment.get(segment.id) == null
        assert response.redirectedUrl == '/segment/list'
    }
}
