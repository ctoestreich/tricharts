package com.tgid.tri.api

import com.tgid.tri.BaseController
import com.tgid.tri.auth.User
import grails.converters.JSON

class DataController extends BaseController {

    def visualizationService

    def user(){
        println params?.id
        def theUser = User.get(params?.id)
        formatResponse([name: theUser?.toString(), states: theUser?.states, genderType: theUser?.genderType?.toString()])
    }

    def searchUser() {
        def query = {
            if(params.firstName) {
                and {
                    ilike('firstName', params.firstName + '%')
                }
            }
            if(params.lastName){
                and {
                    ilike('lastName', params.lastName + '%')
                }
            }
            if(params.state) {
                and {
                    states {
                        eq('abbrev', params.state)
                    }
                }
            }
        }
        def response = []
        def criteria = User.createCriteria()
        def users = criteria.list(query)
        users.each { User user ->
            response << [name: user.toString(), id: user.id, states: user.states]
        }
        formatResponse(response)
    }

    def triathlonRecords() {
        User user = anyUser
        def userId = user?.id
        def data = visualizationService.mapTriathlonRecords(userId)

        formatResponse(data)
    }

    def runningRecords() {
        User user = anyUser
        def userId = user.id
        def data = visualizationService.mapRunningRecords(userId)
        formatResponse(transformRaceRecords(data))
    }

    private List transformRaceRecords(Map data) {
        def transformed = []

        data.each {
            transformed << [distance: it.key, result: it.value]
        }

        transformed
    }

    def bikingRecords() {
        User user = anyUser
        def userId = user.id
        def data = []//visualizationService.mapRunningRecords(userId)
        formatResponse(data)
    }
}
