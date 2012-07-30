<%@ page import="com.tgid.tri.Segment" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap">
  <g:set var="entityName" value="${message(code: 'segment.label', default: 'Segment')}"/>
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

        <g:sortableColumn property="distance" title="${message(code: 'segment.distance.label', default: 'Distance')}"/>

        <g:sortableColumn property="duration" title="${message(code: 'segment.duration.label', default: 'Duration')}"/>

        <th class="header"><g:message code="segment.race.label" default="Race"/></th>

        <g:sortableColumn property="segmentType" title="${message(code: 'segment.segmentType.label', default: 'Segment Type')}"/>

        <th></th>

        <th>Pace</th>
      </tr>
      </thead>
      <tbody>
      <g:each in="${segmentInstanceList}" var="segmentInstance">
        <tr>

          <td>${fieldValue(bean: segmentInstance, field: "distance")}</td>

          <td>${fieldValue(bean: segmentInstance, field: "duration")}</td>

          <td>${fieldValue(bean: segmentInstance, field: "race")}</td>

          <td>${fieldValue(bean: segmentInstance, field: "segmentType")}</td>

          <td class="link">
            <g:link action="show" id="${segmentInstance.id}" class="btn btn-small">Show &raquo;</g:link>
          </td>

          <td>${segmentInstance?.pace}</td>
        </tr>
      </g:each>
      </tbody>
    </table>

    <div class="pagination">
      <bootstrap:paginate total="${segmentInstanceTotal}"/>
    </div>
  </div>

</div>
</body>
</html>