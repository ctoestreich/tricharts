import com.tgid.tri.queue.AthlinksCoursePatternsImportJesqueJob
import com.tgid.tri.queue.AthlinksResultsImportJesqueJob
import com.tgid.tri.queue.AthlinksUserResultsImportJesqueJob
import com.tgid.tri.queue.AthlinksRaceCategoryImportJesqueJob
import com.tgid.tri.queue.AthlinksRaceImportJesqueJob
import org.apache.log4j.DailyRollingFileAppender

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.config.locations = ["file:${userHome}/.grails/s2fb-config.properties"]

grails.app.context = "/"

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
        all: '*/*',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        form: 'application/x-www-form-urlencoded',
        html: ['text/html', 'application/xhtml+xml'],
        js: 'text/javascript',
        json: ['application/json', 'text/json'],
        multipartForm: 'multipart/form-data',
        rss: 'application/rss+xml',
        text: 'text/plain',
        xml: ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

//grails.resources.processing.startup = "delayed"

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = true

grails.gorm.failOnError = true

def logDirectory = '/var/log/tomcat6'

environments {
    development {
        logDirectory = "c:/logs"
        grails.logging.jul.usebridge = true
        grails.naming.entries = ['jdbc/trichartsDB': [
                type: "javax.sql.DataSource",
                auth: "Container",
                description: "tricharts",
                url: "jdbc:mysql://127.0.0.1/tricharts",
                username: "tricharts",
                password: "Tr1Ch4rt5",
                driverClassName: "com.mysql.jdbc.Driver",
//                dialect: "org.hibernate.dialect.MySQLInnoDBDialect",
                maxActive: "8",
                maxIdle: "4"]]
    }
    production {
        jquery.minified = true
        jqueryUi.minified = true
        grails.dbconsole.enabled = true
        grails.dbconsole.urlRoot = '/dbadmin/dbconsole'
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://www.tricharts.com"
    }
}

logg {
    dir = logDirectory
}

// log4j configuration
log4j = {
    appenders {
        console name: 'stdout', threshold: org.apache.log4j.Level.INFO
        //rollingFile name: 'fdbErrorLog', file: logDirectory + '/fdbError.log', threshold: org.apache.log4j.Level.ERROR, maxFileSize: "32MB", maxBackupIndex: 10, 'append': true
        appender new DailyRollingFileAppender(
                name: 'dailyAppender',
                datePattern: "'.'yyyy-MM-dd",  // See the API for all patterns.
                fileName: "${logDirectory}/trichartsError.log",
                threshold: org.apache.log4j.Level.ERROR,
                append: true,
                layout: pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
        )

        appender new DailyRollingFileAppender(
                name: 'dailyAppender',
                datePattern: "'.'yyyy-MM-dd",  // See the API for all patterns.
                fileName: "${logDirectory}/trichartsInfo.log",
                threshold: org.apache.log4j.Level.INFO,
                append: true,
                layout: pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
        )
    }

    error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
          'org.codehaus.groovy.grails.web.pages', //  GSP
          'org.codehaus.groovy.grails.web.sitemesh', //  layouts
          'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
          'org.codehaus.groovy.grails.web.mapping', // URL mapping
          'org.codehaus.groovy.grails.commons', // core / classloading
          'org.codehaus.groovy.grails.plugins', // plugins
          'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
          'org.springframework',
          'org.hibernate',
          'net.sf.ehcache.hibernate',
          'com.uhg.fdb'

    info 'com.tgid.tri'

    warn 'org.mortbay.log'

    root {
        error 'dailyAppender'
        additivity = true
    }

    environments {
        development {
            root {
                error 'stdout', 'dailyAppender'
                additivity = true
            }
        }
    }
}

grails.plugin.cloudfoundry.appname = 'tricharts'

grails.plugins.twitterbootstrap.fixtaglib = true
// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.tgid.tri.auth.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.tgid.tri.auth.UserRole'
grails.plugins.springsecurity.authority.className = 'com.tgid.tri.auth.Role'

grails.plugins.springsecurity.facebook.domain.classname = 'com.tgid.tri.auth.FacebookUser'
grails.plugins.springsecurity.successHandler.defaultTargetUrl = '/dashboard/index'

grails.plugins.springsecurity.controllerAnnotations.staticRules = [
        '/monitoring': ['ROLE_ADMIN'],
        '/monitoring/**': ['ROLE_ADMIN']
]

jodatime.format.html5 = true

// Added by the Joda-Time plugin:
grails.gorm.default.mapping = {
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDateMidnight, class: org.joda.time.DateMidnight
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDateTime, class: org.joda.time.DateTime
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDateTimeZoneAsString, class: org.joda.time.DateTimeZone
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDurationAsString, class: org.joda.time.Duration
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentInstantAsMillisLong, class: org.joda.time.Instant
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentInterval, class: org.joda.time.Interval
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentLocalDate, class: org.joda.time.LocalDate
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime, class: org.joda.time.LocalDateTime
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentLocalTime, class: org.joda.time.LocalTime
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentPeriodAsString, class: org.joda.time.Period
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentTimeOfDay, class: org.joda.time.TimeOfDay
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentYearMonthDay, class: org.joda.time.YearMonthDay
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentYears, class: org.joda.time.Years
}

athlinks {
    key = "8253934e71896ea92a53f00e3577efa0"
}

cache.headers.presets = [
//        authed_page: false, // No caching for logged in user
        content: [shared: true, validFor: 3600], // 1 hr on content
        news: [shared: true, validUntil: new Date() + 1],
        search_results: [validFor: 60, shared: true],
        records: [validFor: 3600, shared: false]
]

jobs {
    enabled = true
    athlinksUberImportJesqueJob {
        enabled = false
    }
    athlinksUserResultsImportJob {
        enabled = true
    }
    athlinksResultsImportJob {
        enabled = true
    }
    athlinksRaceCategoryImportJob {
        enabled = true
    }
    athlinksCoursePatternsImportJob {
        enabled = true
    }
    athlinksRaceImportJob {
        enabled = true
    }
}

sendgrid {
    username = 'ctoestreich'
    password = 'Acetrike1'
}

grails {
    mail {
        host = "smtp.gmail.com"
        port = 465
        username = "acetrike@gmail.com"
        password = '!Chris$4'
        props = ["mail.debug": "true",
                "mail.transport.protocol": "smtps",
                "mail.smtps.host": "smtp.gmail.com",
                "mail.smtps.port": "465",
                "mail.smtps.auth": "true",
                "mail.smtps.user": "acetrike@gmail.com",
                "mail.smtps.password": '!Chris$4']
    }
}

security {
    ui {
        register {
            emailBody = '''\
Hi $user.username,<br/>
<br/>
An account was created at <a href="$url">TriCharts.com</a> with this email address.<br/>
<br/>
If you made the request, please click&nbsp;<a href="$url">here</a> to finish the registration.
'''
            emailFrom = 'noreply@tricharts.com'
            emailSubject = 'New Account'
            defaultRoleNames = ['ROLE_USER']
            postRegisterUrl = '/dashboard/index'
        }

        forgotPassword {
            emailBody = '''\
Hi $user.username,<br/>
<br/>
You requested that your password be reset for <a href="$url">TriCharts.com</a>.<br/>
<br/>
If you didn't make this request then ignore the email; no changes have been made.<br/>
<br/>
If you did make the request, then click <a href="$url">here</a> to reset your password.
'''
            emailFrom = 'noreply@tricharts.com'
            emailSubject = 'Password Reset'
            postResetUrl = null // use defaultTargetUrl if not set
        }
    }
}

tomcat.deploy.username = "ctoestreich"
tomcat.deploy.password = "Acetrike1"
tomcat.deploy.url = "http://www.tricharts.com/manager"

grails {
    redis {
        poolConfig {
            // jedis pool specific tweaks here, see jedis docs & src
            // ex: testWhileIdle = true
        }
        port = 6379
        host = "localhost"
        timeout = 2000 //default in milliseconds
        password = 'a!s@d#f4qwert!' //defaults to no password
    }
}

grails {
    jesque {
        workers {
            athlinksGenericImportWorkerPool {
                workers = 3 //defaults to 1
                queueNames = 'athlinksGenericImport'
                jobTypes = [
                        (AthlinksUserResultsImportJesqueJob.simpleName): AthlinksUserResultsImportJesqueJob,
                        (AthlinksRaceImportJesqueJob.simpleName): AthlinksRaceImportJesqueJob]
            }
            athlinksReferenceImportWorkerPool {
                workers = 2 //defaults to 1
                queueNames = 'importAthlinksReferenceData' //or a list
                jobTypes = [(AthlinksCoursePatternsImportJesqueJob.simpleName): AthlinksCoursePatternsImportJesqueJob,
                        (AthlinksRaceCategoryImportJesqueJob.simpleName): AthlinksRaceCategoryImportJesqueJob]
            }
            athlinksResultsImportWorkerPool {
                workers = 3 //defaults to 1
                queueNames = 'importAthlinksResults' //or a list
                jobTypes = [(AthlinksResultsImportJesqueJob.simpleName): AthlinksResultsImportJesqueJob]
            }
        }
    }
}
