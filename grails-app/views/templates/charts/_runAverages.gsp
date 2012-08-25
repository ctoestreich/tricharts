<p>

<h3>${type} Results</h3></p>
<div id="${id}"></div>
<br>

<script>
  var hasData = false;
  <g:if test="${data}">
  hasData = true;
  var chart;
  var timeToSubtract = Date.parse("1-1-1 0:00:00");
  $(function () {
    chart = new Highcharts.Chart({
                                   plotOptions:{
                                     series:{
                                       animation:{
                                         duration:1500
                                       }
                                     },
                                     column:{
                                       stacking:null,
                                       dataLabels:{
                                         enabled:true,
                                         formatter:function () {
                                           return '' + Highcharts.dateFormat('%M:%S', this.y);
                                         }
                                       }
                                     }
                                   },
                                   chart:{
                                     renderTo:'${id}'
                                   },
                                   title:{
                                     text:'Run Pace Averages'
                                   },
                                   xAxis:{
                                     categories: ${categories},
                                     dateTimeLabelFormats: { // don't display the dummy year
                                       month: '%e. %b',
                                       year: '%b'
                                     }
                                   },
                                   yAxis:[{
                                     title: 'Average Mile',
                                     type:'datetime'
                                   }],
                                   tooltip:{
                                     formatter:function () {
                                       var s;
                                       if(this.point.name) { // the pie chart
                                         s = '' + this.point.name + ': ' + this.y + ' races';
                                       } else {
                                         s = '<b>' + this.x + '</b><br/>' + Highcharts.dateFormat('%H:%M:%S', this.y);
                                       }
                                       return s;
                                     }
                                   },
                                   labels:{
                                     items:[
                                       {
                                         html:'Total Races',
                                         style:{
                                           left:'80px',
                                           top:'8px',
                                           color:'black'
                                         }
                                       }
                                     ]
                                   },
                                   series:[
                                     <g:each in="${data.sort{a,b-> a.key <=> b.key}}" var="result">
                                     {
                                       type:'column',
                                       name:'${result.key}',
                                       data: ${result.value}
                                     },
                                     </g:each>
                                     {
                                       type:'pie',
                                       name:'Total Races',
                                       data: ${totalRaces},
                                       center:[50, 50],
                                       size:80,
                                       showInLegend:false,
                                       dataLabels:{
                                         enabled:false
                                       }
                                     }
                                   ]
                                 });
  });
  </g:if>

  if(!hasData) {
    $("#${id}").html('No data to average.')
  }
</script>