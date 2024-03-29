<%@ page import="com.tgid.tri.auth.User; com.tgid.tri.race.StatusType; com.tgid.tri.race.Race" %>
<g:set var="statusType" value="${params?.int('statusType') ?: 2}"/>
<g:set var="statusTypeText" value="${StatusType.getStatusType(params?.statusType ?: StatusType.Pending.value())}"/>
<!doctype html>
<html>
<head>
  <meta name="layout" content="admin">
  <title>User Data Import</title>
</head>

<body>

    <div class="page-header">
      <h1>Manually Import Race</h1>
    </div>

    <g:if test="${flash.message}">
      <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
    </g:if>

    <g:form controller="admin" action="dataImportRaceProcess" class="form-horizontal">

      <fieldset>
        <div class="control-group">
          <label for='enabled' class="control-label">User:</label>
          <div class="controls"> <g:select name="id"  optionKey="id" from="${Race.listOrderByName()}" /></div>
        </div>

        <div class="form-actions">
          <button type="submit" class="btn btn-primary">
            <i class="icon-ok icon-white"></i>
            <g:message code="default.button.import.label" default="Import" />
          </button>
        </div>
      </fieldset>

    </g:form>

</body>
</html>
