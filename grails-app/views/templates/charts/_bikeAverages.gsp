<p><h3>${title}</h3></p>
<div id="${id}"></div>
<br>

<script>
  var hasData = false;
  <g:if test="${data}">
  hasData = true;
  var chart;
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
                                         enabled:true
                                       }
                                     }
                                   },
                                   chart:{
                                     renderTo:'${id}'
                                   },
                                   title:{
                                     text: '${title ?: "Bike Average Pace By Year"}'
                                   },
                                   xAxis:{
                                     categories: ${categories}
                                   },
                                   yAxis:[{
                                     title: 'Average Pace'
                                   }],
                                   tooltip:{
                                     formatter:function () {
                                       var s;
                                       if(this.point.name) { // the pie chart
                                         s = '' + this.point.name + ': ' + this.y + ' races';
                                       } else {
                                         s = '<b>' + this.x + '</b><br/>' + this.y;
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
                                       center:[30, 30],
                                       size:50,
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