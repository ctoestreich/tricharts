<%@ page import="com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>User Dashboard</title>
  <r:require modules="dashboard,results"/>
  <gvisualization:apiImport/>
</head>

<body>

<div class="page-header">
  <h1>Results For ${user.firstName} <small>results at a glance</small></h1>
</div>

<g:if test="${flash.message}">
  <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
</g:if>

<g:render template="/templates/admin/userSelect"/>

<script type="text/javascript">
  app.loadRunRecords = function () {
    ${remoteFunction(controller: 'visualization', action: 'runningRecords', update: 'runDashboardRecords', params: ['user.id': params?.user?.id])}
    ${remoteFunction(controller: 'visualization', action: 'triathlonRecords', update: 'triathlonDashboardRecords', params: ['user.id': params?.user?.id])}
  };

  $(function () {
    app.loadRunRecords();
  });

</script>

<div class="row well_clear">
  %{--<div class="span12">--}%
  <g:render template="/templates/dashboardHeader" model="[sport: 'Triathlon']"/>

  <div class="row-fluid" id="triathlonDashboardRecords"><g:img dir="/images" file="spinner.gif"/> loading triathlon records...</div>

  <BR>

  <div id="results-triathlon" class="accordion">
    <g:if test="${params?.srt == "type"}">
      <g:render template="/templates/triathlonResults" collection="${triathlons.list().sort {a, b -> b?.race?.raceCategoryType <=> a?.race?.raceCategoryType}}" var="result"/>
    </g:if>
    <g:else>
      <g:render template="/templates/triathlonResults" collection="${triathlons.list().sort {a, b -> b.date <=> a.date}}" var="result"/>
    </g:else>
  </div>
</div>

<br />

<div class="row well_clear">
  %{--<div class="span12">--}%
  <g:render template="/templates/dashboardHeader" model="[sport: 'Running', user: params?.user]"/>

  <div class="row-fluid" id="runDashboardRecords"><g:img dir="/images" file="spinner.gif"/> loading run records...</div>

  <BR>

  <div id="results-run" class="accordion">
    <g:if test="${params?.srt == "type"}">
      <g:render template="/templates/runResults" collection="${runs.list().sort {a, b -> b?.race?.raceCategoryType <=> a?.race?.raceCategoryType}}" var="result"/>
    </g:if>
    <g:else>
      <g:render template="/templates/runResults" collection="${runs.list().sort {a, b -> b.date <=> a.date}}" var="result"/>
    </g:else>
  </div>
  %{--</div>--}%
</div>

%{--<BR/>--}%

%{--<div class="row well_clear">--}%
  %{--<div class="span12">--}%
  %{--<g:render template="/templates/dashboardHeader" model="[sport: 'Biking', user: params?.user]"/>--}%

  %{--<div class="row-fluid" id="bikeDashboardRecords"><g:img dir="/images" file="spinner.gif"/> loading bike records...</div>--}%

  %{--<BR>--}%

  %{--<div id="results-run" class="accordion">--}%
    %{--<g:if test="${params?.srt == "type"}">--}%
      %{--<g:render template="/templates/bikeResults" collection="${bikes.list().sort {a, b -> b?.race?.raceCategoryType <=> a?.race?.raceCategoryType}}" var="result"/>--}%
    %{--</g:if>--}%
    %{--<g:else>--}%
      %{--<g:render template="/templates/bikeResults" collection="${bikes.list().sort {a, b -> b.date <=> a.date}}" var="result"/>--}%
    %{--</g:else>--}%
  %{--</div>--}%
  %{--</div>--}%
%{--</div>--}%


<div class="modal hide" id="deleteConfirmation">
  <div class="modal-header"><button type="button" class="close" data-dismiss="modal">×</button>

    <h3>Delete Results</h3></div>

  <div class="modal-body"><p>Are you really sure you want to delete your results for this race?</p></div>

  <div class="modal-footer"><a href="javascript:void(0);" class="btn" data-dismiss="modal">Cancel</a><a href="javascript:tri.results.deleteRaceResultConfirmation()" class="btn btn-danger">DELETE</a></div>
</div>

<g:form name="modifyRaceResultsForm" id="modifyRaceResultsForm" controller="dashboard" action="modifyRaceResults">
  <g:hiddenField name="raceResultId" value=""/>
  <g:hiddenField name="user.id" value="${user?.id}"/>
  <g:hiddenField name="raceResultEdit" value="false"/>
</g:form>

</body>
</html>