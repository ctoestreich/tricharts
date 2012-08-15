<%@ page import="com.tgid.tri.race.SegmentType; com.tgid.tri.common.JodaTimeHelper" %>

<div class="accordion-group">
  <div class="accordion-heading">
    <g:render template="/templates/resultsHeading" model="[result:result]"/>
  </div>

  <div id="result-collapse-${result?.id}" class="accordion-body collapse in collapse-triathlon" style="height:0px">
    <table width="100%" border="0" cellpadding="3" cellspacing="0" style="background-color:#fff;">
      <tbody>
      <tr>
        <th width="20%" class="clsResultHK"><div>&nbsp;</div></th>
        <th class="clsResultHK"><div id="resH_0"><div>Swim</div></div></th>
        <th class="clsResultHK"><div id="resH_1"><div>Transition</div></div></th>
        <th class="clsResultHK"><div id="resH_2"><div>Bike/Cycle</div></div></th>
        <th class="clsResultHK"><div id="resH_3"><div>Transition</div></div></th>
        <th class="clsResultHK"><div id="resH_4"><div>Run</div></div></th>
        <th class="clsResultHK" colspan="3"><div id="finH">Final Time &amp; Place (A/G/O)</div></th>
      </tr>
      <tr class="clsResultBig">
        <td><div class="clsCentered">Time</div></td>
        <td class="clsResultBL"><tri:formatDuration duration="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Swim}?.duration}"/></td>
        <td class="clsResultBL"><tri:formatDuration duration="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.T1}?.duration}" formatter="${com.tgid.tri.common.JodaTimeHelper.getPeriodFormat(false,true,true)}"/></td>
        <td class="clsResultBL"><tri:formatDuration duration="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Bike}?.duration}"/></td>
        <td class="clsResultBL"><tri:formatDuration duration="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.T2}?.duration}" formatter="${com.tgid.tri.common.JodaTimeHelper.getPeriodFormat(false,true,true)}"/></td>
        <td class="clsResultBL"><tri:formatDuration duration="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.duration}"/></td>
        <td class="clsResultBL" colspan="3" rowspan="2" valign="top" style="border-bottom:1px solid lightGrey"><div style="font-size:33pt; letter-spacing:-.08em" id="finT"><tri:formatDuration duration="${result?.duration}"/></div></td>
      </tr>
      <tr class="clsResultMed">
        <td><div class="clsCentered">Pace/Speed</div></td>
        <td class="clsResultBL">${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Swim}?.pace}</td>
        <td class="clsResultBL">&nbsp;</td>
        <td class="clsResultBL">${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Bike}?.pace}</td>
        <td class="clsResultBL">&nbsp;</td>
        <td class="clsResultBL">${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.pace}</td>
      </tr>
      <tr class="clsResultMed clsBorderBot">
        <td valign="top"><div class="clsCentered">Place (AG/O)</div></td>

        <td class="clsResultBL" valign="top" id="celResS_0">
          <div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Swim}?.placeAgeGroup}" overall="${result?.participantsAgeGroup}" /></div>
          %{--<div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.placeGender}" overall="${result?.participantsGender}" /></div>--}%
          <div><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Swim}?.placeOverall}" overall="${result?.participantsOverall}" /></div>
        </td>
        <td class="clsResultBL" valign="top" id="celResS_0">
          <div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.T1}?.placeAgeGroup}" overall="${result?.participantsAgeGroup}" /></div>
          %{--<div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.placeGender}" overall="${result?.participantsGender}" /></div>--}%
          <div><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.T1}?.placeOverall}" overall="${result?.participantsOverall}" /></div>
        </td>
        <td class="clsResultBL" valign="top" id="celResS_0">
          <div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Bike}?.placeAgeGroup}" overall="${result?.participantsAgeGroup}" /></div>
          %{--<div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.placeGender}" overall="${result?.participantsGender}" /></div>--}%
          <div><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Bike}?.placeOverall}" overall="${result?.participantsOverall}" /></div>
        </td>
        <td class="clsResultBL" valign="top" id="celResS_0">
          <div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.T2}?.placeAgeGroup}" overall="${result?.participantsAgeGroup}" /></div>
          %{--<div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.placeGender}" overall="${result?.participantsGender}" /></div>--}%
          <div><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.T2}?.placeOverall}" overall="${result?.participantsOverall}" /></div>
        </td>
        <td class="clsResultBL" valign="top" id="celResS_0">
          <div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.placeAgeGroup}" overall="${result?.participantsAgeGroup}" /></div>
          %{--<div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.placeGender}" overall="${result?.participantsGender}" /></div>--}%
          <div><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.placeOverall}" overall="${result?.participantsOverall}" /></div>
        </td>

        <g:render template="/templates/dashboard/overallStats" model="[result:result]"/>
      </tr>

      %{--<tr class="clsResultMed">--}%
      %{--<td class="clsCentered"><div class="clsPadTop10"><div class="clsSmallDimB"><span id="lblFName">Christian's</span> Stats:</div>--}%

      %{--<div class="clsPadTop5" style="font-size:20px; letter-spacing:-.08em" id="lblPR"></div></div></td><td class="clsResultBL" colspan="8">--}%
      %{--<table style="width:100%" cellpadding="3" cellspacing="0" border="0"><tbody><tr><td class="clsResultMed clsCentered">RPI</td><td width="60" rowspan="2"><table width="60" cellpadding="0" cellspacing="0" border="0"><tbody><tr class="clsResultMed"><td style="width:10px;">A</td><td style="width:50px;" class="clsCentered"><span id="lblRtgA" style="color: rgb(204, 0, 0); ">-</span></td>--}%
      %{--</tr>--}%
      %{--<tr class="clsResultMed"><td>G</td><td class="clsCentered"><span id="lblRtgG" style="color: rgb(204, 0, 0); ">-</span></td></tr><tr class="clsResultMed"><td>O</td><td class="clsCentered"><span id="lblRtgO" class="" style="color: rgb(204, 0, 0); ">-</span></td></tr></tbody></table></td><td class="clsResultHKBL clsCentered cls10pct">Races</td><td class="clsResultHKBL clsCentered">Best</td><td class="clsResultHK clsCentered">This</td><td class="clsResultHKBL clsCentered">Average</td><td class="clsResultHK clsCentered">This</td>--}%
      %{--</tr>--}%
      %{--<tr class="clsResultMed"><td class="clsCentered"><span style="font-size: 23pt; letter-spacing: -0.1em; color: rgb(204, 0, 0); " id="lblRtgM">-</span></td><td style="font-size:18pt; letter-spacing:-.08em" class="clsResultBL clsCentered"><span id="lblAvg">1</span></td><td style="font-size:18pt; letter-spacing:-.08em" class="clsResultBL clsCentered bgGreenArr" id="celPR"><div id="lblPRVal">1:11:57</div></td><td class="clsCentered"><div id="lblPRDiff" class="clsMargTop5"><div class="clsGreen">—</div></div>--}%
      %{--</td><td style="font-size:18pt; letter-spacing:-.08em" id="celAvg" class="clsResultBL clsCentered bgGreenArr"><div id="lblAVGVal">1:11:57</div></td><td class="clsCentered"><div id="lblAVGDiff" class="clsMargTop5"><div class="clsGreen">—</div></div></td>--}%
      %{--</tr>--}%
      %{--</tbody>--}%
      %{--</table>--}%
      %{--</td>--}%
      %{--</tr>--}%
      </tbody>
    </table>

    <div class="row-fluid">
      <a href="${result?.race?.resultsUrl}" target="_blank">${result?.race?.resultsUrl}</a>
    </div>

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


