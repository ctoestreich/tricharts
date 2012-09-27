<%@ page import="com.tgid.tri.common.CoursePatternLocalMap" %>



<div class="fieldcontain ${hasErrors(bean: coursePatternLocalMapInstance, field: 'mapKey', 'error')} required">
	<label for="key">
		<g:message code="coursePatternLocalMap.key.label" default="Key" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="key" cols="40" rows="5" maxlength="300" required="" value="${coursePatternLocalMapInstance?.key}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: coursePatternLocalMapInstance, field: 'coursePatternLocal', 'error')} required">
	<label for="coursePatternLocal">
		<g:message code="coursePatternLocalMap.coursePatternLocal.label" default="Course Pattern Local" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="coursePatternLocal" name="coursePatternLocal.id" from="${com.tgid.tri.common.CoursePatternLocal.list()}" optionKey="id" required="" value="${coursePatternLocalMapInstance?.coursePatternLocal?.id}" class="many-to-one"/>
</div>

