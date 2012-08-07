<%@ page import="com.tgid.tri.results.RaceResult" %>



<div class="fieldcontain ${hasErrors(bean: raceResultInstance, field: 'race', 'error')} required">
    <label for="race">
        <g:message code="raceResult.race.label" default="Race"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="race" name="race.id" from="${com.tgid.tri.race.Race.list()}" optionKey="id" required=""
              value="${raceResultInstance?.race?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceResultInstance, field: 'placeAgeGroup', 'error')} ">
    <label for="placeAgeGroup">
        <g:message code="raceResult.placeAgeGroup.label" default="Place Age Group"/>

    </label>
    <g:field type="number" name="placeAgeGroup"
             value="${fieldValue(bean: raceResultInstance, field: 'placeAgeGroup')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceResultInstance, field: 'placeOverall', 'error')} ">
    <label for="placeOverall">
        <g:message code="raceResult.placeOverall.label" default="Place Overall"/>

    </label>
    <g:field type="number" name="placeOverall" value="${fieldValue(bean: raceResultInstance, field: 'placeOverall')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: raceResultInstance, field: 'duration', 'error')} ">
    <label for="duration">
        <g:message code="raceResult.duration.label" default="Duration"/>

    </label>
    <joda:periodPicker name="duration" value="${raceResultInstance?.duration}"
                       noSelection="['': '']"></joda:periodPicker>
</div>

<div class="fieldcontain ${hasErrors(bean: raceResultInstance, field: 'segmentResult', 'error')} ">
    <label for="segmentResult">
        <g:message code="raceResult.segmentResult.label" default="Segment Result"/>

    </label>

    <ul class="one-to-many">
        <g:each in="${raceResultInstance?.segmentResult ?}" var="s">
            <li><g:link controller="segmentResult" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
        </g:each>
        <li class="add">
            <g:link controller="segmentResult" action="create"
                    params="['raceResult.id': raceResultInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'segmentResult.label', default: 'SegmentResult')])}</g:link>
        </li>
    </ul>

</div>

<div class="fieldcontain ${hasErrors(bean: raceResultInstance, field: 'user', 'error')} required">
    <label for="user">
        <g:message code="raceResult.user.label" default="User"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="user" name="user.id" from="${com.tgid.tri.auth.User.list()}" optionKey="id" required=""
              value="${raceResultInstance?.user?.id}" class="many-to-one"/>
</div>

