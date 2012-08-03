modules = {
    application {
        dependsOn 'backbone', 'handlebars', 'underscore'
        //resource url:'js/application.js'
        resource url:'css/application.css'
    }

    backbone {
        dependsOn 'jquery'
        resource url:'js/backbone/backbone.min.js'
    }

    handlebars {
        dependsOn 'jquery'
        resource url:'js/handlebars/handlebars.js'
    }

    underscore {
        dependsOn 'jquery'
        resource url:'js/underscore/underscore.min.js'
    }

    raceMvc {
        resource url:'js/backbone/model/race.model.js'
        resource url:'js/backbone/view/race.view.js'
    }
}