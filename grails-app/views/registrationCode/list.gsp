
<%@ page import="com.tgid.tri.auth.RegistrationCode" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'registrationCode.label', default: 'RegistrationCode')}" />
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
						
							<g:sortableColumn property="dateCreated" title="${message(code: 'registrationCode.dateCreated.label', default: 'Date Created')}" />
						
							<g:sortableColumn property="token" title="${message(code: 'registrationCode.token.label', default: 'Token')}" />
						
							<g:sortableColumn property="username" title="${message(code: 'registrationCode.username.label', default: 'Username')}" />
						
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${registrationCodeInstanceList}" var="registrationCodeInstance">
						<tr>
						
							<td><g:formatDate date="${registrationCodeInstance.dateCreated}" /></td>
						
							<td>${fieldValue(bean: registrationCodeInstance, field: "token")}</td>
						
							<td>${fieldValue(bean: registrationCodeInstance, field: "username")}</td>
						
							<td class="link">
								<g:link action="show" id="${registrationCodeInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${registrationCodeInstanceTotal}" />
				</div>
			</div>

		</div>
	</body>
</html>
