
<%@ page import="com.tgid.tri.auth.Country" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'country.label', default: 'Country')}" />
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
						
							<g:sortableColumn property="countryID" title="${message(code: 'country.countryID.label', default: 'Country ID')}" />
						
							<g:sortableColumn property="countryID3" title="${message(code: 'country.countryID3.label', default: 'Country ID 3')}" />
						
							<g:sortableColumn property="countryName" title="${message(code: 'country.countryName.label', default: 'Country Name')}" />
						
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${countryInstanceList}" var="countryInstance">
						<tr>
						
							<td>${fieldValue(bean: countryInstance, field: "countryID")}</td>
						
							<td>${fieldValue(bean: countryInstance, field: "countryID3")}</td>
						
							<td>${fieldValue(bean: countryInstance, field: "countryName")}</td>
						
							<td class="link">
								<g:link action="show" id="${countryInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${countryInstanceTotal}" />
				</div>
			</div>

		</div>
	</body>
</html>
