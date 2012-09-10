
<%@ page import="com.tgid.tri.results.SegmentResult" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'segmentResult.label', default: 'SegmentResult')}" />
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
					<h1><g:message code="default.show.label" args="['Segment Result']" /></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<dl>
				
					<g:if test="${segmentResultInstance?.raceSegment}">
						<dt><g:message code="segmentResult.raceSegment.label" default="Race Segment" /></dt>
						
							<dd><g:link controller="raceSegment" action="show" id="${segmentResultInstance?.raceSegment?.id}">${segmentResultInstance?.raceSegment?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${segmentResultInstance?.placeAgeGroup}">
						<dt><g:message code="segmentResult.placeAgeGroup.label" default="Place Age Group" /></dt>
						
							<dd><g:fieldValue bean="${segmentResultInstance}" field="placeAgeGroup"/></dd>
						
					</g:if>
				
					<g:if test="${segmentResultInstance?.placeOverall}">
						<dt><g:message code="segmentResult.placeOverall.label" default="Place Overall" /></dt>
						
							<dd><g:fieldValue bean="${segmentResultInstance}" field="placeOverall"/></dd>
						
					</g:if>
				
					<g:if test="${segmentResultInstance?.placeGender}">
						<dt><g:message code="segmentResult.placeGender.label" default="Place Gender" /></dt>
						
							<dd><g:fieldValue bean="${segmentResultInstance}" field="placeGender"/></dd>
						
					</g:if>
				
					<g:if test="${segmentResultInstance?.duration}">
						<dt><g:message code="segmentResult.duration.label" default="Duration" /></dt>
						
							<dd><g:fieldValue bean="${segmentResultInstance}" field="duration"/></dd>
						
					</g:if>
				
					<g:if test="${segmentResultInstance?.raceResult}">
						<dt><g:message code="segmentResult.raceResult.label" default="Race Result" /></dt>
						
							<dd><g:link controller="raceResult" action="show" id="${segmentResultInstance?.raceResult?.id}">${segmentResultInstance?.raceResult?.encodeAsHTML()}</g:link></dd>
						
					</g:if>

              <g:if test="${segmentResultInstance?.raceResult?.user}">
                <dt><g:message code="segmentResult.raceResult.user.label" default="User" /></dt>

                <dd><g:link controller="user" action="show" id="${segmentResultInstance?.raceResult?.user?.id}">${segmentResultInstance?.raceResult?.user?.encodeAsHTML()}</g:link></dd>

              </g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${segmentResultInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${segmentResultInstance?.id}">
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
