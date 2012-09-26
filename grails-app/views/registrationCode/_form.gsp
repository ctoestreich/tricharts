<%@ page import="com.tgid.tri.auth.RegistrationCode" %>



<div class="fieldcontain ${hasErrors(bean: registrationCodeInstance, field: 'token', 'error')} ">
	<label for="token">
		<g:message code="registrationCode.token.label" default="Token" />
		
	</label>
	<g:textField name="token" value="${registrationCodeInstance?.token}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationCodeInstance, field: 'username', 'error')} ">
	<label for="username">
		<g:message code="registrationCode.username.label" default="Username" />
		
	</label>
	<g:textField name="username" value="${registrationCodeInstance?.username}"/>
</div>

