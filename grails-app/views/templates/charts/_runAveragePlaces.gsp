<h3>${title}</h3>
<div id="${id}"></div>
<br>

<script>
  var hasData${id} = false;
  <g:if test="${data}">
  hasData${id} = true;
  var chart${id};
  var timeToSubtract = Date.parse("1-1-1 0:00:00");
  $(function () {
    var colors = Highcharts.getOptions().colors,
            categories = ${categories},
            name = 'Mile Pace Averages',
            data = [
              <g:each in="${data}" var="barData" status="i">
              <g:if test="${barData.hasData}">
              {
                y: ${barData.y},
                color:colors[${i}],
                drilldown:{
                  name:'${barData?.drilldown?.name}',
                  categories: ${barData?.drilldown?.categories ?: []},
                  data: ${barData?.drilldown?.data ?: []},
                  color:colors[${i}]
                }
              }
              </g:if>
                <g:else>{}</g:else>
              <g:if test="${data.size() > i+1}">,
              </g:if>
              </g:each>
            ];

    chart${id} = new Highcharts.Chart({
                                        plotOptions:{
                                          series:{
                                            animation:{
                                              duration:500
                                            }
                                          },
                                          column:{
                                            stacking:null,
                                            dataLabels:{
                                              enabled:true,
                                              color:colors[0],
                                              style:{
                                                fontWeight:'bold'
                                              },
                                              formatter:function () {
                                                return '' + app.getGetOrdinal(this.y);
                                              }
                                            },
                                            point:{
                                              events:{
                                                click:function () {
                                                  var drilldown = this.drilldown;
                                                  if(drilldown) { // drill down
                                                    app.setChart(chart${id}, drilldown.name, drilldown.categories, drilldown.data, drilldown.color);
                                                  } else { // restore
                                                    app.setChart(chart${id}, name, categories, data);
                                                  }
                                                }
                                              }
                                            }
                                          }
                                        },
                                        chart:{
                                          renderTo:'${id}',
                                          type:'column'
                                        },
                                        title:{
                                          text:'${title ?: "Average Placement"}'
                                        },
                                        subtitle:{
                                          text:'Click the columns to view yearly averages. Click again to view all-time average.'
                                        },
                                        xAxis:{
                                          categories: ${categories}
                                        },
                                        yAxis:[
                                          {
                                            title:'Average Placement'
                                          }
                                        ],
                                        tooltip:{
                                          formatter:function () {
                                            var point = this.point;
                                            var s;
                                            if(point.drilldown) {
                                              s = '<b>' + this.x + '</b><br/>' + app.getGetOrdinal(this.y) +'<br/>Click to view ' + point.category + ' by year';
                                            } else if(this.point.name) { // the pie chart
                                              s = '' + this.point.name + ': ' + this.y + ' races';
                                            } else {
                                              s = '<b>' + this.x + '</b><br/>' + app.getGetOrdinal(this.y);
                                            }
                                            return s;
                                          }
                                        },
                                        labels:{
                                          items:[
                                            {
                                              html:'Total Races',
                                              style:{
                                                left:'60px',
                                                top:'8px',
                                                color:'black'
                                              }
                                            }
                                          ]
                                        },
                                        series:[
                                          {
                                            type:'pie',
                                            name:'Total Races',
                                            data: ${totalRaces},
                                            center:[30, 30],
                                            size:50,
                                            showInLegend:false,
                                            dataLabels:{
                                              enabled:false
                                            }
                                          },
                                          {
                                            name:name,
                                            data:data,
                                            color:'white'
                                          }
                                        ]
                                      });
  });
  </g:if>

  if(!hasData${id}) {
    $("#${id}").html('No data to average.')
  }
</script>