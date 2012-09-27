package com.tgid.tri.auth



import org.junit.*
import grails.test.mixin.*

@TestFor(RegistrationCodeController)
@Mock(RegistrationCode)
class RegistrationCodeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/registrationCode/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.registrationCodeInstanceList.size() == 0
        assert model.registrationCodeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.registrationCodeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.registrationCodeInstance != null
        assert view == '/registrationCode/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/registrationCode/show/1'
        assert controller.flash.message != null
        assert RegistrationCode.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/registrationCode/list'

        populateValidParams(params)
        def registrationCode = new RegistrationCode(params)

        assert registrationCode.save() != null

        params.id = registrationCode.id

        def model = controller.show()

        assert model.registrationCodeInstance == registrationCode
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/registrationCode/list'

        populateValidParams(params)
        def registrationCode = new RegistrationCode(params)

        assert registrationCode.save() != null

        params.id = registrationCode.id

        def model = controller.edit()

        assert model.registrationCodeInstance == registrationCode
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/registrationCode/list'

        response.reset()

        populateValidParams(params)
        def registrationCode = new RegistrationCode(params)

        assert registrationCode.save() != null

        // test invalid parameters in update
        params.id = registrationCode.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/registrationCode/edit"
        assert model.registrationCodeInstance != null

        registrationCode.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/registrationCode/show/$registrationCode.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        registrationCode.clearErrors()

        populateValidParams(params)
        params.id = registrationCode.id
        params.version = -1
        controller.update()

        assert view == "/registrationCode/edit"
        assert model.registrationCodeInstance != null
        assert model.registrationCodeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/registrationCode/list'

        response.reset()

        populateValidParams(params)
        def registrationCode = new RegistrationCode(params)

        assert registrationCode.save() != null
        assert RegistrationCode.count() == 1

        params.id = registrationCode.id

        controller.delete()

        assert RegistrationCode.count() == 0
        assert RegistrationCode.get(registrationCode.id) == null
        assert response.redirectedUrl == '/registrationCode/list'
    }
}
