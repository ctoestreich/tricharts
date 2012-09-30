package com.tgid.tri.data



import org.junit.*
import grails.test.mixin.*

@TestFor(ImportLogController)
@Mock(ImportLog)
class ImportLogControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/importLog/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.importLogInstanceList.size() == 0
        assert model.importLogInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.importLogInstance != null
    }

    void testSave() {
        controller.save()

        assert model.importLogInstance != null
        assert view == '/importLog/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/importLog/show/1'
        assert controller.flash.message != null
        assert ImportLog.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/importLog/list'

        populateValidParams(params)
        def importLog = new ImportLog(params)

        assert importLog.save() != null

        params.id = importLog.id

        def model = controller.show()

        assert model.importLogInstance == importLog
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/importLog/list'

        populateValidParams(params)
        def importLog = new ImportLog(params)

        assert importLog.save() != null

        params.id = importLog.id

        def model = controller.edit()

        assert model.importLogInstance == importLog
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/importLog/list'

        response.reset()

        populateValidParams(params)
        def importLog = new ImportLog(params)

        assert importLog.save() != null

        // test invalid parameters in update
        params.id = importLog.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/importLog/edit"
        assert model.importLogInstance != null

        importLog.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/importLog/show/$importLog.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        importLog.clearErrors()

        populateValidParams(params)
        params.id = importLog.id
        params.version = -1
        controller.update()

        assert view == "/importLog/edit"
        assert model.importLogInstance != null
        assert model.importLogInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/importLog/list'

        response.reset()

        populateValidParams(params)
        def importLog = new ImportLog(params)

        assert importLog.save() != null
        assert ImportLog.count() == 1

        params.id = importLog.id

        controller.delete()

        assert ImportLog.count() == 0
        assert ImportLog.get(importLog.id) == null
        assert response.redirectedUrl == '/importLog/list'
    }
}
