<%@ page import="com.tgid.tri.results.RaceResult" %>

<g:set var="year" value="${maxYear ?: 0}"/>
<g:while test="${year-- >= (minYear ?: 0)}">
  <div class="row well_clear">
    %{--<div class="span12">--}%
    <g:render template="/templates/dashboardHeader" model="[sport: year + 1901 + ' Personal Records', user: params?.user, showButtons: 'no']"/>

    <div id="results-run" class="accordion in">
      <g:each in="${types}" var="type">
        <g:set var="key" value="'${year + 1901}_${type}'"/>
        <g:if test="${data.containsKey(key) && data.get(key) != null}">
          <g:render template="/templates/runResults" collection="${[data.get(key)]}" var="result"/>
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