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

