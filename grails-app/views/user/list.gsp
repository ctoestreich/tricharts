<%@ page import="com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap">
  <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
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
            <label for="username">User Name</label><g:textField name="username" value="${filters?.username}" class="span12"/>
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

        <g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'Username')}" params="${filters}"/>

        <g:sortableColumn property="firstName" title="${message(code: 'user.firstName.label', default: 'First Name')}" params="${filters}"/>

        <g:sortableColumn property="lastName" title="${message(code: 'user.lastName.label', default: 'Last Name')}" params="${filters}"/>
        <th class="header"><g:message code="segmentResult.user.racers.label" default="Rcr"/></th>

        <th></th>
      </tr>
      </thead>
      <tbody>
      <g:each in="${userInstanceList}" var="userInstance">
        <tr>

          <td>${fieldValue(bean: userInstance, field: "username")}</td>

          <td>${fieldValue(bean: userInstance, field: "firstName")}</td>

          <td>${fieldValue(bean: userInstance, field: "lastName")}</td>
          <td>${com.tgid.tri.auth.Racer.findAllByUser(userInstance).size()}</td>

          <td class="link">
            <g:link action="show" id="${userInstance.id}" class="btn btn-small">Show &raquo;</g:link>
            <a href="${createLink(controller: 'admin', action: 'importRacers', params: [userID: userInstance?.id])}" class="btn  btn-small"><i class="icon-download-alt"></i>
              Import Racers
            </a>
          </td>
        </tr>
      </g:each>
      </tbody>
    </table>

    <div class="pagination">
      <bootstrap:paginate total="${userInstanceTotal}" params="${filters}"/>
    </div>
  </div>

</div>
</body>
</html>
