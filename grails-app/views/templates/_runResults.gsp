<%@ page import="com.tgid.tri.common.JodaTimeHelper" %>
<g:set var="segmentSize" value="${result?.segmentResults?.size() ?: 1}"/>
<g:set var="raceType" value="${result?.race?.raceType}"/>

<div class="accordion-group">
  <div class="accordion-heading">
    <g:render template="/templates/resultsHeading" model="[result:result]"/>
  </div>

  <div id="result-collapse-${result?.id}" class="accordion-body collapse in" style="height:0px">
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
        <td class="clsResultBL"><div id="resT_0"><div>11:26</div></div></td>
        <td class="clsResultBL"><div id="resT_1"><div>01:35</div></div></td>
        <td class="clsResultBL"><div id="resT_2"><div>38:51</div></div></td>
        <td class="clsResultBL"><div id="resT_3"><div>:39</div></div></td>
        <td class="clsResultBL"><div id="resT_4"><div>19:28</div></div></td>
        <td class="clsResultBL" colspan="3" rowspan="2" valign="top" style="border-bottom:1px solid lightGrey"><div style="font-size:33pt; letter-spacing:-.08em" id="finT"><div>1:11:57</div></div></td>
      </tr>
      <tr class="clsResultMed">
        <td><div class="clsCentered">Pace/Speed</div></td>
        <td class="clsResultBL"><div id="resP_0"><div>01:25</div></div></td><td class="clsResultBL"><div id="resP_1"><div>&nbsp;</div></div></td>
        <td class="clsResultBL"><div id="resP_2"><div>23.17</div></div></td><td class="clsResultBL"><div id="resP_3"><div>&nbsp;</div></div></td>
        <td class="clsResultBL"><div id="resP_4"><div>06:29</div></div></td></tr><tr class="clsResultMed clsBorderBot"><td valign="top"><div class="clsCentered">Place (G/O)</div></td>
        <td class="clsResultBL" valign="top" id="celResS_0" style="color: rgb(46, 176, 9); "><div id="resS_0"><div><div class="clsBorderBot">25</div>

          <div>33</div></div></div></td>
        <td class="clsResultBL" valign="top" id="celResS_1" style="color: rgb(71, 180, 14); "><div id="resS_1"><div><div class="clsBorderBot">42</div>

          <div>54</div></div></div></td>
        <td class="clsResultBL" valign="top" id="celResS_2" style="color: rgb(56, 177, 11); "><div id="resS_2"><div><div class="clsBorderBot">31</div>

          <div>32</div></div></div></td>
        <td class="clsResultBL" valign="top" id="celResS_3" style="color: rgb(10, 171, 2); "><div id="resS_3"><div><div class="clsBorderBot">5</div>

          <div>11</div></div></div></td>
        <td class="clsResultBL" valign="top" id="celResS_4" style="color: rgb(36, 175, 7); "><div id="resS_4"><div><div class="clsBorderBot">21</div>

          <div>22</div></div></div></td>
        <td class="cls10pct clsCentered clsResultBL">
          <div id="rnkA"><div class="clsBorderBot">3</div></div>

          <div id="cntA"><div class="clsBorderBot">26</div></div>

          <div id="pctA"><div style="color:#3db20c">12%</div></div></td>
        <td class="cls10pct clsCentered clsResultBL">
          <div id="rnkG"><div class="clsBorderBot">19</div></div>

          <div id="cntG"><div class="clsBorderBot">293</div></div>

          <div id="pctG"><div style="color:#1fae06">6%</div></div></td>
        <td class="cls10pct clsCentered clsResultBL"><div id="rnkO"><div class="clsBorderBot">20</div></div>

          <div id="cntO"><div class="clsBorderBot">466</div></div>

          <div id="pctO"><div style="color:#14ad04">4%</div></div>
        </td>
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


