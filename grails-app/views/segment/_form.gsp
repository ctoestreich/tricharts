<%@ page import="com.tgid.tri.Segment" %>


asdf
<div class="fieldcontain ${hasErrors(bean: segmentInstance, field: 'distance', 'error')} required">
	<label for="distance">
		<g:message code="segment.distance.label" default="Distance" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="distance" required="" value="${fieldValue(bean: segmentInstance, field: 'distance')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentInstance, field: 'duration', 'error')} required">
	<label for="duration">
		<g:message code="segment.duration.label" default="Duration" />
		<span class="required-indicator">*</span>
	</label>
	<joda:periodPicker name="duration" value="${segmentInstance?.duration}" ></joda:periodPicker>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentInstance, field: 'race', 'error')} required">
	<label for="race">
		<g:message code="segment.race.label" default="Race" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="race" name="race.id" from="${com.tgid.tri.Race.list()}" optionKey="id" required="" value="${segmentInstance?.race?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentInstance, field: 'segmentType', 'error')} required">
	<label for="segmentType">
		<g:message code="segment.segmentType.label" default="Segment Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="segmentType" from="${com.tgid.tri.SegmentType?.values()}" keys="${com.tgid.tri.SegmentType.values()*.name()}" required="" value="${segmentInstance?.segmentType?.name()}"/>
</div>

