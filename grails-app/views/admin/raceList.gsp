<%@ page import="com.tgid.tri.race.StatusType; com.tgid.tri.race.Race" %>
<g:set var="statusType" value="${params?.statusType ?: StatusType.Pending.value()}"/>
<g:set var="statusTypeText" value="${StatusType.getStatusType(params?.statusType ?: StatusType.Pending.value())}"/>
<!doctype html>
<html>
<head>
    <meta name="layout" content="bootstrap">
    <g:set var="entityName" value="${message(code: 'race.label', default: 'Race')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<div class="row-fluid">
    <div class="well">
        <div class="btn-group-wrap">
            <div class="btn-group">
                <g:link class="btn" controller="admin" action="raceList"
                        params='[statusType: "${com.tgid.tri.race.StatusType.Pending.value()}"]'>Pending Races</g:link>
                <g:link class="btn" controller="admin" action="raceList"
                        params='[statusType: "${com.tgid.tri.race.StatusType.Approved.value()}"]'>Approved Races</g:link>
                <g:link class="btn" controller="admin" action="raceList"
                        params='[statusType: "${com.tgid.tri.race.StatusType.Deleted.value()}"]'>Deleted Races</g:link>
            </div>
        </div>
    </div>
</div>

<div class="row-fluid">

    <div class="span12">

        <div class="page-header">
            <h1>${statusTypeText} <g:message code="default.list.label" args="[entityName]"/></h1>
        </div>

        <g:if test="${flash.message}">
            <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
        </g:if>

        <table class="table table-striped">
            <thead>
            <tr>

                <g:sortableColumn params="[statusType: statusType]" property="name"
                                  title="${message(code: 'race.name.label', default: 'Name')}"/>

                <g:sortableColumn params="[statusType: statusType]" property="date"
                                  title="${message(code: 'race.date.label', default: 'Date')}"/>

                <g:sortableColumn params="[statusType: statusType]" property="raceType"
                                  title="${message(code: 'race.raceType.label', default: 'Race Type')}"/>

                %{--<g:sortableColumn property="distanceType" title="${message(code: 'race.distanceType.label', default: 'Distance Type')}" />--}%
                %{----}%
                %{--<g:sortableColumn property="distance" title="${message(code: 'race.distance.label', default: 'Distance')}" />--}%

                <g:sortableColumn params="[statusType: statusType]" property="raceCategoryType"
                                  title="${message(code: 'race.raceCategoryType.label', default: 'Race Category Type')}"/>
                <g:sortableColumn params="[statusType: statusType]" property="statusType"
                                  title="${message(code: 'race.statusType.label', default: 'Race Status Type')}"/>

                <th></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${raceInstanceList}" var="raceInstance">
                <tr>

                    <td>${fieldValue(bean: raceInstance, field: "name")}</td>

                    <td><g:formatDate date="${raceInstance.date}"/></td>

                    <td>${fieldValue(bean: raceInstance, field: "raceType")}</td>

                    %{--<td>${fieldValue(bean: raceInstance, field: "distanceType")}</td>--}%
                    %{----}%
                    %{--<td>${fieldValue(bean: raceInstance, field: "distance")}</td>--}%

                    <td>${fieldValue(bean: raceInstance, field: "raceCategoryType")}</td>

                    <td>${fieldValue(bean: raceInstance, field: "statusType")}</td>

                    <td class="link">
                        <div class="btn-group">
                            <g:if test="${raceInstance.statusType != com.tgid.tri.race.StatusType.Approved}">
                            <g:link controller="admin" action="updateRaceStatus"
                                    params="[statusType: statusType, 'raceId': raceInstance.id, 'newStatus': StatusType.Approved]"
                                    class="btn btn-small btn-primary"><i class="icon-ok"></i>&nbsp;Approve</g:link>
                            </g:if>
                          <g:if test="${raceInstance.statusType != com.tgid.tri.race.StatusType.Pending}">
                            <g:link controller="admin" action="updateRaceStatus"
                                    params="[statusType: statusType, 'raceId': raceInstance.id, 'newStatus': StatusType.Pending]"
                                    class="btn btn-small btn-info"><i class="icon-adjust"></i>&nbsp;Pending</g:link>
                          </g:if>
                            <g:if test="${raceInstance.statusType != com.tgid.tri.race.StatusType.Deleted}">
                                <g:link controller="admin" action="updateRaceStatus"
                                        params="[statusType: statusType, 'raceId': raceInstance.id, 'newStatus': StatusType.Deleted]"
                                        class="btn btn-small btn-danger"><i class="icon-remove"></i>&nbsp;Delete</g:link>
                            </g:if>
                            <g:link controller="race" action="edit" id="${raceInstance.id}"
                                    class="btn btn-small">Edit &raquo;</g:link>
                        </div>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>

        <div class="pagination">
            <bootstrap:paginate total="${raceInstanceTotal}"/>
        </div>
    </div>

</div>
</body>
</html>
