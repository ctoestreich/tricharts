<%@ page import="com.tgid.tri.Race" %>



<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="race.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${com.tgid.tri.auth.User.list()}" optionKey="id" required="" value="${raceInstance?.user?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'raceType', 'error')} required">
	<label for="raceType">
		<g:message code="race.raceType.label" default="Race Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="raceType" from="${com.tgid.tri.RaceType?.values()}" keys="${com.tgid.tri.RaceType.values()*.name()}" required="" value="${raceInstance?.raceType?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="race.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="day"  value="${raceInstance?.date}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'duration', 'error')} required">
	<label for="duration">
		<g:message code="race.duration.label" default="Duration" />
		<span class="required-indicator">*</span>
	</label>
	<joda:periodPicker name="duration" value="${raceInstance?.duration}" ></joda:periodPicker>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="race.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${raceInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'segments', 'error')} ">
	<label for="segments">
		<g:message code="race.segments.label" default="Segments" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${raceInstance?.segments?}" var="s">
    <li><g:link controller="segment" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="segment" action="create" params="['race.id': raceInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'segment.label', default: 'Segment')])}</g:link>
</li>
</ul>

</div>

