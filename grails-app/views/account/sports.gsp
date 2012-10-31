<%@ page import="com.tgid.tri.race.RaceType; com.tgid.tri.auth.State; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="account"/>
  <title>My Account</title>
  <r:require modules="dashboard"/>
  <gvisualization:apiImport/>
</head>

<body>

Select the sports you wish to view in your results page.
<p>&nbsp;</p>
<g:form class="form-horizontal" controller="account" action="sports">
  <fieldset>
    <g:each in="[com.tgid.tri.race.RaceType.Biking, com.tgid.tri.race.RaceType.Running, com.tgid.tri.race.RaceType.Triathlon]" var="raceType">
    <div class="control-group ">
      <label class="control-label" for="${raceType.raceType}">${raceType.raceType}</label>
      <div class="controls">
        <g:checkBox name="${raceType.raceType}" value="${raceType.raceType}" checked="${userSport.sports.find{ it.raceType == raceType.raceType }}" />
      </div>
    </div>
    </g:each>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">
        <i class="icon-ok icon-white"></i>
        <g:message code="default.button.update.label" default="Update"/>
      </button>
    </div>
  </fieldset>
</g:form>

<script type="text/javascript">
  $(function () {
    $('.autopop').popover();
  });
</script>
</body>
</html>