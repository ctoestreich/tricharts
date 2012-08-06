<%@ page import="com.tgid.tri.race.RaceSegment" %>



<div class="fieldcontain ${hasErrors(bean: raceSegmentInstance, field: 'race', 'error')} required">
	<label for="race">
		<g:message code="raceSegment.race.label" default="Race" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="race" name="race.id" from="${com.tgid.tri.race.Race.list()}" optionKey="id" required="" value="${raceSegmentInstance?.race?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceSegmentInstance, field: 'segment', 'error')} required">
	<label for="segment">
		<g:message code="raceSegment.segment.label" default="Segment" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="segment" name="segment.id" from="${com.tgid.tri.race.Segment.list()}" optionKey="id" required="" value="${raceSegmentInstance?.segment?.id}" class="many-to-one"/>
</div>

