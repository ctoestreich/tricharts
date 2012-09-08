<%@ page import="com.tgid.tri.auth.Racer" %>



<div class="fieldcontain ${hasErrors(bean: racerInstance, field: 'racerID', 'error')} required">
	<label for="racerID">
		<g:message code="racer.racerID.label" default="Racer ID" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="racerID" required="" value="${fieldValue(bean: racerInstance, field: 'racerID')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: racerInstance, field: 'lastImport', 'error')} ">
	<label for="lastImport">
		<g:message code="racer.lastImport.label" default="Last Import" />
		
	</label>
	<g:datePicker name="lastImport" precision="day"  value="${racerInstance?.lastImport}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: racerInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="racer.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${com.tgid.tri.auth.User.list()}" optionKey="id" required="" value="${racerInstance?.user?.id}" class="many-to-one"/>
</div>

