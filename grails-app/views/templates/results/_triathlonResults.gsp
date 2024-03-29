<%@ page import="com.tgid.tri.race.SegmentType; com.tgid.tri.common.JodaTimeHelper" %>

<div class="accordion-group">
  <div class="accordion-heading">
    <g:render template="/templates/results/resultsHeading" model="[result: result]"/>
  </div>

  <div id="result-collapse-${result?.id}" class="accordion-body collapse collapse-triathlon">
    <g:render template="/templates/results/triathlonResultTable" model="[result: result]"/>

    <g:render template="/templates/dashboard/raceResultsButtons" model="[result: result]"/>
  </div>
</div>

