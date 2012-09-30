grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.war.file = "target/ROOT.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "https://repo.springsource.org/repo"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        runtime 'mysql:mysql-connector-java:5.1.21'
//        compile 'joda-time:joda-time-hibernate:1.3'
//        compile 'org.jadira.usertype:usertype.jodatime:1.9'
        compile "org.jadira.usertype:usertype.jodatime:1.9"
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.7.2"
        runtime ":jquery-ui:1.8.15"
        runtime ":resources:1.2-RC1"

        runtime ':twitter-bootstrap:2.1.0.1'
        runtime ':fields:1.3'
        runtime ":cache-headers:1.1.5"
        runtime ":cached-resources:1.0"
        runtime ":zipped-resources:1.0"
        runtime ":gsp-resources:0.4.1"
        runtime ":spring-security-core:1.2.7.3"
        runtime ":database-migration:1.1"

        compile ":grails-melody:1.14"
        compile ":svn:1.0.2"
        compile ":joda-time:1.4"
        compile ":google-visualization:0.5.3"
//        compile ":cloud-foundry:1.2.3"
        compile ':webxml:1.4.1'
        compile ":mail:1.0"
//        compile ':sendgrid:0.4'
        //runtime ":spring-security-facebook:0.9"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"
        compile ":springcache:1.3.1"
        compile ":quartz:1.0-RC2"
        compile ":executor:0.3"
        compile ":jesque:0.4.0"
        compile ":redis:1.3.2"

        test ":spock:0.6"
        build ":tomcat:$grailsVersion"
        compile ':cache:1.0.0'
    }
}
