
<%@ page import="com.tgid.tri.race.RaceSegment" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'raceSegment.label', default: 'RaceSegment')}" />
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
				
					<g:if test="${raceSegmentInstance?.race}">
						<dt><g:message code="raceSegment.race.label" default="Race" /></dt>
						
							<dd><g:link controller="race" action="show" id="${raceSegmentInstance?.race?.id}">${raceSegmentInstance?.race?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${raceSegmentInstance?.segment}">
						<dt><g:message code="raceSegment.segment.label" default="Segment" /></dt>
						
							<dd><g:link controller="segment" action="show" id="${raceSegmentInstance?.segment?.id}">${raceSegmentInstance?.segment?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${raceSegmentInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${raceSegmentInstance?.id}">
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
