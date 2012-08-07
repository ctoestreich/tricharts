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

<g:if test="${flash.message}">
    <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
</g:if>

<g:hasErrors bean="${raceResult}">
    <bootstrap:alert class="alert-error">
        <ul>
            <g:eachError bean="${raceResult}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </bootstrap:alert>
</g:hasErrors>

<g:if test="${!race}">
<g:form id="createRunResult" class="form-inline well" controller="dashboard" action="createRunResult">
  <g:select optionKey="id" id="raceResult.race" from="${runs}" name="raceResult.race" />
  <button type="submit" class="btn">Next -></button>
</g:form>
</g:if>
<g:else>
  <g:form id="saveRunResult" class="form-horizontal well" controller="dashboard" action="saveRunResult">
    <input type="hidden" name="raceResult.race" value="${race.id}" id="raceResult.race">
    <input type="hidden" name="segmentCount" value="${raceResult?.segmentResults?.size()}" id="segmentCount">


    <fieldset>
      <legend>${race}</legend>

      <g:render template="/templates/resultsInput" model="[name:'Race']" />

      <g:each in="${raceResult.segmentResults?.sort{a,b -> a.segmentOrder <=> b.segmentOrder}}" var="segmentResult" status="i">
        <div class="well">
          <g:render template="/templates/resultsInput" model='[name: "Segment",prefix:"segmentResult[${i}].",segmentResult:segmentResult]' />
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