<%@ page import="com.tgid.tri.common.CoursePatternLocal; com.tgid.tri.common.CoursePatternLocalMap" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap">
  <g:set var="entityName" value="${message(code: 'coursePatternLocalMap.label', default: 'CoursePatternLocalMap')}"/>
  <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="row-fluid">

  <div class="span3">
    <div class="well">
      <ul class="nav nav-list">
        <li class="nav-header">${entityName}</li>
        <li>
          <g:link class="list" action="list">
            <i class="icon-list"></i>
            <g:message code="default.list.label" args="[entityName]"/>
          </g:link>
        </li>
        <li class="active">
          <g:link class="create" action="create">
            <i class="icon-plus icon-white"></i>
            <g:message code="default.create.label" args="[entityName]"/>
          </g:link>
        </li>
      </ul>
    </div>
  </div>

  <div class="span9">

    <div class="page-header">
      <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    </div>

    <g:if test="${flash.message}">
      <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
    </g:if>

    <g:hasErrors bean="${coursePatternLocalMapInstance}">
      <bootstrap:alert class="alert-error">
        <ul>
          <g:eachError bean="${coursePatternLocalMapInstance}" var="error">
            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
          </g:eachError>
        </ul>
      </bootstrap:alert>
    </g:hasErrors>

    <fieldset>
      <g:form class="form-horizontal" action="create">
        <g:hiddenField name="importLogId" value="${params?.importLogId}" />
        <fieldset>
          <f:with bean="coursePatternLocalMapInstance">
            <f:field property="mapKey" input-class="span12"/>
            %{--<f:field property="coursePatternLocal" input-class="span6"/>--}%
            <div class="control-group ">
              <label for="coursePatternLocal" class="control-label">Course Pattern Local</label>

              <div class="controls">
                <g:select optionKey="id" from="${CoursePatternLocal.list().sort{it.raceCategoryType && it.distance}}" value="${params?.coursePatternLocal?.id}" id="coursePatternLocal" required="" name="coursePatternLocal.id" class="span6"/>
                <g:link controller="coursePatternLocal" action="create" class="btn" params="[returnToMap: true, mapKey: params?.mapKey]">Create</g:link>
              </div>
            </div>
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

</div>
</body>
</html>
