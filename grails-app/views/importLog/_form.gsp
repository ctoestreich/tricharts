<%@ page import="com.tgid.tri.data.ImportLog" %>



<div class="fieldcontain ${hasErrors(bean: importLogInstance, field: 'importName', 'error')} required">
	<label for="importName">
		<g:message code="importLog.importName.label" default="Import Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="importName" required="" value="${importLogInstance?.importName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: importLogInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="importLog.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="1000" value="${importLogInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: importLogInstance, field: 'complete', 'error')} ">
	<label for="complete">
		<g:message code="importLog.complete.label" default="Complete" />
		
	</label>
	<g:checkBox name="complete" value="${importLogInstance?.complete}" />
</div>

<div class="fieldcontain ${hasErrors(bean: importLogInstance, field: 'createDate', 'error')} required">
	<label for="createDate">
		<g:message code="importLog.createDate.label" default="Create Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="createDate" precision="day"  value="${importLogInstance?.createDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: importLogInstance, field: 'error', 'error')} ">
	<label for="error">
		<g:message code="importLog.error.label" default="Error" />
		
	</label>
	<g:checkBox name="error" value="${importLogInstance?.error}" />
</div>

