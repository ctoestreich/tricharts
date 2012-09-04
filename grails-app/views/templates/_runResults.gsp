<%@ page import="com.tgid.tri.race.SegmentType; com.tgid.tri.common.JodaTimeHelper" %>
<g:set var="segmentSize" value="${result?.segmentResults?.size() ?: 1}"/>
<g:set var="raceType" value="${result?.race?.raceType}"/>

<div class="accordion-group">
  <div class="accordion-heading">
    <g:render template="/templates/resultsHeading" model="[result: result]"/>
  </div>

  <div id="result-collapse-${result?.id}" class="accordion-body collapse in collapse-run" style="height:0px">
    <g:render template="/templates/runResultTable" model="[result: result]"/>
    <g:render template="/templates/dashboard/raceResultsButtons" model="[result: result]"/>
  </div>
</div>


