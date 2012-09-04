<%@ page import="com.tgid.tri.results.RaceResult" %>

<g:set var="year" value="${maxYear ?: 0}"/>
<g:while test="${year-- >= (minYear ?: 0)}">
  <div class="row well_clear">
    %{--<div class="span12">--}%
    <g:render template="/templates/dashboardHeader" model="[sport: year + 1901 + ' Personal Records', user: params?.user, showButtons: 'no']"/>

    <div id="results-run" class="accordion">
      <g:each in="${types}" var="type">
        <g:set var="key" value="'${year + 1901}_${type}'"/>
        <g:if test="${data.containsKey(key) && data.get(key) != null}">
          <g:set var="result" value="${data.get(key)}"/>
          <div class="accordion-group">
            <div class="accordion-heading">
              <g:render template="/templates/resultsHeading" model="[result: result]"/>
            </div>

            <div id="result-collapse-${result?.id}" class="accordion-body collapse in collapse-run" style="height:0px">
              <g:render template="/templates/runResultTable" model="[result: result]"/>
            </div>
          </div>
        </g:if>
      </g:each>
    </div>
  </div>
</g:while>
<script type="text/javascript">
  $(function () {
    $(".collapse").collapse();
  });
</script>