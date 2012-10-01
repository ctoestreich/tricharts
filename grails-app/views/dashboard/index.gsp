<%@ page import="com.tgid.tri.auth.User" %>
<g:set var="paramMap" value="${[:]}"/>
<sec:ifAllGranted roles="ROLE_ADMIN">
  <g:set var="paramMap" value="${['user.id': params?.user?.id]}"/>
</sec:ifAllGranted>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>User Dashboard</title>
  <r:require modules="dashboard, results, widgets, charting"/>
  <gvisualization:apiImport/>
</head>

<body>

<div class="page-header">
  <h1>Results For ${user.firstName} <small>you are fast</small></h1>
</div>

<g:if test="${flash.message}">
  <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
</g:if>

<g:render template="/templates/admin/userSelect"/>

<script type="text/javascript">
  app.loadRunRecords = function () {
    ${remoteFunction(controller: 'visualization', action: 'runningRecords', update: 'runDashboardRecords', params: ['user.id': params?.user?.id])}
    ${remoteFunction(controller: 'visualization', action: 'triathlonRecords', update: 'triathlonDashboardRecords', params: ['user.id': params?.user?.id])}
  };

  app.clearRecordsCache = function(){
    ${remoteFunction(controller: 'visualization', action: 'clearRecordsCache', params: ['user.id': params?.user?.id])}
    app.loadRunRecords();
  };

  $(function () {
    app.loadRunRecords();
  });

</script>

<div class="row-fluid well-white">
  <div class="span12" style="text-align: center">
    <g:link class="btn" controller="dashboard" action="index" params="${paramMap}">Refresh </g:link>&nbsp;
    <g:link class="btn" controller="dashboard" action="index" params="${paramMap}">View My Dashboard</g:link>&nbsp;
    <g:link class="btn" controller="results" action="index" params="${paramMap}">View All My Results</g:link>&nbsp;
    <g:link class="btn" controller="visualization" action="index" params="${paramMap}">View My Charts</g:link>&nbsp;
    <g:link class="btn" controller="account" action="index" params="${paramMap}">View My Account</g:link>&nbsp;
  </div>
</div>

<div class="row-fluid">
  <div class="span4"><div class="chart smallchart loading-large" id="raceTypes" name="raceTypes" style="height: 300px"><h4>Race Types</h4></div></div>

  <div class="span4"><div class="chart smallchart loading-large" id="raceTypes2012" name="raceTypes2012" style="height: 300px"><h4>Race Types ${new java.util.Date().year + 1900}</h4></div></div>

  <div class="span4"><div class="chart smallchart loading-large" id="raceTypeYearly" name="raceTypeYearly" style="height: 300px"><h4>Races Per Year</h4></div></div>
</div>

<div class="row-fluid">
  <h2>Triathlon Records</h2>
  <div class="row-fluid" id="triathlonDashboardRecords"><g:img dir="/images" file="spinner.gif"/> loading triathlon records...</div>
</div>

<div class="row-fluid">
  <h2>Running Records</h2>
  <div class="row-fluid" id="runDashboardRecords"><g:img dir="/images" file="spinner.gif"/> loading run records...</div>
</div>

<div class="row-fluid">
  <h2>Last 5 Results</h2>

  <div class="span12">
    <g:if test="${latestResults?.size() > 0}">
    <g:each in="${latestResults}" var="result">
      <g:if test="${result?.race?.raceType == com.tgid.tri.race.RaceType.Triathlon}">
        <g:render template="/templates/results/triathlonResults" model="[result: result]"/>
      </g:if>
      <g:elseif test="${result?.race?.raceType == com.tgid.tri.race.RaceType.Running}">
        <g:render template="/templates/results/runResults" model="[result: result]"/>
      </g:elseif>
    </g:each>
    </g:if>
    <g:else>
      <div class="well-white">No Results</div>
    </g:else>
  </div>
  <div class="span12" style="text-align: right; padding-top:10px;"><g:link class="btn btn-primary" controller="results" action="index">See All Results</g:link></div>
</div>
<g:form name="modifyRaceResultsForm" id="modifyRaceResultsForm" controller="results" action="modifyRaceResults">
  <g:hiddenField name="raceResultId" value=""/>
  <g:hiddenField name="user.id" value="${user?.id}"/>
  <g:hiddenField name="raceResultEdit" value="false"/>
</g:form>
<g:form name="raceResultDeleteForm" id="raceResultDeleteForm" controller="results" action="deleteRaceResult">
  <g:hiddenField name="raceResultDeleteId" value=""/>
  <g:hiddenField name="user.id" value="${user?.id}"/>
</g:form>

<r:script>
  $(function () {
    var racesCompletedChart;
    var racesCompleted2012Chart;
    var racesCompletedYearlyChart;

    $(document).ready(function () {
      var options = {
        chart:{
          renderTo:'raceTypes',
          plotBackgroundColor:null,
          plotBorderWidth:null,
          plotShadow:true
        },
        credits:{
          enabled:false
        },
        title:{
          text:'Race Types'
        },
        tooltip:{
          pointFormat:'{series.name} - {point.y}: <b>{point.percentage}%</b>',
          percentageDecimals:1
        },
        exporting:{
          enabled:false
        },
        plotOptions:{
          pie:{
            allowPointSelect:true,
            cursor:'pointer',
            dataLabels:{
              enabled:false
            },
            showInLegend:true
          }
        }
      };

      var options2 = {
        chart:{
          renderTo:'raceTypes2012',
          plotBackgroundColor:null,
          plotBorderWidth:null,
          plotShadow:true
        },
        credits:{
          enabled:false
        },
        title:{
          text:'Race Types ${new java.util.Date().year + 1900}'
        },
        tooltip:{
          pointFormat:'{series.name} - {point.y}: <b>{point.percentage}%</b>',
          percentageDecimals:1
        },
        exporting:{
          enabled:false
        },
        plotOptions:{
          pie:{
            allowPointSelect:true,
            cursor:'pointer',
            dataLabels:{
              enabled:false
            },
            showInLegend:true
          }
        }
      };

      var options3 = {
        chart:{
          renderTo:'raceTypeYearly',
          plotBackgroundColor:null,
          plotBorderWidth:null,
          plotShadow:true
        },
        credits:{
          enabled:false
        },
        title:{
          text:'Races Per Year'
        },
        tooltip:{
          pointFormat:'{series.name} - {point.y}: <b>{point.percentage}%</b>',
          percentageDecimals:1
        },
        exporting:{
          enabled:false
        },
        plotOptions:{
          pie:{
            allowPointSelect:true,
            cursor:'pointer',
            dataLabels:{
              enabled:false
            },
            showInLegend:true
          }
        }
      };

      jQuery.ajax({
                    type:'POST',
                    url:'${createLink(controller: 'dashboard', action: 'racesCompleted', params: ['user.id', params?.user?.id])}',
                    data:{ 'user.id':'${params?.user?.id}'},
                     complete:function(){
                    $('#raceTypes').removeClass('loading-large');
                    },
                    success:function (data, textStatus) {
                        if(data){
                            options.series = data;
                            racesCompletedChart = new Highcharts.Chart(options);
                        } else {
                            $('#raceTypes').addClass('nodata');
                        }
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                    }});
      jQuery.ajax({
                    type:'POST',
                    url:'${createLink(controller: 'dashboard', action: 'racesCompleted', params: ['user.id', params?.user?.id])}',
                    data:{ 'user.id':'${params?.user?.id}', year: ${new java.util.Date().year + 1900}},
                    complete:function(){
                    $('#raceTypes2012').removeClass('loading-large');
                    },
                    success:function (data, textStatus) {
                        if(data){
                            options2.series = data;
                            racesCompleted2012Chart = new Highcharts.Chart(options2);
                            } else {
                            $('#raceTypes2012').addClass('nodata');
                        }
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                    }});
      jQuery.ajax({
                    type:'POST',
                    url:'${createLink(controller: 'dashboard', action: 'racesCompleted', params: ['user.id', params?.user?.id])}',
                    data:{ 'user.id':'${params?.user?.id}', yearly: 'true'},
                     complete:function(){
                        $('#raceTypeYearly').removeClass('loading-large');
                    },
                    success:function (data, textStatus) {
                    if(data){
                        options3.series = data;
                        racesCompletedYearlyChart = new Highcharts.Chart(options3);
                        } else {
                            $('#raceTypeYearly').addClass('nodata');
                        }
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                    }});
    });

  });
</r:script>

</body>
</html>