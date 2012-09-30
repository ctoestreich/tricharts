<%@ page import="com.tgid.tri.common.CoursePatternLocal" %>



<div class="fieldcontain ${hasErrors(bean: coursePatternLocalInstance, field: 'distance', 'error')} required">
	<label for="distance">
		<g:message code="coursePatternLocal.distance.label" default="Distance" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="distance" min="0.0" step="any" required="" value="${fieldValue(bean: coursePatternLocalInstance, field: 'distance')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: coursePatternLocalInstance, field: 'distanceType', 'error')} required">
	<label for="distanceType">
		<g:message code="coursePatternLocal.distanceType.label" default="Distance Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="distanceType" from="${com.tgid.tri.race.DistanceType?.values()}" keys="${com.tgid.tri.race.DistanceType.values()*.name()}" required="" value="${coursePatternLocalInstance?.distanceType?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: coursePatternLocalInstance, field: 'raceCategoryType', 'error')} required">
	<label for="raceCategoryType">
		<g:message code="coursePatternLocal.raceCategoryType.label" default="Race Category Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="raceCategoryType" from="${com.tgid.tri.race.RaceCategoryType?.values()}" keys="${com.tgid.tri.race.RaceCategoryType.values()*.name()}" required="" value="${coursePatternLocalInstance?.raceCategoryType?.name()}"/>
</div>

