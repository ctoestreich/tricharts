<%@ page import="com.tgid.tri.race.SegmentType" %>
<h4>Race Times By State</h4>
<div id="averagesDiv"></div>
<BR>

<script>
  var hasData = false;
  <g:if test="${males.size() > 0 || females.size() > 0 || you.size() > 0}">
  hasData = true;
  var chart;
  var timeToSubtract = Date.parse("1/1/1 0:00:00");
  $(function () {
    chart = new Highcharts.Chart({
                                   chart: {
                                     renderTo: '${div}',
                                     type: 'scatter',
                                     zoomType: 'xy'
                                   },
                                   title: {
                                     text: 'Mile Averages'
                                   },
                                   subtitle: {
                                     text: '${raceCategoryType} in ${state.name}'
                                   },
                                   xAxis: {
                                     title: {
                                       enabled: true,
                                       text: 'Age'
                                     },
                                     startOnTick: true,
                                     endOnTick: true,
                                     showLastLabel: true
                                   },
                                   yAxis: {
                                     type:'datetime',
                                     title: {
                                       text: 'Mile Times'
                                     }
                                   },
                                   tooltip: {
                                     formatter: function() {
                                       return '' + Highcharts.dateFormat('%H:%M:%S', this.y);
                                     }
                                   },
                                   legend: {
                                     layout: 'vertical',
                                     align: 'left',
                                     verticalAlign: 'top',
                                     x: 100,
                                     y: 70,
                                     floating: true,
                                     backgroundColor: '#FFFFFF',
                                     borderWidth: 1
                                   },
                                   plotOptions: {
                                     scatter: {
                                       marker: {
                                         radius: 5,
                                         states: {
                                           hover: {
                                             enabled: true,
                                             lineColor: 'rgb(100,100,100)'
                                           }
                                         }
                                       },
                                       states: {
                                         hover: {
                                           marker: {
                                             enabled: false
                                           }
                                         }
                                       }
                                     }
                                   },
                                   series: [{
                                     name: 'Female',
                                     color: 'rgba(223, 83, 83, .5)',
                                     data: ${showFemale ? females : []}

                                   }, {
                                     name: 'Male',
                                     color: 'rgba(119, 152, 191, .5)',
                                     data: ${showMale ? males : []}
                                   }, {
                                     name: 'You',
                                     color: 'rgba(102, 255, 0, .5)',
                                     data: ${showYou ? you : []}
                                   }]
                                 });
  });
  </g:if>

  if(!hasData){
    $("#${div}").parent('div').addClass("nodata");
  }

  %{--if(!hasData) {--}%
    %{--$("#${div}").html('No data for ${raceCategoryType} races.')--}%
  %{--}--}%
</script>