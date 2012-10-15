<h4>${segmentResult?.raceSegment?.segment}</h4>
<input type="hidden" name="${prefix}raceSegment.id" value="${segmentResult?.raceSegment?.id}"/>
<g:if test="${segmentResult?.id}"><input type="hidden" name="${prefix}id" value="${segmentResult?.id}"/></g:if>

%{--<label class="control-label" for="${prefix}duration">${name} Duration</label>--}%
%{--<div class="controls">--}%
<g:if test="${segmentResult}">
%{--<tri:periodPicker id="${segmentResult?.id}" value="${segmentResult?.duration}" prefix="${prefix}" />--}%
%{--<BR>--}%
  <f:field property="duration" bean="segmentResult" prefix="${prefix}"/>
</g:if>
<g:elseif test="${raceResult}">
%{--<tri:periodPicker id="${raceResult?.id}" value="${raceResult?.duration}" prefix="${prefix}"/>--}%
%{--<BR />--}%
  <f:field property="duration" bean="raceResult" prefix="${prefix}"/>
  <f:field property="age" bean="raceResult" prefix="${prefix}"/>
  <f:field property="bibNumber" bean="raceResult" prefix="${prefix}"/>
  <f:field property="genderType" bean="raceResult" prefix="${prefix}"/>
</g:elseif>

%{--<joda:periodPicker name="${prefix ?: ''}duration" value="${segmentResult?.duration}" fields="hours,minutes,seconds" />--}%




<g:if test="${segmentResult}">
  <f:field bean="segmentResult" property="placeAgeGroup" prefix="${prefix}"/>
</g:if>
<g:elseif test="${raceResult}">
  <f:field bean="raceResult" property="placeAgeGroup" prefix="${prefix}"/>
</g:elseif>

%{--<label class="control-label" for="${prefix}placeAgeGroup">${name} Place Age Group</label>--}%

%{--<div class="controls">--}%
%{--<input type="number" name="${prefix}placeAgeGroup" value="${segmentResult?.placeAgeGroup}"/>--}%
%{--</div>--}%


<g:if test="${segmentResult}">
  <f:field bean="segmentResult" property="placeGender" prefix="${prefix}"/>
</g:if>
<g:elseif test="${raceResult}">
  <f:field bean="raceResult" property="placeGender" prefix="${prefix}"/>
</g:elseif>

<g:if test="${segmentResult}">
  <f:field bean="segmentResult" property="placeOverall" prefix="${prefix}"/>
</g:if>
<g:elseif test="${raceResult}">
  <f:field bean="raceResult" property="placeOverall" prefix="${prefix}"/>
</g:elseif>

<g:if test="${isRaceResults}">
  <f:field bean="raceResult" property="participantsAgeGroup"/>
  <f:field bean="raceResult" property="participantsGender"/>
  <f:field bean="raceResult" property="participantsOverall"/>
</g:if>