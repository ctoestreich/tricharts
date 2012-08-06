
<%@ page import="com.tgid.tri.results.RaceResult" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'raceResults.label', default: 'RaceResults')}" />
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
						
							<th class="header"><g:message code="raceResults.race.label" default="Race" /></th>
						
							<g:sortableColumn property="duration" title="${message(code: 'raceResults.duration.label', default: 'Duration')}" />
						
							<g:sortableColumn property="placeAgeGroup" title="${message(code: 'raceResults.placeAgeGroup.label', default: 'Place Age Group')}" />
						
							<g:sortableColumn property="placeOverall" title="${message(code: 'raceResults.placeOverall.label', default: 'Place Overall')}" />
						
							<th class="header"><g:message code="raceResults.user.label" default="User" /></th>
						
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${raceResultsInstanceList}" var="raceResultsInstance">
						<tr>
						
							<td>${fieldValue(bean: raceResultsInstance, field: "race")}</td>
						
							<td>${fieldValue(bean: raceResultsInstance, field: "duration")}</td>
						
							<td>${fieldValue(bean: raceResultsInstance, field: "placeAgeGroup")}</td>
						
							<td>${fieldValue(bean: raceResultsInstance, field: "placeOverall")}</td>
						
							<td>${fieldValue(bean: raceResultsInstance, field: "user")}</td>
						
							<td class="link">
								<g:link action="show" id="${raceResultsInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${raceResultsInstanceTotal}" />
				</div>
			</div>

		</div>
	</body>
</html>
