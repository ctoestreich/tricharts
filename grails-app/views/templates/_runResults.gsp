<%@ page import="com.tgid.tri.race.SegmentType; com.tgid.tri.common.JodaTimeHelper" %>
<g:set var="segmentSize" value="${result?.segmentResults?.size() ?: 1}"/>
<g:set var="raceType" value="${result?.race?.raceType}"/>

<div class="accordion-group">
  <div class="accordion-heading">
    <g:render template="/templates/resultsHeading" model="[result: result]"/>
  </div>

  <div id="result-collapse-${result?.id}" class="accordion-body collapse in collapse-run" style="height:0px">
    <table width="100%" border="0" cellpadding="3" cellspacing="0" style="background-color:#fff;">
      <tbody>
      <tr>
        <th width="20%" class="clsResultHK"><div>&nbsp;</div></th>
        <th class="clsResultHK"><div id="resH_4"><div>Run</div></div></th>
        <th class="clsResultHK" colspan="3"><div id="finH">Final Time &amp; Place (A/G/O)</div></th>
      </tr>
      <tr class="clsResultBig">
        <td><div class="clsCentered">Time</div></td>
        <td class="clsResultBL"><tri:formatDuration duration="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.duration}"/></td>
        <td class="clsResultBL" colspan="3" rowspan="2" valign="top" style="border-bottom:1px solid lightGrey"><div style="font-size:33pt; letter-spacing:-.08em" id="finT">
          <tri:formatDuration duration="${result?.duration}"/></div>
        </td>
      </tr>
      <tr class="clsResultMed">
        <td><div class="clsCentered">Pace/Speed</div></td>
        <td class="clsResultBL">${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.pace}</td>
      </tr>
      <tr class="clsResultMed clsBorderBot"><td valign="top"><div class="clsCentered">Place (G/O)</div></td>
        <td class="clsResultBL" valign="top" id="celResS_0">
          <div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.placeAgeGroup}" overall="${result?.participantsAgeGroup}" /></div>
          %{--<div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.placeGender}" overall="${result?.participantsGender}" /></div>--}%
          <div><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.placeOverall}" overall="${result?.participantsOverall}" /></div>
        </td>
        <g:render template="/templates/dashboard/overallStats" model="[result: result]"/>
      </tr>
      </tbody>
    </table>

    <g:render template="/templates/dashboard/raceResultsButtons" model="[result: result]"/>
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


