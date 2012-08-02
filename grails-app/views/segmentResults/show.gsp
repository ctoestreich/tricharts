
<%@ page import="com.tgid.tri.results.SegmentResults" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'segmentResults.label', default: 'SegmentResults')}" />
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
				
					<g:if test="${segmentResultsInstance?.duration}">
						<dt><g:message code="segmentResults.duration.label" default="Duration" /></dt>
						
							<dd><g:fieldValue bean="${segmentResultsInstance}" field="duration"/></dd>
						
					</g:if>
				
					<g:if test="${segmentResultsInstance?.placeAgeGroup}">
						<dt><g:message code="segmentResults.placeAgeGroup.label" default="Place Age Group" /></dt>
						
							<dd><g:fieldValue bean="${segmentResultsInstance}" field="placeAgeGroup"/></dd>
						
					</g:if>
				
					<g:if test="${segmentResultsInstance?.placeOverall}">
						<dt><g:message code="segmentResults.placeOverall.label" default="Place Overall" /></dt>
						
							<dd><g:fieldValue bean="${segmentResultsInstance}" field="placeOverall"/></dd>
						
					</g:if>
				
					<g:if test="${segmentResultsInstance?.raceResults}">
						<dt><g:message code="segmentResults.raceResults.label" default="Race Results" /></dt>
						
							<dd><g:link controller="raceResults" action="show" id="${segmentResultsInstance?.raceResults?.id}">${segmentResultsInstance?.raceResults?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${segmentResultsInstance?.segment}">
						<dt><g:message code="segmentResults.segment.label" default="Segment" /></dt>
						
							<dd><g:link controller="segment" action="show" id="${segmentResultsInstance?.segment?.id}">${segmentResultsInstance?.segment?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${segmentResultsInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${segmentResultsInstance?.id}">
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
