<%@ page import="com.tgid.tri.auth.User" %>
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

  $(function () {
    app.loadRunRecords();
  });

</script>

<div class="row-fluid">
  <div class="span4 well"><div id="raceTypes" name="raceTypes" style="height: 300px"></div></div>
  <div class="span4 well"><div id="raceTypes2012" name="raceTypes2012" style="height: 300px"></div></div>
  <div class="span4 well"><div id="raceTypeYearly" name="raceTypeYearly" style="height: 300px"></div></div>
</div>

<br />



<div class="row-fluid well_clear">
  <div class="row-fluid"><h3>Triathlon Records</h3></div>
  <div class="row-fluid" id="triathlonDashboardRecords"><g:img dir="/images" file="spinner.gif"/> loading triathlon records...</div>

</div>

<br/>

<div class="row-fluid well_clear">
  <div class="row-fluid"><h3>Running Records</h3></div>
  <div class="row-fluid" id="runDashboardRecords"><g:img dir="/images" file="spinner.gif"/> loading run records...</div>
</div>

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
          pointFormat:'{series.name}: <b>{point.percentage}%</b>',
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
          pointFormat:'{series.name}: <b>{point.percentage}%</b>',
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
          pointFormat:'{series.name}: <b>{point.percentage}%</b>',
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
                    url:'${createLink(controller: 'dashboard', action:'racesCompleted', params:['user.id',params?.user?.id])}',
                    data:{ 'user.id':'${params?.user?.id}'},
                    success:function (data, textStatus) {
                      console.log(data);
                      options.series = data;
                      racesCompletedChart = new Highcharts.Chart(options);
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                    }});

      jQuery.ajax({
                    type:'POST',
                    url:'${createLink(controller: 'dashboard', action:'racesCompleted', params:['user.id',params?.user?.id])}',
                    data:{ 'user.id':'${params?.user?.id}', year: ${new java.util.Date().year +1900}},
                    success:function (data, textStatus) {
                      options2.series = data;
                      racesCompleted2012Chart = new Highcharts.Chart(options2);
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                    }});
      jQuery.ajax({
                    type:'POST',
                    url:'${createLink(controller: 'dashboard', action:'racesCompleted', params:['user.id',params?.user?.id])}',
                    data:{ 'user.id':'${params?.user?.id}', yearly: 'true'},
                    success:function (data, textStatus) {
                      options3.series = data;
                      racesCompletedYearlyChart = new Highcharts.Chart(options3);
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                    }});
    });

  });
</r:script>

</body>
</html>