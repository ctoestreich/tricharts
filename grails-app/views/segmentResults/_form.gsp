<%@ page import="com.tgid.tri.results.SegmentResults" %>



<div class="fieldcontain ${hasErrors(bean: segmentResultsInstance, field: 'segment', 'error')} required">
	<label for="segment">
		<g:message code="segmentResults.segment.label" default="Segment" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="segment" name="segment.id" from="${com.tgid.tri.race.Segment.list()}" optionKey="id" required="" value="${segmentResultsInstance?.segment?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentResultsInstance, field: 'placeAgeGroup', 'error')} ">
	<label for="placeAgeGroup">
		<g:message code="segmentResults.placeAgeGroup.label" default="Place Age Group" />
		
	</label>
	<g:field type="number" name="placeAgeGroup" value="${fieldValue(bean: segmentResultsInstance, field: 'placeAgeGroup')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentResultsInstance, field: 'placeOverall', 'error')} ">
	<label for="placeOverall">
		<g:message code="segmentResults.placeOverall.label" default="Place Overall" />
		
	</label>
	<g:field type="number" name="placeOverall" value="${fieldValue(bean: segmentResultsInstance, field: 'placeOverall')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentResultsInstance, field: 'duration', 'error')} required">
	<label for="duration">
		<g:message code="segmentResults.duration.label" default="Duration" />
		<span class="required-indicator">*</span>
	</label>
	<joda:periodPicker name="duration" value="${segmentResultsInstance?.duration}" ></joda:periodPicker>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentResultsInstance, field: 'raceResults', 'error')} required">
	<label for="raceResults">
		<g:message code="segmentResults.raceResults.label" default="Race Results" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="raceResults" name="raceResults.id" from="${com.tgid.tri.results.RaceResults.list()}" optionKey="id" required="" value="${segmentResultsInstance?.raceResults?.id}" class="many-to-one"/>
</div>

