<%@ page import="com.tgid.tri.race.SegmentType" %>
<h3 class="section" id="ttl${id}">${type} Results</h3>

<div id="${id}_Swim"></div>
<BR>

<div id="${id}_Bike"></div>
<BR>

<div id="${id}_Run"></div>
<BR>
%{--Maybe do records here?--}%
%{--<div id="${id}Records" class="span3"></div>--}%

<script>
  app.removeLoadingClasses($("#${id}_Bike").parent('div'));

  var hasData = false;
  <g:each in="${[SegmentType.Swim , SegmentType.Bike, SegmentType.Run]}" var="segmentType">
  <g:if test="${data.get(segmentType)}">
  $('#${id}_${segmentType}').parent('div').removeClass('chart');
  $('#${id}_${segmentType}').addClass('chart');
  $("#${id}_${segmentType}").height(400);
  %{--$('#ttl${id}').hide();--}%
  hasData = true;
  var chart${segmentType};
  var timeToSubtract = Date.parse("1-1-1 0:00:00");
  $(function () {
    chart${segmentType} = new Highcharts.Chart({
                                                 plotOptions:{
                                                   column:{
                                                     stacking:'normal',
                                                     dataLabels:{
                                                       enabled:true,
                                                       formatter:function () {
                                                         return '' + Highcharts.dateFormat('%H:%M:%S', this.y);
                                                       }
                                                     }
                                                   }
                                                 },
                                                 chart:{
                                                   renderTo:'${id}_${segmentType}',
                                                   type:'spline'
                                                 },
                                                 title:{
                                                   text:'${segmentType} Pace ${type}'
                                                 },
                                                 xAxis:{
                                                   type:'datetime',
                                                   title:{text:'Race Date'},
                                                   dateTimeLabelFormats:{ // don't display the dummy year
                                                     month:'%e. %b',
                                                     year:'%b'
                                                   }
                                                 },
                                                 yAxis:{
                                                   <g:if test="${segmentType != SegmentType.Bike}">
                                                   type:'datetime',
                                                   </g:if>
                                                   title:{text:'${segmentType} Pace'}
                                                 },
                                                 tooltip:{
                                                   formatter:function () {
                                                     <g:if test="${segmentType == SegmentType.Bike}">
                                                     return '<b>' + this.point.name + '</b><br/>' + this.y;
                                                     </g:if>
                                                     <g:else>
                                                     return '<b>' + this.point.name + '</b><br/>' + Highcharts.dateFormat('%H:%M:%S', this.y);
                                                     </g:else>
                                                   }
                                                 },
                                                 series:[
                                                   <g:each in="${data.get(segmentType)}" var="series" status="i">
                                                   <g:if test="${i>0}">,
                                                     </g:if>{
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
  </g:each>

  if(!hasData){
    $("#${id}_Bike").parent('div').height(300).addClass("chart").addClass("nodata");
  }
</script>