<%@ page import="com.tgid.tri.race.StatusType; com.tgid.tri.race.Race" %>
<g:set var="statusType" value="${params?.int('statusType') ?: 2}"/>
<g:set var="statusTypeText" value="${StatusType.getStatusType(params?.statusType ?: StatusType.Pending.value())}"/>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap">
  <g:set var="entityName" value="${message(code: 'race.label', default: 'Race')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<div class="row-fluid">

  <div class="span12">

    <div class="page-header">
      <h1>${statusTypeText} <g:message code="default.list.label" args="[entityName]"/></h1>
    </div>

    <g:if test="${flash.message}">
      <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
    </g:if>

    <g:form controller="admin" action="updateSettings" class="form-horizontal">

      <fieldset>
        <div class="control-group">
          <label for='enabled' class="control-label">Enable All Jobs:</label>
          <div class="controls"> <g:select name="enabled" value="${grailsApplication.config.jobs.enabled}" from="${[true, false]}" /></div>
        </div>
        <div class="control-group">
          <label for='enabled' class="control-label">Enable Athlinks Results Import Job:</label>
          <div class="controls"> <g:select name="AthlinksResultsImportJob" value="${grailsApplication.config.jobs.AthlinksResultsImportJob.enabled}" from="${[true, false]}" /></div>
        </div>
        <div class="control-group">
          <label for='enabled' class="control-label">Enable User Results Import Job:</label>
          <div class="controls"> <g:select name="AthlinksUserResultsImportJob" value="${grailsApplication.config.jobs.AthlinksUserResultsImportJob.enabled}" from="${[true, false]}" /></div>
        </div>


        <div class="form-actions">
          <button type="submit" class="btn btn-primary">
            <i class="icon-ok icon-white"></i>
            <g:message code="default.button.update.label" default="Update" />
          </button>
        </div>
      </fieldset>

    </g:form>

  </div>

</div>
</body>
</html>
