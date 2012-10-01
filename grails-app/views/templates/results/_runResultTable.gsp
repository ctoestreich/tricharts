<%@ page import="com.tgid.tri.race.SegmentType" %>
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
  <tr class="clsResultMed clsBorderBot"><td valign="top"><div class="clsCentered">Place (AG/O)</div></td>
    <td class="clsResultBL" valign="top" id="celResS_0">
      <div class="clsBorderBot"><tri:placement place="${result?.placeAgeGroup}" overall="${result?.participantsAgeGroup}" /></div>
      %{--<div class="clsBorderBot"><tri:placement place="${result?.segmentResults?.find {it.raceSegment.segmentType == SegmentType.Run}?.placeGender}" overall="${result?.participantsGender}" /></div>--}%
      <div><tri:placement place="${result?.placeOverall}" overall="${result?.participantsOverall}" /></div>
    </td>
    <g:render template="/templates/dashboard/overallStats" model="[result: result]"/>
  </tr>
  </tbody>
</table>