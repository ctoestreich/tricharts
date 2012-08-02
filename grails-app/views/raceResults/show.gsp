
<%@ page import="com.tgid.tri.results.RaceResults" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'raceResults.label', default: 'RaceResults')}" />
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
				
					<g:if test="${raceResultsInstance?.race}">
						<dt><g:message code="raceResults.race.label" default="Race" /></dt>
						
							<dd><g:link controller="race" action="show" id="${raceResultsInstance?.race?.id}">${raceResultsInstance?.race?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${raceResultsInstance?.duration}">
						<dt><g:message code="raceResults.duration.label" default="Duration" /></dt>
						
							<dd><g:fieldValue bean="${raceResultsInstance}" field="duration"/></dd>
						
					</g:if>
				
					<g:if test="${raceResultsInstance?.placeAgeGroup}">
						<dt><g:message code="raceResults.placeAgeGroup.label" default="Place Age Group" /></dt>
						
							<dd><g:fieldValue bean="${raceResultsInstance}" field="placeAgeGroup"/></dd>
						
					</g:if>
				
					<g:if test="${raceResultsInstance?.placeOverall}">
						<dt><g:message code="raceResults.placeOverall.label" default="Place Overall" /></dt>
						
							<dd><g:fieldValue bean="${raceResultsInstance}" field="placeOverall"/></dd>
						
					</g:if>
				
					<g:if test="${raceResultsInstance?.segmentResults}">
						<dt><g:message code="raceResults.segmentResults.label" default="Segment Results" /></dt>
						
							<g:each in="${raceResultsInstance.segmentResults}" var="s">
							<dd><g:link controller="segmentResults" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
				
					<g:if test="${raceResultsInstance?.user}">
						<dt><g:message code="raceResults.user.label" default="User" /></dt>
						
							<dd><g:link controller="user" action="show" id="${raceResultsInstance?.user?.id}">${raceResultsInstance?.user?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${raceResultsInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${raceResultsInstance?.id}">
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
