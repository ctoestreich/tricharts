
<%@ page import="com.tgid.tri.job.JobLog" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'jobLog.label', default: 'JobLog')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
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
								<g:message code="default.list.label" args="[entityName]" />
							</g:link>
						</li>
						<li>
							<g:link class="create" action="create">
								<i class="icon-plus"></i>
								<g:message code="default.create.label" args="[entityName]" />
							</g:link>
						</li>
					</ul>
				</div>
			</div>

			<div class="span9">
				
				<div class="page-header">
					<h1><g:message code="default.list.label" args="[entityName]" /></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>
				
				<table class="table table-striped">
					<thead>
						<tr>
						
							<g:sortableColumn property="jobName" title="${message(code: 'jobLog.jobName.label', default: 'Job Name')}" />
						
							<g:sortableColumn property="description" title="${message(code: 'jobLog.description.label', default: 'Description')}" />
						
							<g:sortableColumn property="complete" title="${message(code: 'jobLog.complete.label', default: 'Complete')}" />
						
							<g:sortableColumn property="createDate" title="${message(code: 'jobLog.createDate.label', default: 'Create Date')}" />
						
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${jobLogInstanceList}" var="jobLogInstance">
						<tr>
						
							<td>${fieldValue(bean: jobLogInstance, field: "jobName")}</td>
						
							<td>${fieldValue(bean: jobLogInstance, field: "description")}</td>
						
							<td><g:formatBoolean boolean="${jobLogInstance.complete}" /></td>
						
							<td><g:formatDate date="${jobLogInstance.createDate}" /></td>
						
							<td class="link">
								<g:link action="show" id="${jobLogInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${jobLogInstanceTotal}" />
				</div>
			</div>

		</div>
	</body>
</html>
