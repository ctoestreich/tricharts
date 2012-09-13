<%@ page import="com.tgid.tri.auth.User; com.tgid.tri.race.StatusType; com.tgid.tri.race.Race" %>
<g:set var="statusType" value="${params?.int('statusType') ?: 2}"/>
<g:set var="statusTypeText" value="${StatusType.getStatusType(params?.statusType ?: StatusType.Pending.value())}"/>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap">
  <title>Site Admin</title>
</head>

<body>
<div class="page-header">
  <h1>Admin <small>bash$ su root</small></h1>
</div>

<div class="row-fluid">
  <div class="span4 well">
    <cache:render template="/templates/admin/adminNav" key="${request.forwardURI}"/>
  </div>

  <div class="span8">
    <g:if test="${flash.message}">
      <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
      <br/>
    </g:if>

    Admin Dashboard

  </div>

</div>
</body>
</html>
