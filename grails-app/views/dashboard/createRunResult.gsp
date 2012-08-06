<%@ page import="com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>User Dashboard</title>
</head>

<body>

<div class="page-header">
  <h1>Results For ${user.firstName} <small> results at a glance</small></h1>
</div>

<g:if test="${!race}">
<g:form id="createRunResult" class="form-inline well" controller="dashboard" action="createRunResult">
  <g:select optionKey="id" id="raceResult.race" from="${runs}" name="raceResults.race" />
  <button type="submit" class="btn">Next -></button>
</g:form>
</g:if>
<g:else>
  <g:form id="saveRunResult" class="form-horizontal well" controller="dashboard" action="saveRunResult">
    <input type="hidden" name="raceResults.race" value="${race.id}" id="raceResults.race">
    <input type="hidden" name="segmentCount" value="${raceResults?.segmentResults?.size()}" id="segmentCount">


    <fieldset>
      <legend>${race}</legend>

      <g:render template="/templates/resultsInput" model="[name:'Race']" />

      <g:each in="${raceResults.segmentResults?.sort{a,b -> a.segmentOrder <=> b.segmentOrder}}" var="segmentResults" status="i">
        <div class="well">
          <g:render template="/templates/resultsInput" model='[name: "Segment",prefix:"segmentResults[${i}].",segmentResults:segmentResults]' />
        </div>
      </g:each>

      <div class="control-group">
        <button type="submit" class="btn">Save</button>
      </div>
    </fieldset>
  </g:form>
</g:else>

</body>
</html>