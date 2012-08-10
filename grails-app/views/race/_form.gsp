<%@ page import="com.tgid.tri.race.Race" %>



<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="race.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${raceInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="race.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="day"  value="${raceInstance?.date}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'raceType', 'error')} required">
	<label for="raceType">
		<g:message code="race.raceType.label" default="Race Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="raceType" from="${com.tgid.tri.race.RaceType?.values()}" keys="${com.tgid.tri.race.RaceType.values()*.name()}" required="" value="${raceInstance?.raceType?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'distanceType', 'error')} ">
	<label for="distanceType">
		<g:message code="race.distanceType.label" default="Distance Type" />
		
	</label>
	<g:select name="distanceType" from="${com.tgid.tri.race.DistanceType?.values()}" keys="${com.tgid.tri.race.DistanceType.values()*.name()}" value="${raceInstance?.distanceType?.name()}" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'distance', 'error')} ">
	<label for="distance">
		<g:message code="race.distance.label" default="Distance" />
		
	</label>
	<g:field type="number" name="distance" value="${fieldValue(bean: raceInstance, field: 'distance')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'raceCategoryType', 'error')} ">
	<label for="raceCategoryType">
		<g:message code="race.raceCategoryType.label" default="Race Category Type" />
		
	</label>
	<g:select name="raceCategoryType" from="${com.tgid.tri.race.RaceCategoryType?.values()}" keys="${com.tgid.tri.race.RaceCategoryType.values()*.name()}" value="${raceInstance?.raceCategoryType?.name()}" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'statusType', 'error')} required">
	<label for="statusType">
		<g:message code="race.statusType.label" default="Status Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="statusType" from="${com.tgid.tri.race.StatusType?.values()}" keys="${com.tgid.tri.race.StatusType.values()*.name()}" required="" value="${raceInstance?.statusType?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceInstance, field: 'segments', 'error')} ">
	<label for="segments">
		<g:message code="race.segments.label" default="Segments" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${raceInstance?.segments?}" var="s">
    <li><g:link controller="raceSegment" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="raceSegment" action="create" params="['race.id': raceInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'raceSegment.label', default: 'RaceSegment')])}</g:link>
</li>
</ul>

</div>

