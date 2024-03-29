<%@ page import="com.tgid.tri.auth.User; com.tgid.tri.race.StatusType; com.tgid.tri.race.Race" %>
<g:set var="statusType" value="${params?.int('statusType') ?: 2}"/>
<g:set var="statusTypeText" value="${StatusType.getStatusType(params?.statusType ?: StatusType.Pending.value())}"/>
<!doctype html>
<html>
<head>
  <meta name="layout" content="admin">
  <title>Site Admin</title>
  <style>
  .stats h5 {
    margin-bottom: 5px;
  }

  .stats div {
    text-align: center; /* center horizontally */
    /*vertical-align:middle; *//* center vertically */
  }

  .stat {
    color: #2e90ff;
    font-weight: bolder;
    font-family: "Verdana, Geneva, sans-serif";
    font-size: 1.5em;
  }
  </style>
</head>

<body>
<g:if test="${flash.message}">
  <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
  <br/>
</g:if>

<div class="row-fluid">
  <div class="overview">
    <ul>
      <li>
        <h5>Users</h5>
        <h3><redis:memoize key="admin-Users" expire="3600">${com.tgid.tri.auth.User.count()}</redis:memoize></h3>
      </li>
      <li>
        <h5>Races</h5>
        <h3><redis:memoize key="admin-Races" expire="3600">${com.tgid.tri.race.Race.count()}</redis:memoize></h3>
      </li>
      <li>
        <h5>Patterns</h5>
        <h3><redis:memoize key="admin-Patterns" expire="3600">${com.tgid.tri.race.CoursePattern.count()}</redis:memoize></h3>
      </li>
      <li>
        <h5>Results</h5>
        <h3><redis:memoize key="admin-Results" expire="60">${com.tgid.tri.results.RaceResult.count()}</redis:memoize></h3>
      </li>
    </ul>
  </div>
</div>

<div class="row-fluid">
  <g:link class="btn btn-info" controller="admin" action="index" params="[clearCache: true]">Clear Stats Cache</g:link>
</div>

</body>
</html>
