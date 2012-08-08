
<%@ page import="com.tgid.tri.results.SegmentResult" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'segmentResult.label', default: 'SegmentResult')}" />
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
						
							<th class="header"><g:message code="segmentResult.raceSegment.label" default="Race Segment" /></th>
						
							<g:sortableColumn property="placeAgeGroup" title="${message(code: 'segmentResult.placeAgeGroup.label', default: 'Place Age Group')}" />
						
							<g:sortableColumn property="placeOverall" title="${message(code: 'segmentResult.placeOverall.label', default: 'Place Overall')}" />
						
							<g:sortableColumn property="placeGender" title="${message(code: 'segmentResult.placeGender.label', default: 'Place Gender')}" />
						
							<g:sortableColumn property="duration" title="${message(code: 'segmentResult.duration.label', default: 'Duration')}" />
						
							<th class="header"><g:message code="segmentResult.raceResult.label" default="Race Result" /></th>
						
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${segmentResultInstanceList}" var="segmentResultInstance">
						<tr>
						
							<td>${fieldValue(bean: segmentResultInstance, field: "raceSegment")}</td>
						
							<td>${fieldValue(bean: segmentResultInstance, field: "placeAgeGroup")}</td>
						
							<td>${fieldValue(bean: segmentResultInstance, field: "placeOverall")}</td>
						
							<td>${fieldValue(bean: segmentResultInstance, field: "placeGender")}</td>
						
							<td>${fieldValue(bean: segmentResultInstance, field: "duration")}</td>
						
							<td>${fieldValue(bean: segmentResultInstance, field: "raceResult")}</td>
						
							<td class="link">
								<g:link action="show" id="${segmentResultInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${segmentResultInstanceTotal}" />
				</div>
			</div>

		</div>
	</body>
</html>
