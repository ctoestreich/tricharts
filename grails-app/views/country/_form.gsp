<%@ page import="com.tgid.tri.auth.Country" %>



<div class="fieldcontain ${hasErrors(bean: countryInstance, field: 'countryID', 'error')} required">
	<label for="countryID">
		<g:message code="country.countryID.label" default="Country ID" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="countryID" required="" value="${countryInstance?.countryID}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: countryInstance, field: 'countryID3', 'error')} required">
	<label for="countryID3">
		<g:message code="country.countryID3.label" default="Country ID 3" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="countryID3" required="" value="${countryInstance?.countryID3}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: countryInstance, field: 'countryName', 'error')} required">
	<label for="countryName">
		<g:message code="country.countryName.label" default="Country Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="countryName" required="" value="${countryInstance?.countryName}"/>
</div>

