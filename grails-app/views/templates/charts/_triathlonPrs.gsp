<%@ page import="com.tgid.tri.race.SegmentType; com.tgid.tri.results.RaceResult" %>
%{--${data}--}%
<g:set var="year" value="${maxYear ?: 0}"/>
<g:while test="${year-- >= (minYear ?: 0)}">
  <div class="row well_clear">
    %{--<div class="span12">--}%
    <g:render template="/templates/dashboardHeader" model="[sport: year + 1901 + ' Personal Records', user: params?.user, showButtons: 'no']"/>

    <div id="results-run" class="accordion">
      <g:each in="${types}" var="type">
        <h5>${type}</h5>
        <g:each in="${[SegmentType.Swim, SegmentType.Bike, SegmentType.Run]}" var="segmentType">
          <g:set var="key" value="'${year + 1901}_${type}_${segmentType}'"/>
          <g:if test="${data.containsKey(key) && data.get(key) != null}">
            <g:set var="result" value="${data.get(key)}"/>
            <div class="accordion-group">
              <div class="accordion-heading">
                <div class="row-fluid">
                  <div class="span4 clsResultBig"><a class="accordion-toggle" data-toggle="collapse" href="#result-collapse-${result?.id}">${result?.raceResult?.race}</a></div>

                  <div class="span2 clsResultBig"><span class="accordion-toggle">${segmentType}</span></div>

                  <div class="span2 clsResultBig"><span class="accordion-toggle">${result?.raceResult?.date?.format("M/dd/yyyy")}</span></div>

                  <div class="span2 clsResultBig"><span class="accordion-toggle">${result?.placeAgeGroup}/${result?.placeGender ?: '?'}/${result?.placeOverall}</span></div>

                  <div class="span2 clsResultBig"><span class="accordion-toggle"><tri:formatDuration duration="${result?.duration}"/> <tri:displayPace pace="${result?.pace}"/></span></div>
                </div>
              </div>
            </div>

            <div id="result-collapse-${result?.id}" class="accordion-body collapse collapse-triathlon" style="height:0px">
              <g:render template="/templates/triathlonResultTable" model="[result: result?.raceResult]"/>
            </div>
          </g:if>
        </g:each>
      </g:each>
    </div>
  </div>
</g:while>
<script type="text/javascript">
  $(function () {
    $('.collapse').collapse({
                              toggle: false
                            });
    $('.autopop').popover();
  });
</script>