<%@ page import="com.tgid.tri.results.SegmentResult" %>



<div class="fieldcontain ${hasErrors(bean: segmentResultInstance, field: 'raceSegment', 'error')} ">
	<label for="raceSegment">
		<g:message code="segmentResult.raceSegment.label" default="Race Segment" />
		
	</label>
	<g:select id="raceSegment" name="raceSegment.id" from="${com.tgid.tri.race.RaceSegment.list()}" optionKey="id" value="${segmentResultInstance?.raceSegment?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentResultInstance, field: 'placeAgeGroup', 'error')} ">
	<label for="placeAgeGroup">
		<g:message code="segmentResult.placeAgeGroup.label" default="Place Age Group" />
		
	</label>
	<g:field type="number" name="placeAgeGroup" value="${fieldValue(bean: segmentResultInstance, field: 'placeAgeGroup')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentResultInstance, field: 'placeOverall', 'error')} ">
	<label for="placeOverall">
		<g:message code="segmentResult.placeOverall.label" default="Place Overall" />
		
	</label>
	<g:field type="number" name="placeOverall" value="${fieldValue(bean: segmentResultInstance, field: 'placeOverall')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentResultInstance, field: 'duration', 'error')} required">
	<label for="duration">
		<g:message code="segmentResult.duration.label" default="Duration" />
		<span class="required-indicator">*</span>
	</label>
	<joda:periodPicker name="duration" value="${segmentResultInstance?.duration}" ></joda:periodPicker>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentResultInstance, field: 'raceResult', 'error')} required">
	<label for="raceResult">
		<g:message code="segmentResult.raceResult.label" default="Race Result" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="raceResult" name="raceResult.id" from="${com.tgid.tri.results.RaceResult.list()}" optionKey="id" required="" value="${segmentResultInstance?.raceResult?.id}" class="many-to-one"/>
</div>

