<%@ page import="com.tgid.tri.results.RaceResult" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap">
  <g:set var="entityName" value="${message(code: 'raceResult.label', default: 'RaceResult')}"/>
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
            <label for="firstName">First Name</label><g:textField name="firstName" value="${filters?.firstName}" class="span12"/>
          </div>

          <div class="row-fluid">
            <label for="lastName">Last Name</label><g:textField name="lastName" value="${filters?.lastName}" class="span12"/>
          </div>

          <div class="row-fluid">
            <label for="username">Race Name</label><g:textField name="name" value="${filters?.name}" class="span12"/>
          </div>
        </div>

        <div class="row-fluid">
          <g:submitButton name="filter" value="Filter" class="btn"/>
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

        %{--<th class="header"><g:message code="raceResult.race.label" default="Race" /></th>--}%

        <g:sortableColumn property="race" title="${message(code: 'raceResult.race.label', default: 'Race')}" params="${filters}"/>

        %{--<th class="header"><g:message code="raceResult.placeAgeGroup.label" default="Place Age Group"/></th>--}%
        %{--<th class="header"><g:message code="raceResult.placeOverall.label" default="Place Overall"/></th>--}%
        %{--<th class="header"><g:message code="raceResult.duration.label" default="Duration"/></th>--}%

        %{--<g:sortableColumn property="placeAgeGroup" title="${message(code: 'raceResult.placeAgeGroup.label', default: 'Place Age Group')}" />--}%

        %{--<g:sortableColumn property="placeOverall" title="${message(code: 'raceResult.placeOverall.label', default: 'Place Overall')}" />--}%

        %{--<g:sortableColumn property="duration" title="${message(code: 'raceResult.duration.label', default: 'Duration')}" />--}%

        <g:sortableColumn property="user" title="${message(code: 'raceResult.user.label', default: 'User')}" params="${filters}"/>


        %{--<th class="header"><g:message code="raceResult.user.label" default="User" /></th>--}%



        <th></th>
      </tr>
      </thead>
      <tbody>
      <g:each in="${raceResultInstanceList}" var="raceResultInstance">
        <tr>

          <td class="span7">${fieldValue(bean: raceResultInstance, field: "race")}</td>

          %{--<td>${fieldValue(bean: raceResultInstance, field: "placeAgeGroup")}</td>--}%
%{----}%
          %{--<td>${fieldValue(bean: raceResultInstance, field: "placeOverall")}</td>--}%
%{----}%
          %{--<td>${fieldValue(bean: raceResultInstance, field: "duration")}</td>--}%

          <td class="span4">${fieldValue(bean: raceResultInstance, field: "user")}</td>

          <td class="link" class="span1">
            <g:link action="show" id="${raceResultInstance.id}" class="btn btn-small">Show &raquo;</g:link>
          </td>
        </tr>
      </g:each>
      </tbody>
    </table>

    <div class="pagination">
      <bootstrap:paginate total="${raceResultInstanceTotal}" params="${filters}"/>
    </div>
  </div>

</div>
</body>
</html>
