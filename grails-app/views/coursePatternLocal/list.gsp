
<%@ page import="com.tgid.tri.common.CoursePatternLocal" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'coursePatternLocal.label', default: 'CoursePatternLocal')}" />
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
						
							<g:sortableColumn property="distance" title="${message(code: 'coursePatternLocal.distance.label', default: 'Distance')}" />
						
							<g:sortableColumn property="distanceType" title="${message(code: 'coursePatternLocal.distanceType.label', default: 'Distance Type')}" />
						
							<g:sortableColumn property="raceCategoryType" title="${message(code: 'coursePatternLocal.raceCategoryType.label', default: 'Race Category Type')}" />
						
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${coursePatternLocalInstanceList}" var="coursePatternLocalInstance">
						<tr>
						
							<td>${fieldValue(bean: coursePatternLocalInstance, field: "distance")}</td>
						
							<td>${fieldValue(bean: coursePatternLocalInstance, field: "distanceType")}</td>
						
							<td>${fieldValue(bean: coursePatternLocalInstance, field: "raceCategoryType")}</td>
						
							<td class="link">
								<g:link action="show" id="${coursePatternLocalInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${coursePatternLocalInstanceTotal}" />
				</div>
			</div>

		</div>
	</body>
</html>
