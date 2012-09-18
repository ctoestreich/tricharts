<%@ page import="com.tgid.tri.auth.Country; com.tgid.tri.auth.State; com.tgid.tri.race.Race" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'race.label', default: 'Race')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
      <r:require module="widgets" />
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
					<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${raceInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${raceInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>

				<fieldset>
					<g:form class="form-horizontal" action="edit" id="${raceInstance?.id}" >
						<g:hiddenField name="version" value="${raceInstance?.version}" />
						<fieldset>
                          <f:with bean="raceInstance">
                            <f:field property="resultsUrl" />
                            <f:field property="name" />
                            <f:field property="date" />
                            <f:field property="raceType" />
                            <f:field property="distanceType" />
                            <f:field property="distance" input-step="any" input-min="0" />
                            <f:field property="raceCategoryType"  />
                            <f:field property="statusType"  />
                            <f:field property="athlinkRaceID"  />
                            <f:field property="eventCourseID"  />
                            <f:field property="city"  />
                            <f:field property="state"  />
                            <f:field property="country"  />
                            %{--<div class="control-group ">--}%
                              %{--<label class="control-label" for="states">State</label>--}%

                              %{--<div class="controls">--}%
                                %{--<g:select class="span6" optionKey="id" value="${raceInstance?.state?.id}" optionValue="name" id="state" name="state" from="${State?.list()?.sort {it.name}}"/>--}%
                              %{--</div>--}%
                            %{--</div>--}%
                            %{--<div class="control-group ">--}%
                              %{--<label class="control-label" for="states">Country</label>--}%

                              %{--<div class="controls">--}%
                                %{--<g:select class="span6" optionKey="id" value="${raceInstance?.country?.id}" optionValue="countryName" id="country" name="country" from="${Country?.list()?.sort {it.countryName}}"/>--}%
                              %{--</div>--}%
                            %{--</div>--}%

                            <div class="control-group">
                              <label class="control-label" for="coursePattern">Course Pattern</label>
                              <div class="controls">
                                <f:display property="coursePattern" />
                              </div>
                            </div>
                            <f:field property="segments" />
                          </f:with>
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">
									<i class="icon-ok icon-white"></i>
									<g:message code="default.button.update.label" default="Update" />
								</button>
								<button type="submit" class="btn btn-danger" name="_action_delete" formnovalidate>
									<i class="icon-trash icon-white"></i>
									<g:message code="default.button.delete.label" default="Delete" />
								</button>
							</div>
						</fieldset>
					</g:form>
				</fieldset>

			</div>

		</div>
    <script>

    </script>
	</body>
</html>
