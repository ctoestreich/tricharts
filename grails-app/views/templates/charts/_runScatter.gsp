<%@ page import="com.tgid.tri.race.SegmentType" %>
<p>

<h3>Race Times By State</h3></p>
<div id="averagesDiv"></div>
<BR>

%{--Maybe do records here?--}%
%{--<div id="${id}Records" class="span3"></div>--}%

<script>
  var hasData = false;

  <g:if test="${males.size() > 0 || females.size() > 0}">
  hasData = true;
  var chart;
  var timeToSubtract = Date.parse("1-1-1 0:00:00");
  $(function () {
    chart = new Highcharts.Chart({
                                   chart: {
                                     renderTo: '${div}',
                                     type: 'scatter',
                                     zoomType: 'xy'
                                   },
                                   title: {
                                     text: 'Race Times By Gender'
                                   },
                                   subtitle: {
                                     text: '${raceCategoryType} in state ${state.name}'
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
                                     data: ${females}

                                   }, {
                                     name: 'Male',
                                     color: 'rgba(119, 152, 191, .5)',
                                     data: ${males}
                                   }]
                                 });
    %{--chart${id} = new Highcharts.Chart({--}%
                                        %{--plotOptions:{--}%
                                          %{--scatter:{--}%
                                            %{--marker:{--}%
                                              %{--radius:5,--}%
                                              %{--states:{--}%
                                                %{--hover:{--}%
                                                  %{--enabled:true,--}%
                                                  %{--lineColor:'rgb(100,100,100)'--}%
                                                %{--}--}%
                                              %{--}--}%
                                            %{--},--}%
                                            %{--states:{--}%
                                              %{--hover:{--}%
                                                %{--marker:{--}%
                                                  %{--enabled:false--}%
                                                %{--}--}%
                                              %{--}--}%
                                            %{--}--}%
                                          %{--}--}%
                                        %{--},--}%
                                        %{--chart:{--}%
                                          %{--renderTo:"${id}",--}%
                                          %{--type:'scatter',--}%
                                          %{--zoomType:'xy'--}%
                                        %{--},--}%
                                        %{--title:{--}%
                                          %{--text:'Age vs. Mile Time'--}%
                                        %{--},--}%
                                        %{--subtitle:{--}%
                                          %{--text:'States: MN'--}%
                                        %{--},--}%
                                        %{--xAxis:{--}%
                                          %{--type:'datetime',--}%
                                          %{--title:{--}%
                                            %{--enabled:true,--}%
                                            %{--text:'Mile Time'--}%
                                          %{--},--}%
                                          %{--startOnTick:true,--}%
                                          %{--endOnTick:true,--}%
                                          %{--showLastLabel:true,--}%
                                          %{--dateTimeLabelFormats:{ // don't display the dummy year--}%
                                            %{--month:'%e. %b',--}%
                                            %{--year:'%b'--}%
                                          %{--}--}%
                                        %{--},--}%

                                        %{--yAxis:{--}%
                                          %{--title:{--}%
                                            %{--text:'Age'--}%
                                          %{--}--}%
                                        %{--},--}%
                                        %{--tooltip:{--}%
                                          %{--formatter:function () {--}%
                                            %{--return '<b>' + this.point.name + '</b><br/>' + Highcharts.dateFormat('%H:%M:%S', this.y);--}%
                                          %{--}--}%
                                        %{--},--}%
                                        %{--legend:{--}%
                                          %{--layout:'vertical',--}%
                                          %{--align:'left',--}%
                                          %{--verticalAlign:'top',--}%
                                          %{--x:100,--}%
                                          %{--y:70,--}%
                                          %{--floating:true,--}%
                                          %{--backgroundColor:'#FFFFFF',--}%
                                          %{--borderWidth:1--}%
                                        %{--},--}%
                                        %{--series:[--}%
                                          %{--{--}%
                                            %{--name:'Female',--}%
                                            %{--color:'rgba(223, 83, 83, .5)',--}%
                                            %{--data: []--}%

                                          %{--},--}%
                                          %{--{--}%
                                            %{--name:'Male',--}%
                                            %{--color:'rgba(119, 152, 191, .5)',--}%
                                            %{--data:[]--}%
                                          %{--}--}%
                                        %{--]--}%
                                      %{--});--}%
  });
  </g:if>

  if(!hasData) {
    $("#${div}").html('No data for ${raceCategoryType} races.')
  }
</script>