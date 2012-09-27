
<%@ page import="com.tgid.tri.data.ImportLog" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'importLog.label', default: 'ImportLog')}" />
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
				
					<g:if test="${importLogInstance?.importName}">
						<dt><g:message code="importLog.importName.label" default="Import Name" /></dt>
						
							<dd><g:fieldValue bean="${importLogInstance}" field="importName"/></dd>
						
					</g:if>
				
					<g:if test="${importLogInstance?.description}">
						<dt><g:message code="importLog.description.label" default="Description" /></dt>
						
							<dd><g:fieldValue bean="${importLogInstance}" field="description"/></dd>
						
					</g:if>
				
					<g:if test="${importLogInstance?.complete}">
						<dt><g:message code="importLog.complete.label" default="Complete" /></dt>
						
							<dd><g:formatBoolean boolean="${importLogInstance?.complete}" /></dd>
						
					</g:if>
				
					<g:if test="${importLogInstance?.createDate}">
						<dt><g:message code="importLog.createDate.label" default="Create Date" /></dt>
						
							<dd><g:formatDate date="${importLogInstance?.createDate}" /></dd>
						
					</g:if>
				
					<g:if test="${importLogInstance?.error}">
						<dt><g:message code="importLog.error.label" default="Error" /></dt>
						
							<dd><g:formatBoolean boolean="${importLogInstance?.error}" /></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${importLogInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${importLogInstance?.id}">
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
