<%@ page import="com.tgid.tri.race.RaceType; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <r:require modules="dashboard, results, widgets"/>
  <title>User Dashboard</title>
</head>

<body>

<div class="page-header">
  <h1>Results For ${user.firstName} <small>results at a glance</small></h1>
</div>

<g:if test="${flash.message}">
  <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
</g:if>

<g:hasErrors bean="${raceResult}">
  <bootstrap:alert class="alert-error">
    <ul>
      <g:eachError bean="${raceResult}" var="error">
        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                error="${error}"/></li>
      </g:eachError>
    </ul>
  </bootstrap:alert>
</g:hasErrors>

<g:if test="${!race}">
  <h3>Add ${params?.raceType} Result</h3>
  <g:form id="createResult" class="form-inline well" controller="results" action="selectRace">
    <g:hiddenField name="user.id" id="user.id" value="${params.get('user.id')}"/>
    <g:hiddenField name="raceType" id="raceType" value="${params?.raceType}"/>
    <g:if test="${races}">
      <p>Search For Race By Name</p>
      <g:select noSelection="${['': '']}" optionKey="id" id="race" from="${races.sort {a, b -> b.date <=> a.date}}" name="race.id"/>
      <button type="submit" class="btn">Next -></button>
    </g:if>
    <g:else>
      <bootstrap:alert class="alert-info"><g:message code="raceResult.races.none.approved"/></bootstrap:alert>
      <a href="<g:createLink controller="results" action="addRace" params='[raceType: "${params?.raceType}"]'/>" class="btn">Add Race</a>
    </g:else>
  </g:form>
  <script>
    $(function () {
      $("#race").combobox();
    });
  </script>
</g:if>
<g:else>
  <g:form id="saveResult" class="form-horizontal well" controller="results" action="saveResult">
    <g:hiddenField name="user.id" if="user.id" value="${user?.id}"/>
    <input type="hidden" name="race.id" value="${race.id}" id="race">
    <input type="hidden" name="raceResultId" value="${raceResult?.id}" id="raceResultId">
    <input type="hidden" name="segmentCount" value="${raceResult?.segmentResults?.size()}" id="segmentCount">

    <div class="row-fluid">
      <div class="span8"><h2>${race}</h2></div>

      <div class="span4">
        <div class="btn-group-wrap-right">
          <div class="btn-group">
          %{--<a class="btn"--}%
          %{--href="javascript:app.${sport.toLowerCase()}Charts()"--}%
          %{--id="dashboard-charts"><i class="icon-camera"></i>Common Charts</a>--}%
            <g:if test="${race.raceType == RaceType.Triathlon}">
              <a class="btn"
                 href="javascript:tri.results.toggleTransitions();"
                 id="hide-transitions"><i class="icon-tag"></i>Toggle Transitions</a>
            </g:if>
          </div>
        </div>
      </div>
    </div>
    <hr>
    <fieldset>
    %{--<legend></legend>--}%

      <g:render template="/templates/resultsInput" model="[name: 'Race', isRaceResults: true, raceResult: raceResult]"/>

      <g:set var="hidden" value="${raceResult.segmentResults?.size() == 1}"/>
      <g:each in="${raceResult.segmentResults?.sort {a, b -> a.segmentOrder <=> b.segmentOrder}}"
              var="segmentResult" status="i">
        <div class="well ${segmentResult?.raceSegment?.segment?.segmentType}" style="${hidden ? 'display:none' : ''}">
          <g:render template="/templates/resultsInput"
                    model='[name: "Segment", prefix: "segmentResult[${i}].", segmentResult: segmentResult, isSegmentResults: true]'/>
        </div>
      </g:each>

      <div class="form-actions">
        <button type="submit" class="btn">Save</button>
        <a href="${createLink(controller:'dashboard', action:'index')}" class="btn">Cancel</a>
      </div>
    </fieldset>
  </g:form>
</g:else>

<r:script>
  $(document).ready(function () {
    $('form:first *:input[type!=hidden]:first').focus();
  });
</r:script>
</body>
</html>