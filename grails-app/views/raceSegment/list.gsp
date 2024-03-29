<%@ page import="com.tgid.tri.race.RaceSegment" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap">
  <g:set var="entityName" value="${message(code: 'raceSegment.label', default: 'RaceSegment')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="row-fluid">

  <div class="span3">
    <div class="well">
      <ul class="nav nav-list">
        <li class="nav-header">${entityName}</li>
        <li class="active">
          <g:link class="list" action="list">
            <i class="icon-list icon-white"></i>
            <g:message code="default.list.label" args="[entityName]"/>
          </g:link>
        </li>
        <li>
          <g:link class="create" action="create">
            <i class="icon-plus"></i>
            <g:message code="default.create.label" args="[entityName]"/>
          </g:link>
        </li>
      </ul>
    </div>

    <div class="filters well">
      <div class="row-fluid">
        <span class="nav-header">Filters</span>
      </div>
      <g:form action="list" class="form-inline">
        <div class="row-fluid">
          <div class="row-fluid">
            <label for="name">Name</label><g:textField name="name" value="${filters?.name}" class="span12"/>
          </div>

          <div class="row-fluid">
            <label for="raceType">Race Type</label><g:select class="span12" from="${com.tgid.tri.race.RaceType}" noSelection="['': '']" name="raceType" value="${filters?.raceType}"/>
          </div>

          <div class="row-fluid">
            <label for="raceCategoryType">Race Category Type</label><g:select class="span12" from="${com.tgid.tri.race.RaceCategoryType}" noSelection="['': '']" name="raceCategoryType" value="${filters?.raceCategoryType}"/>
          </div>
        </div>

        <div class="row-fluid">
          <g:submitButton name="filter" value="Filter"/>
        </div>
      </g:form>
    </div>
  </div>

  <div class="span9">

    <div class="page-header">
      <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    </div>

    <g:if test="${flash.message}">
      <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
    </g:if>

    <table class="table table-striped">
      <thead>
      <tr>

        <th class="header"><g:message code="raceSegment.race.label" default="Race" params="${filters}"/></th>

        <th class="header"><g:message code="raceSegment.segment.label" default="Segment" params="${filters}"/></th>

        <th></th>
      </tr>
      </thead>
      <tbody>
      <g:each in="${raceSegmentInstanceList}" var="raceSegmentInstance">
        <tr>

          <td>${fieldValue(bean: raceSegmentInstance, field: "race")}</td>

          <td>${fieldValue(bean: raceSegmentInstance, field: "segment")}</td>

          <td class="link">
            <g:link action="show" id="${raceSegmentInstance.id}" class="btn btn-small">Show &raquo;</g:link>
          </td>
        </tr>
      </g:each>
      </tbody>
    </table>

    <div class="pagination">
      <bootstrap:paginate total="${raceSegmentInstanceTotal}" params="${filters}"/>
    </div>
  </div>

</div>
</body>
</html>
