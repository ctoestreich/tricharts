<%@ page import="com.tgid.tri.auth.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="user.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="email" name="username" required="" value="${userInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="user.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="password" required="" value="${userInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'firstName', 'error')} required">
	<label for="firstName">
		<g:message code="user.firstName.label" default="First Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="firstName" required="" value="${userInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'lastName', 'error')} required">
	<label for="lastName">
		<g:message code="user.lastName.label" default="Last Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lastName" required="" value="${userInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'dob', 'error')} required">
	<label for="dob">
		<g:message code="user.dob.label" default="Dob" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dob" precision="day"  value="${userInstance?.dob}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'stateCode', 'error')} required">
	<label for="stateCode">
		<g:message code="user.stateCode.label" default="State Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="stateCode" required="" value="${userInstance?.stateCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountExpired', 'error')} ">
	<label for="accountExpired">
		<g:message code="user.accountExpired.label" default="Account Expired" />
		
	</label>
	<g:checkBox name="accountExpired" value="${userInstance?.accountExpired}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountLocked', 'error')} ">
	<label for="accountLocked">
		<g:message code="user.accountLocked.label" default="Account Locked" />
		
	</label>
	<g:checkBox name="accountLocked" value="${userInstance?.accountLocked}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
	<label for="enabled">
		<g:message code="user.enabled.label" default="Enabled" />
		
	</label>
	<g:checkBox name="enabled" value="${userInstance?.enabled}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'passwordExpired', 'error')} ">
	<label for="passwordExpired">
		<g:message code="user.passwordExpired.label" default="Password Expired" />
		
	</label>
	<g:checkBox name="passwordExpired" value="${userInstance?.passwordExpired}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'racers', 'error')} ">
	<label for="racers">
		<g:message code="user.racers.label" default="Racers" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${userInstance?.racers?}" var="r">
    <li><g:link controller="racer" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="racer" action="create" params="['user.id': userInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'racer.label', default: 'Racer')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'races', 'error')} ">
	<label for="races">
		<g:message code="user.races.label" default="Races" />
		
	</label>
	<g:select name="races" from="${com.tgid.tri.race.Race.list()}" multiple="multiple" optionKey="id" size="5" value="${userInstance?.races*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'states', 'error')} ">
	<label for="states">
		<g:message code="user.states.label" default="States" />
		
	</label>
	<g:select name="states" from="${com.tgid.tri.auth.State.list()}" multiple="multiple" optionKey="id" size="5" value="${userInstance?.states*.id}" class="many-to-many"/>
</div>

