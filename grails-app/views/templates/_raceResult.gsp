<%@ page import="com.tgid.tri.common.JodaTimeHelper" %>
<g:set var="segmentSize" value="${result?.segmentResults?.size() ?: 1}"/>
<table class="table table-bordered table-striped" data-toggle="collapse">
  <thead>
  <tr>
    <th colspan="${segmentSize}"><h4>${result.race.name} ${result.race.date.format("M/dd/yyyy")}</h4></th>
  </tr>
  </thead>
  <tbody>
  <tr>
    <g:each in="${result.segmentResults.sort{a,b -> a.segmentOrder <=> b.segmentOrder }}" var="segment">
      <td width="${100/segmentSize}%">
        <h5>${segment.segment.segmentType}</h5>
      ${segment.segment.distance} ${segment.segment.distanceType}</br>
      <tri:formatDuration duration="${segment.duration}" formatter="${JodaTimeHelper.getPeriodFormat(false, true, true)}" /><br>
      ${segment.pace}
      </td>
    </g:each>
  </tr>
  </tbody>
</table>


