<%@ page import="com.tgid.tri.job.JobLog" %>



<div class="fieldcontain ${hasErrors(bean: jobLogInstance, field: 'jobName', 'error')} required">
	<label for="jobName">
		<g:message code="jobLog.jobName.label" default="Job Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="jobName" required="" value="${jobLogInstance?.jobName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: jobLogInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="jobLog.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${jobLogInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: jobLogInstance, field: 'complete', 'error')} ">
	<label for="complete">
		<g:message code="jobLog.complete.label" default="Complete" />
		
	</label>
	<g:checkBox name="complete" value="${jobLogInstance?.complete}" />
</div>

<div class="fieldcontain ${hasErrors(bean: jobLogInstance, field: 'createDate', 'error')} required">
	<label for="createDate">
		<g:message code="jobLog.createDate.label" default="Create Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="createDate" precision="day"  value="${jobLogInstance?.createDate}"  />
</div>

