<%@ page import="grails.converters.JSON; com.tgid.tri.auth.State; com.tgid.tri.auth.Country; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>Add A Race</title>
  <r:require modules="dashboard,results"/>
  <gvisualization:apiImport/>
</head>

<body>

<div class="page-header">
  <h1>Add A Race&nbsp;<small>races are fun</small></h1>
</div>

<div class="row-fluid">
  <g:if test="${flash.message}">
    <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
  </g:if>

  <g:hasErrors bean="${raceInstance}">
    <bootstrap:alert class="alert-error">
      <ul>
        <g:eachError bean="${raceInstance}" var="error">
          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
      </ul>
    </bootstrap:alert>
  </g:hasErrors>
</div>

<div class="row-fluid">
  <fieldset>
    <g:form class="form-horizontal" action="addRace">
      <fieldset>

        <f:with bean="raceInstance">
          <f:field property="resultsUrl" input-class="span8"/>
          <f:field property="name" input-class="span8"/>
          <f:field property="date"/>
          <f:field property="raceType"/>
          <f:field property="distanceType"/>
          <f:field property="distance" input-step="any" input-min="0"/>
          <f:field property="raceCategoryType"/>
          <f:field property="city" input-class="span6"/>
          <f:field property="state" input-class="span6" default="${user?.states?.toList()?.first()}"/>
          <f:field property="country" input-class="span6" default="${Country.findByCountryID("US")}"/>
        </f:with>

        <div class="form-actions">
          <button type="submit" class="btn btn-primary">
            <i class="icon-ok icon-white"></i>
            <g:message code="default.button.create.label" default="Create"/>
          </button>
        </div>
      </fieldset>
    </g:form>
  </fieldset>
</div>
<script>
  var bike = ${[com.tgid.tri.race.RaceCategoryType.Road,
    com.tgid.tri.race.RaceCategoryType.Criterium,
    com.tgid.tri.race.RaceCategoryType.TimeTrial,
    com.tgid.tri.race.RaceCategoryType.Training,
    com.tgid.tri.race.RaceCategoryType.Unknown] as JSON};

  var triathlon = ${[com.tgid.tri.race.RaceCategoryType.Olympic,
    com.tgid.tri.race.RaceCategoryType.Sprint,
    com.tgid.tri.race.RaceCategoryType.HalfIronman,
    com.tgid.tri.race.RaceCategoryType.Ironman,
    com.tgid.tri.race.RaceCategoryType.Training,
    com.tgid.tri.race.RaceCategoryType.Unknown] as JSON};

  var run = ${[com.tgid.tri.race.RaceCategoryType.FiveKilometer,
    com.tgid.tri.race.RaceCategoryType.TenKilometer,
    com.tgid.tri.race.RaceCategoryType.TenMile,
    com.tgid.tri.race.RaceCategoryType.HalfMarathon,
    com.tgid.tri.race.RaceCategoryType.Marathon,
    com.tgid.tri.race.RaceCategoryType.OneKilometer,
    com.tgid.tri.race.RaceCategoryType.OneMile,
    com.tgid.tri.race.RaceCategoryType.ThreeMile,
    com.tgid.tri.race.RaceCategoryType.FiveMile,
    com.tgid.tri.race.RaceCategoryType.EightKilometer,
    com.tgid.tri.race.RaceCategoryType.EightMile,
    com.tgid.tri.race.RaceCategoryType.FifteenKilometer,
    com.tgid.tri.race.RaceCategoryType.TenMile,
    com.tgid.tri.race.RaceCategoryType.TwentyFiveKilometer,
    com.tgid.tri.race.RaceCategoryType.TwentyMile,
    com.tgid.tri.race.RaceCategoryType.Run,
    com.tgid.tri.race.RaceCategoryType.Training,
    com.tgid.tri.race.RaceCategoryType.Unknown] as JSON};

  var other = ${[com.tgid.tri.race.RaceCategoryType.Training,
    com.tgid.tri.race.RaceCategoryType.Unknown] as JSON};

  $(function () {
    $('#raceType').change(updateRaceCategoryType);
    updateRaceCategoryType();
  });

  function updateRaceCategoryType() {
    var sub, options = {};
    $('#raceCategoryType').empty();
    sub = function(){
      if($('#raceType').val() === '${com.tgid.tri.race.RaceType.Running}') {
        return run;
      } else if($('#raceType').val() === '${com.tgid.tri.race.RaceType.Biking}') {
        return bike;
      } else if($('#raceType').val() === '${com.tgid.tri.race.RaceType.Triathlon}') {
        return triathlon;
      } else {
        return other;
      }
    }();

    $(sub).each(function (i, o) {
      $('#raceCategoryType').append(
              $('<option>').text(o.name).val(o.name)
      );
    });
  }

</script>
</body>
</html>