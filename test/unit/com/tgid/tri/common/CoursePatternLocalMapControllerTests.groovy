package com.tgid.tri.common



import org.junit.*
import grails.test.mixin.*

@TestFor(CoursePatternLocalMapController)
@Mock(CoursePatternLocalMap)
class CoursePatternLocalMapControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/coursePatternLocalMap/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.coursePatternLocalMapInstanceList.size() == 0
        assert model.coursePatternLocalMapInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.coursePatternLocalMapInstance != null
    }

    void testSave() {
        controller.save()

        assert model.coursePatternLocalMapInstance != null
        assert view == '/coursePatternLocalMap/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/coursePatternLocalMap/show/1'
        assert controller.flash.message != null
        assert CoursePatternLocalMap.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/coursePatternLocalMap/list'

        populateValidParams(params)
        def coursePatternLocalMap = new CoursePatternLocalMap(params)

        assert coursePatternLocalMap.save() != null

        params.id = coursePatternLocalMap.id

        def model = controller.show()

        assert model.coursePatternLocalMapInstance == coursePatternLocalMap
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/coursePatternLocalMap/list'

        populateValidParams(params)
        def coursePatternLocalMap = new CoursePatternLocalMap(params)

        assert coursePatternLocalMap.save() != null

        params.id = coursePatternLocalMap.id

        def model = controller.edit()

        assert model.coursePatternLocalMapInstance == coursePatternLocalMap
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/coursePatternLocalMap/list'

        response.reset()

        populateValidParams(params)
        def coursePatternLocalMap = new CoursePatternLocalMap(params)

        assert coursePatternLocalMap.save() != null

        // test invalid parameters in update
        params.id = coursePatternLocalMap.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/coursePatternLocalMap/edit"
        assert model.coursePatternLocalMapInstance != null

        coursePatternLocalMap.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/coursePatternLocalMap/show/$coursePatternLocalMap.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        coursePatternLocalMap.clearErrors()

        populateValidParams(params)
        params.id = coursePatternLocalMap.id
        params.version = -1
        controller.update()

        assert view == "/coursePatternLocalMap/edit"
        assert model.coursePatternLocalMapInstance != null
        assert model.coursePatternLocalMapInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/coursePatternLocalMap/list'

        response.reset()

        populateValidParams(params)
        def coursePatternLocalMap = new CoursePatternLocalMap(params)

        assert coursePatternLocalMap.save() != null
        assert CoursePatternLocalMap.count() == 1

        params.id = coursePatternLocalMap.id

        controller.delete()

        assert CoursePatternLocalMap.count() == 0
        assert CoursePatternLocalMap.get(coursePatternLocalMap.id) == null
        assert response.redirectedUrl == '/coursePatternLocalMap/list'
    }
}
