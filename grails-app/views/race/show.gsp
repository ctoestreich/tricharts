
<%@ page import="com.tgid.tri.race.Race" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'race.label', default: 'Race')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">
			
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">${entityName}</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
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
					<h1><g:message code="default.show.label" args="[entityName]" /></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<dl>
				
					<g:if test="${raceInstance?.name}">
						<dt><g:message code="race.name.label" default="Name" /></dt>
						
							<dd><g:fieldValue bean="${raceInstance}" field="name"/></dd>
						
					</g:if>
				
					<g:if test="${raceInstance?.date}">
						<dt><g:message code="race.date.label" default="Date" /></dt>
						
							<dd><g:formatDate date="${raceInstance?.date}" /></dd>
						
					</g:if>
				
					<g:if test="${raceInstance?.raceType}">
						<dt><g:message code="race.raceType.label" default="Race Type" /></dt>
						
							<dd><g:fieldValue bean="${raceInstance}" field="raceType"/></dd>
						
					</g:if>
				
					<g:if test="${raceInstance?.distanceType}">
						<dt><g:message code="race.distanceType.label" default="Distance Type" /></dt>
						
							<dd><g:fieldValue bean="${raceInstance}" field="distanceType"/></dd>
						
					</g:if>
				
					<g:if test="${raceInstance?.distance}">
						<dt><g:message code="race.distance.label" default="Distance" /></dt>
						
							<dd><g:fieldValue bean="${raceInstance}" field="distance"/></dd>
						
					</g:if>
				
					<g:if test="${raceInstance?.raceCategoryType}">
						<dt><g:message code="race.raceCategoryType.label" default="Race Category Type" /></dt>
						
							<dd><g:fieldValue bean="${raceInstance}" field="raceCategoryType"/></dd>
						
					</g:if>
				
					<g:if test="${raceInstance?.segments}">
						<dt><g:message code="race.segments.label" default="Segments" /></dt>
						
							<g:each in="${raceInstance.segments}" var="s">
							<dd><g:link controller="raceSegment" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${raceInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${raceInstance?.id}">
							<i class="icon-pencil"></i>
							<g:message code="default.button.edit.label" default="Edit" />
						</g:link>
						<button class="btn btn-danger" type="submit" name="_action_delete">
							<i class="icon-trash icon-white"></i>
							<g:message code="default.button.delete.label" default="Delete" />
						</button>
					</div>
				</g:form>

			</div>

		</div>
	</body>
</html>
