<%@ page import="com.tgid.tri.results.RaceResults" %>



<div class="fieldcontain ${hasErrors(bean: raceResultsInstance, field: 'race', 'error')} required">
	<label for="race">
		<g:message code="raceResults.race.label" default="Race" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="race" name="race.id" from="${com.tgid.tri.race.Race.list()}" optionKey="id" required="" value="${raceResultsInstance?.race?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceResultsInstance, field: 'duration', 'error')} required">
	<label for="duration">
		<g:message code="raceResults.duration.label" default="Duration" />
		<span class="required-indicator">*</span>
	</label>
	<joda:periodPicker name="duration" value="${raceResultsInstance?.duration}" ></joda:periodPicker>
</div>

<div class="fieldcontain ${hasErrors(bean: raceResultsInstance, field: 'placeAgeGroup', 'error')} required">
	<label for="placeAgeGroup">
		<g:message code="raceResults.placeAgeGroup.label" default="Place Age Group" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="placeAgeGroup" required="" value="${fieldValue(bean: raceResultsInstance, field: 'placeAgeGroup')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceResultsInstance, field: 'placeOverall', 'error')} required">
	<label for="placeOverall">
		<g:message code="raceResults.placeOverall.label" default="Place Overall" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="placeOverall" required="" value="${fieldValue(bean: raceResultsInstance, field: 'placeOverall')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceResultsInstance, field: 'segmentResults', 'error')} ">
	<label for="segmentResults">
		<g:message code="raceResults.segmentResults.label" default="Segment Results" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${raceResultsInstance?.segmentResults?}" var="s">
    <li><g:link controller="segmentResults" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="segmentResults" action="create" params="['raceResults.id': raceResultsInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'segmentResults.label', default: 'SegmentResults')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: raceResultsInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="raceResults.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${com.tgid.tri.auth.User.list()}" optionKey="id" required="" value="${raceResultsInstance?.user?.id}" class="many-to-one"/>
</div>

