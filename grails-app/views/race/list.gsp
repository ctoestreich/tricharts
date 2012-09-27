<%@ page import="com.tgid.tri.race.Race" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap">
  <g:set var="entityName" value="${message(code: 'race.label', default: 'Race')}"/>
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

          <div class="row-fluid">
            <label for="raceCategoryType">State</label><g:select class="span12" from="${com.tgid.tri.auth.State.list()}" optionKey="abbrev" optionValue="name" noSelection="['': '']" name="state" value="${filters?.state}"/>
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

        <g:sortableColumn property="name" title="${message(code: 'race.name.label', default: 'Name')}" params="${filters}"/>

        <g:sortableColumn property="date" title="${message(code: 'race.date.label', default: 'Date')}" params="${filters}"/>

        <g:sortableColumn property="raceType" title="${message(code: 'race.raceType.label', default: 'Race Type')}" params="${filters}"/>

        %{--<g:sortableColumn property="distanceType" title="${message(code: 'race.distanceType.label', default: 'Distance Type')}" />--}%
        %{----}%
        %{--<g:sortableColumn property="distance" title="${message(code: 'race.distance.label', default: 'Distance')}" />--}%

        <g:sortableColumn property="raceCategoryType" title="${message(code: 'race.raceCategoryType.label', default: 'Category')}" params="${filters}"/>
        <g:sortableColumn property="statusType" title="${message(code: 'race.statusType.label', default: 'Status')}" params="${filters}"/>

        <th></th>
      </tr>
      </thead>
      <tbody>
      <g:each in="${raceInstanceList}" var="raceInstance">
        <tr>

          <td class="span4">${fieldValue(bean: raceInstance, field: "name")}</td>

          <td><g:formatDate date="${raceInstance.date}" format="M/dd/yyyy"/></td>

          <td>${fieldValue(bean: raceInstance, field: "raceType")}</td>

          %{--<td>${fieldValue(bean: raceInstance, field: "distanceType")}</td>--}%
          %{----}%
          %{--<td>${fieldValue(bean: raceInstance, field: "distance")}</td>--}%

          <td>${fieldValue(bean: raceInstance, field: "raceCategoryType")}</td>

          <td>${fieldValue(bean: raceInstance, field: "statusType")}</td>

          <td class="link">
            <g:link action="show" id="${raceInstance.id}" class="btn btn-small">Show &raquo;</g:link>
          </td>
        </tr>
      </g:each>
      </tbody>
    </table>

    <div class="pagination">
      <bootstrap:paginate total="${raceInstanceTotal}" params="${filters}"/>
    </div>
  </div>

</div>
</body>
</html>
