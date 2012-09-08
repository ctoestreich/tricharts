package com.tgid.tri.job



import org.junit.*
import grails.test.mixin.*

@TestFor(JobLogController)
@Mock(JobLog)
class JobLogControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/jobLog/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.jobLogInstanceList.size() == 0
        assert model.jobLogInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.jobLogInstance != null
    }

    void testSave() {
        controller.save()

        assert model.jobLogInstance != null
        assert view == '/jobLog/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/jobLog/show/1'
        assert controller.flash.message != null
        assert JobLog.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/jobLog/list'

        populateValidParams(params)
        def jobLog = new JobLog(params)

        assert jobLog.save() != null

        params.id = jobLog.id

        def model = controller.show()

        assert model.jobLogInstance == jobLog
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/jobLog/list'

        populateValidParams(params)
        def jobLog = new JobLog(params)

        assert jobLog.save() != null

        params.id = jobLog.id

        def model = controller.edit()

        assert model.jobLogInstance == jobLog
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/jobLog/list'

        response.reset()

        populateValidParams(params)
        def jobLog = new JobLog(params)

        assert jobLog.save() != null

        // test invalid parameters in update
        params.id = jobLog.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/jobLog/edit"
        assert model.jobLogInstance != null

        jobLog.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/jobLog/show/$jobLog.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        jobLog.clearErrors()

        populateValidParams(params)
        params.id = jobLog.id
        params.version = -1
        controller.update()

        assert view == "/jobLog/edit"
        assert model.jobLogInstance != null
        assert model.jobLogInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/jobLog/list'

        response.reset()

        populateValidParams(params)
        def jobLog = new JobLog(params)

        assert jobLog.save() != null
        assert JobLog.count() == 1

        params.id = jobLog.id

        controller.delete()

        assert JobLog.count() == 0
        assert JobLog.get(jobLog.id) == null
        assert response.redirectedUrl == '/jobLog/list'
    }
}
