
<%@ page import="com.tgid.tri.common.CoursePatternLocalMap" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'coursePatternLocalMap.label', default: 'CoursePatternLocalMap')}" />
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
						
							<g:sortableColumn property="key" title="${message(code: 'coursePatternLocalMap.mapKey.label', default: 'Key')}" />
						
							<th class="header"><g:message code="coursePatternLocalMap.coursePatternLocal.label" default="Course Pattern Local" /></th>
						
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${coursePatternLocalMapInstanceList}" var="coursePatternLocalMapInstance">
						<tr>
						
							<td>${fieldValue(bean: coursePatternLocalMapInstance, field: "mapKey")}</td>
						
							<td>${fieldValue(bean: coursePatternLocalMapInstance, field: "coursePatternLocal")}</td>
						
							<td class="link">
								<g:link action="show" id="${coursePatternLocalMapInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${coursePatternLocalMapInstanceTotal}" />
				</div>
			</div>

		</div>
	</body>
</html>
