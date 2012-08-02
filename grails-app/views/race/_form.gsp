<%@ page import="com.tgid.tri.race.Race" %>



<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'raceType', 'error')} required">
	<label for="raceType">
		<g:message code="race.raceType.label" default="Race Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="raceType" from="${com.tgid.tri.race.RaceType?.values()}" keys="${com.tgid.tri.race.RaceType.values()*.name()}" required="" value="${raceInstance?.raceType?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="race.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="day"  value="${raceInstance?.date}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'distance', 'error')} required">
	<label for="distance">
		<g:message code="race.distance.label" default="Distance" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="distance" required="" value="${fieldValue(bean: raceInstance, field: 'distance')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'distanceType', 'error')} required">
	<label for="distanceType">
		<g:message code="race.distanceType.label" default="Distance Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="distanceType" from="${com.tgid.tri.race.DistanceType?.values()}" keys="${com.tgid.tri.race.DistanceType.values()*.name()}" required="" value="${raceInstance?.distanceType?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="race.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${raceInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'participantsAgeGroup', 'error')} required">
	<label for="participantsAgeGroup">
		<g:message code="race.participantsAgeGroup.label" default="Participants Age Group" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="participantsAgeGroup" required="" value="${fieldValue(bean: raceInstance, field: 'participantsAgeGroup')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'participantsOverall', 'error')} required">
	<label for="participantsOverall">
		<g:message code="race.participantsOverall.label" default="Participants Overall" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="participantsOverall" required="" value="${fieldValue(bean: raceInstance, field: 'participantsOverall')}"/>
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

