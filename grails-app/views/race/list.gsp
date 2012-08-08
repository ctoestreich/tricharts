
<%@ page import="com.tgid.tri.race.Race" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'race.label', default: 'Race')}" />
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
						
							<g:sortableColumn property="name" title="${message(code: 'race.name.label', default: 'Name')}" />
						
							<g:sortableColumn property="date" title="${message(code: 'race.date.label', default: 'Date')}" />
						
							<g:sortableColumn property="raceType" title="${message(code: 'race.raceType.label', default: 'Race Type')}" />
						
							<g:sortableColumn property="distanceType" title="${message(code: 'race.distanceType.label', default: 'Distance Type')}" />
						
							<g:sortableColumn property="distance" title="${message(code: 'race.distance.label', default: 'Distance')}" />
						
							<g:sortableColumn property="raceCategoryType" title="${message(code: 'race.raceCategoryType.label', default: 'Race Category Type')}" />
						
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${raceInstanceList}" var="raceInstance">
						<tr>
						
							<td>${fieldValue(bean: raceInstance, field: "name")}</td>
						
							<td><g:formatDate date="${raceInstance.date}" /></td>
						
							<td>${fieldValue(bean: raceInstance, field: "raceType")}</td>
						
							<td>${fieldValue(bean: raceInstance, field: "distanceType")}</td>
						
							<td>${fieldValue(bean: raceInstance, field: "distance")}</td>
						
							<td>${fieldValue(bean: raceInstance, field: "raceCategoryType")}</td>
						
							<td class="link">
								<g:link action="show" id="${raceInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${raceInstanceTotal}" />
				</div>
			</div>

		</div>
	</body>
</html>
