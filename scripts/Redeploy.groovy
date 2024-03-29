scriptScope = grails.util.BuildScope.WAR

includeTargets << grailsScript("_GrailsWar")

ant.taskdef(name: "deploy", classname: "org.apache.catalina.ant.DeployTask")
ant.taskdef(name: "list", classname: "org.apache.catalina.ant.ListTask")
ant.taskdef(name: "undeploy", classname: "org.apache.catalina.ant.UndeployTask")

target(main: "Undeploy and redeploy") {
    depends(parseArguments, compile, createConfig)

    def cmd = argsMap.params ? argsMap.params[0] : 'deploy'
    argsMap.params.clear()
    def user = config.tomcat.deploy.username ?: 'manager'
    def pass = config.tomcat.deploy.password ?: 'secret'
    def url = config.tomcat.deploy.url ?: 'http://localhost:8080/manager'

    configureServerContextPath()
    println "Undeploying application $serverContextPath from Tomcat"
    println '''\
NOTE: If you experience a classloading error during undeployment you need to take the following steps:

* Upgrade to Tomcat 6.0.20 or above
* Pass this system argument to Tomcat: -Dorg.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false

See http://tomcat.apache.org/tomcat-6.0-doc/config/systemprops.html for more information
'''
    undeploy(
            url: url,
            path: serverContextPath,
            username: user,
            password: pass)

    war()
    println "Deploying application $serverContextPath to Tomcat"
    deploy(war: warName,
           url: url,
           path: serverContextPath,
           username: user,
           password: pass)
}

setDefaultTarget(main)
