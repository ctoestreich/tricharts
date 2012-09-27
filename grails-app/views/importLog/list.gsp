<%@ page import="com.tgid.tri.data.ImportLog" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap">
  <g:set var="entityName" value="${message(code: 'importLog.label', default: 'ImportLog')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
  <r:require module="jquery-ui" />
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
            <label for="name">Import Name</label><g:textField name="name" value="${filters?.name}" class="span12"/>
          </div>

          <div class="row-fluid">
            <label for="error">Error</label><g:select class="span12" from="${[true, false]}" noSelection="['': '']" name="error" value="${filters.error}"/>
          </div>

          <div class="row-fluid">
            <label for="error">Min Date</label><g:textField name="minDate" class="span12 datePicker" value="${filters?.minDate}" />
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

        <g:sortableColumn property="importName" title="${message(code: 'importLog.importName.label', default: 'Import Name')}" params="${filters}"/>

        <g:sortableColumn property="description" title="${message(code: 'importLog.description.label', default: 'Description')}" params="${filters}"/>

        <g:sortableColumn property="complete" title="${message(code: 'importLog.complete.label', default: 'Complete')}" params="${filters}"/>

        <g:sortableColumn property="createDate" title="${message(code: 'importLog.createDate.label', default: 'Create Date')}" params="${filters}"/>

        <g:sortableColumn property="error" title="${message(code: 'importLog.error.label', default: 'Error')}" params="${filters}"/>

        <th></th>
      </tr>
      </thead>
      <tbody>
      <g:each in="${importLogInstanceList}" var="importLogInstance">
        <tr>

          <td>${fieldValue(bean: importLogInstance, field: "importName")}</td>

          <td>${fieldValue(bean: importLogInstance, field: "description")}</td>

          <td><g:formatBoolean boolean="${importLogInstance.complete}"/></td>

          <td><g:formatDate date="${importLogInstance.createDate}"/></td>

          <td><g:formatBoolean boolean="${importLogInstance.error}"/></td>

          <td class="link">
            <g:link controller="coursePatternLocalMap" action="create" params="[mapKey: importLogInstance?.description, importLogId: importLogInstance.id]" class="btn btn-small">Map</g:link>
            <g:link action="show" id="${importLogInstance.id}" class="btn btn-small">Show &raquo;</g:link>
          </td>
        </tr>
      </g:each>
      </tbody>
    </table>

    <div class="pagination">
      <bootstrap:paginate total="${importLogInstanceTotal}" params="${filters}"/>
    </div>
  </div>

</div>

<script>
  $(function(){
    $('.datePicker').datepicker({maxDate: "+1D", changeMonth: true, changeYear: true, dateFormat: "mm/dd/yy", defaultDate: "${params?.minDate}"});
  });
</script>
</body>
</html>
