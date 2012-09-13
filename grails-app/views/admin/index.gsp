<%@ page import="com.tgid.tri.auth.User; com.tgid.tri.race.StatusType; com.tgid.tri.race.Race" %>
<g:set var="statusType" value="${params?.int('statusType') ?: 2}"/>
<g:set var="statusTypeText" value="${StatusType.getStatusType(params?.statusType ?: StatusType.Pending.value())}"/>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap">
  <title>Site Admin</title>
  <style>
    .stats h5{ margin-bottom: 5px;}
    .stats div {
      text-align:center; /* center horizontally */
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

    <div class="row-fluid stats">
      <div class="span3 well"><h5>Users</h5><redis:memoize key="admin-Users" expire="3600"><span class="stat">${com.tgid.tri.auth.User.count()}</span></redis:memoize> </div>
      <div class="span3 well"><h5>Races</h5><redis:memoize key="admin-Races" expire="3600"><span class="stat">${com.tgid.tri.race.Race.count()}</span></redis:memoize> </div>
      <div class="span3 well"><h5>Patterns</h5><redis:memoize key="admin-Patterns" expire="3600"><span class="stat">${com.tgid.tri.race.CoursePattern.count()}</span></redis:memoize> </div>
      <div class="span3 well"><h5>Results</h5><redis:memoize key="admin-Results" expire="60"><span class="stat">${com.tgid.tri.results.RaceResult.count()}</span></redis:memoize> </div>
    </div>

  </div>

</div>
</body>
</html>
