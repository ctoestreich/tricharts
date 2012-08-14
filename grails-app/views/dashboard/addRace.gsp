<%@ page import="com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>Add A Race</title>
  <r:require modules="dashboard,results"/>
  <gvisualization:apiImport/>
</head>

<body>

<div class="page-header">
  <h1>Add A Race&nbsp;<small>races are fun</small></h1>
</div>

<div class="row-fluid">
  <g:if test="${flash.message}">
    <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
  </g:if>

  <g:hasErrors bean="${raceInstance}">
    <bootstrap:alert class="alert-error">
      <ul>
        <g:eachError bean="${raceInstance}" var="error">
          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
      </ul>
    </bootstrap:alert>
  </g:hasErrors>
</div>

<div class="row-fluid">
  <fieldset>
    <g:form class="form-horizontal" action="addRace">
      <fieldset>

        <f:with bean="raceInstance">
          <f:field property="resultsUrl" />
          <f:field property="name" />
          <f:field property="date" />
          <f:field property="raceType" />
          <f:field property="distanceType" />
          <f:field property="distance" input-step="any" input-min="0" />
          <f:field property="raceCategoryType"  />
        </f:with>


        <div class="form-actions">
          <button type="submit" class="btn btn-primary">
            <i class="icon-ok icon-white"></i>
            <g:message code="default.button.create.label" default="Create"/>
          </button>
        </div>
      </fieldset>
    </g:form>
  </fieldset>
</div>

</body>
</html>