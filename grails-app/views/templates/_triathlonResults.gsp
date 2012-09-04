<%@ page import="com.tgid.tri.race.SegmentType; com.tgid.tri.common.JodaTimeHelper" %>

<div class="accordion-group">
  <div class="accordion-heading">
    <g:render template="/templates/resultsHeading" model="[result:result]"/>
  </div>

  <div id="result-collapse-${result?.id}" class="accordion-body collapse in collapse-triathlon" style="height:0px">
    <g:render template="/templates/triathlonResultTable" model="[result: result]" />

      <g:render template="/templates/dashboard/raceResultsButtons" model="[result: result]" />
  </div>
</div>
%{--<table class="table-condensed" id="table${result.id}">--}%
%{--<thead>--}%
%{--<tr>--}%
%{--<th colspan="2"><h5>${result.race.name} ${result.race.date.format("M/dd/yyyy")}</h5></th>--}%
%{--</tr>--}%
%{--<tr><th>&nbsp;</th><th>Run</th></tr>--}%
%{--</thead>--}%
%{--<tbody>--}%
%{--<tr>--}%
%{--<g:each in="${result.segmentResults.sort{a,b -> a.segmentOrder <=> b.segmentOrder }}" var="segment">--}%
%{--<td width="${100/segmentSize}%">--}%
%{--${segment.segment.distance} ${segment.segment.distanceType} ${segment.segment.segmentType}</br>--}%
%{--Total Time: <tri:formatDuration duration="${segment.duration}" formatter="${JodaTimeHelper.getPeriodFormat(false, true, true)}" /><br>--}%
%{--Pace: ${segment.pace}--}%
%{--</td>--}%
%{--</g:each>--}%
%{--</tr>--}%
%{--</tbody>--}%
%{--</table>--}%


