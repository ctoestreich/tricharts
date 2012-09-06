modules = {
    application {
        dependsOn 'backbone', 'handlebars', 'underscore'
        resource url: 'js/application.js', disposition: 'head'
        resource url: 'css/application.css'
    }

    results {
        resource url: 'js/results.js'
    }

    backbone {
        dependsOn 'jquery'
        resource url: 'js/backbone/backbone.min.js'
    }

    handlebars {
        dependsOn 'jquery'
        resource url: 'js/handlebars/handlebars.js'
    }

    underscore {
        dependsOn 'jquery'
        resource url: 'js/underscore/underscore.min.js'
    }

    raceMvc {
        resource url: 'js/backbone/model/race.model.js'
        resource url: 'js/backbone/view/race.view.js'
    }

    highcharts {
        dependsOn 'jquery'
        resource url: 'js/highcharts/highcharts.js'
        resource url: 'js/highcharts/modules/exporting.js'
    }

    progression {
        dependsOn 'highcharts'
        resource url: 'js/highcharts/highchart.theme.js'
    }

    dashboard {
        dependsOn 'jquery'
        resource url: 'js/dashboard/dashboard.js', disposition: 'head'
    }

    widgets {
        dependsOn 'jquery, jquery-ui'
        resource url: 'js/jquery/widgets.js'
    }

    analytics {
        resource url: 'js/google.js', disposition: 'head'
    }
}