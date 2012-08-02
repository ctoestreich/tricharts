<%@ page import="com.tgid.tri.race.Segment" %>



<div class="fieldcontain ${hasErrors(bean: segmentInstance, field: 'distance', 'error')} required">
	<label for="distance">
		<g:message code="segment.distance.label" default="Distance" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="distance" required="" value="${fieldValue(bean: segmentInstance, field: 'distance')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentInstance, field: 'distanceType', 'error')} required">
	<label for="distanceType">
		<g:message code="segment.distanceType.label" default="Distance Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="distanceType" from="${com.tgid.tri.race.DistanceType?.values()}" keys="${com.tgid.tri.race.DistanceType.values()*.name()}" required="" value="${segmentInstance?.distanceType?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentInstance, field: 'order', 'error')} required">
	<label for="order">
		<g:message code="segment.order.label" default="Order" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="order" required="" value="${fieldValue(bean: segmentInstance, field: 'order')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentInstance, field: 'race', 'error')} required">
	<label for="race">
		<g:message code="segment.race.label" default="Race" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="race" name="race.id" from="${com.tgid.tri.race.Race.list()}" optionKey="id" required="" value="${segmentInstance?.race?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: segmentInstance, field: 'segmentType', 'error')} required">
	<label for="segmentType">
		<g:message code="segment.segmentType.label" default="Segment Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="segmentType" from="${com.tgid.tri.race.SegmentType?.values()}" keys="${com.tgid.tri.race.SegmentType.values()*.name()}" required="" value="${segmentInstance?.segmentType?.name()}"/>
</div>

