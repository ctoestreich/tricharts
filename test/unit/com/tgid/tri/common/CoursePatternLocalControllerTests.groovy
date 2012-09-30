package com.tgid.tri.common



import org.junit.*
import grails.test.mixin.*

@TestFor(CoursePatternLocalController)
@Mock(CoursePatternLocal)
class CoursePatternLocalControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/coursePatternLocal/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.coursePatternLocalInstanceList.size() == 0
        assert model.coursePatternLocalInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.coursePatternLocalInstance != null
    }

    void testSave() {
        controller.save()

        assert model.coursePatternLocalInstance != null
        assert view == '/coursePatternLocal/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/coursePatternLocal/show/1'
        assert controller.flash.message != null
        assert CoursePatternLocal.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/coursePatternLocal/list'

        populateValidParams(params)
        def coursePatternLocal = new CoursePatternLocal(params)

        assert coursePatternLocal.save() != null

        params.id = coursePatternLocal.id

        def model = controller.show()

        assert model.coursePatternLocalInstance == coursePatternLocal
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/coursePatternLocal/list'

        populateValidParams(params)
        def coursePatternLocal = new CoursePatternLocal(params)

        assert coursePatternLocal.save() != null

        params.id = coursePatternLocal.id

        def model = controller.edit()

        assert model.coursePatternLocalInstance == coursePatternLocal
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/coursePatternLocal/list'

        response.reset()

        populateValidParams(params)
        def coursePatternLocal = new CoursePatternLocal(params)

        assert coursePatternLocal.save() != null

        // test invalid parameters in update
        params.id = coursePatternLocal.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/coursePatternLocal/edit"
        assert model.coursePatternLocalInstance != null

        coursePatternLocal.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/coursePatternLocal/show/$coursePatternLocal.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        coursePatternLocal.clearErrors()

        populateValidParams(params)
        params.id = coursePatternLocal.id
        params.version = -1
        controller.update()

        assert view == "/coursePatternLocal/edit"
        assert model.coursePatternLocalInstance != null
        assert model.coursePatternLocalInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/coursePatternLocal/list'

        response.reset()

        populateValidParams(params)
        def coursePatternLocal = new CoursePatternLocal(params)

        assert coursePatternLocal.save() != null
        assert CoursePatternLocal.count() == 1

        params.id = coursePatternLocal.id

        controller.delete()

        assert CoursePatternLocal.count() == 0
        assert CoursePatternLocal.get(coursePatternLocal.id) == null
        assert response.redirectedUrl == '/coursePatternLocal/list'
    }
}
