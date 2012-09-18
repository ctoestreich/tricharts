<%@ page import="com.tgid.tri.data.AthlinksResultsImportJob; com.tgid.tri.race.StatusType; com.tgid.tri.race.Race" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap">
  <g:set var="entityName" value="${message(code: 'race.label', default: 'Race')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<div class="row-fluid">
  <div class="span4 well">
    <cache:render template="/templates/admin/adminNav" key="${request.forwardURI}"/>
  </div>

  <div class="span8">

    <div class="page-header">
      <h1>Manually Run Job <small></small></h1>
    </div>

    <g:if test="${flash.message}">
      <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
    </g:if>


    <div class="row-fluid">
      <ul class="nav nav-stacked nav-pills">
        <li class="nav-header">Trigger Scheduled Jobs</li>
        <li><a href="${createLink(controller: 'admin', action: 'importAthlinksResults')}">Import Racer Results (null or older than 1 day)</a></li>
        <li class="nav-header">Manual Data Import</li>
        <li><a href="${createLink(controller: 'admin', action: 'dataImport')}">Import Single User Results</a></li>
        <li><a href="${createLink(controller: 'admin', action: 'dataImportRace')}">Import Single Race</a></li>
        <li class="nav-header">Reference Import</li>
        <li><a href="${createLink(controller: 'admin', action: 'importAthlinksCoursePatterns')}">Import Athlinks Courses</a></li>
        <li><a href="${createLink(controller: 'admin', action: 'importAthlinksRaceCategories')}">Import Athlinks Categories</a></li>
        <li class="nav-header">Run These With Caution</li>
        <li><a class="btn-danger" style="color: #fff;" href="${createLink(controller: 'admin', action: 'importAllAthlinksRaces')}">Re-Import All Athlinks Races</a></li>
      </ul>
    </div>

  </div>

</div>
</body>
</html>
