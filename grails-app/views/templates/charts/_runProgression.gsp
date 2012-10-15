<h4 id="ttl${id}">${type} Results</h4>
<div id="${id}"></div>
<br>
%{--Maybe do records here?--}%
%{--<div id="${id}Records" class="span3"></div>--}%

<script>
  var timeToSubtract = Date.parse("1/1/1 0:00:00");
//  console.log(timeToSubtract);
//  console.log(Date.parse('1/1/1 0:05:31'));
//  console.log(Date.parse('1/1/1 0:05:31') - timeToSubtract);
  app.removeLoadingClasses($("#${id}").parent('div'));
  var hasData = false;
  <g:if test="${data}">
  $('#${id}').parent('div').removeClass('chart');
  $('#${id}').addClass('chart');
  $('#ttl${id}').hide();
  hasData = true;
  var chart;
//  var timeToSubtract = Date.parse("1/1/1 0:00:00");
    $(function(){
  chart = new Highcharts.Chart({
                                 plotOptions:{
                                   series: {
                                     animation: {
                                       duration: 1500
                                     }
                                   },
                                   column:{
                                     stacking:'normal',
                                     dataLabels:{
                                       enabled:true,
                                       formatter:function () {
                                         return '' + Highcharts.dateFormat('%M:%S', this.y);
                                       }
                                     }
                                   }
                                 },
                                 chart:{
                                   renderTo:'${id}',
                                   type:'spline'
                                 },
                                 title:{
                                   text:'${type} Results'
                                 },
                                 xAxis:{
                                   type:'datetime',
                                   title:{text:'Race Date'},
                                   dateTimeLabelFormats: { // don't display the dummy year
                                     month: '%e. %b',
                                     year: '%b'
                                   }
                                 },
                                 yAxis:{
                                   type:'datetime',
                                   title:{text:'Race Time'}

                                 },
                                 tooltip:{
                                   formatter:function () {
                                     return '<b>' + this.point.name + '</b><br/>' + Highcharts.dateFormat('%H:%M:%S', this.y);
                                   }
                                 },
                                 series:[
                                   <g:each in="${data}" var="series" status="i">
                                   <g:if test="${i>0}">,</g:if>{
                                     name:'${type} Race Year ${series.key}',
                                     // Define the data points. All series have a dummy year
                                     // of 1970/71 in order to be compared on the same x axis. Note
                                     // that in JavaScript, months start at 0 for January, 1 for February etc.
                                     data:[${series.value}]
                                   }
                                   </g:each>
                                 ]
                               });
    });
  </g:if>

  if(!hasData){
    $("#${id}").parent('div').height(300).addClass("chart").addClass("nodata");
  }

</script>