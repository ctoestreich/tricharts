<%@ page import="com.tgid.tri.race.SegmentType; com.tgid.tri.race.RaceCategoryType; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>Averages</title>
  <r:require modules="dashboard,results, charting, widgets"/>
  <gvisualization:apiImport/>
</head>

<body>

%{--<div class="page-header">--}%
%{--<h1>Averages For ${user.firstName} <small>above average</small></h1>--}%
%{--</div>--}%

%{--<g:render template="/templates/admin/userSelect"/>--}%

%{--<g:render template="/templates/visualization/dateSelection"/>--}%

<g:render template="/templates/visualization/chartSelection"/>

<g:if test="${params?.raceType == 'Running'}">
  <div class="row-fluid chart loading-large" style="height:400px;" id="averages"><h4>Loading Chart Run Averages</h4></div>
</g:if>
<g:elseif test="${params?.raceType == 'Triathlon'}">
  <div class="row-fluid chart loading-large" style="height:400px;" id="averagesOverall"><h4>Loading Chart Overall Placement</h4></div>
  <br />
  <g:each in="${[SegmentType.Swim, SegmentType.Bike, SegmentType.Run]}" var="segmentType">
    <div class="row-fluid chart loading-large" style="height:400px;" id="averages${segmentType}"><h4>Loading Chart ${segmentType} Overall Placement</h4></div>
    <br />
  </g:each>
</g:elseif>

<script>
  $(function () {
    <g:if test="${params?.raceType == 'Running'}">
    jQuery.ajax({
                  type:'POST',
                  url:'${createLink(controller: 'visualization', action:'runningAveragePlaces', params:['user.id',params?.user?.id])}',
                  data:{ 'user.id':'${params?.user?.id}', div:'averagesDiv', type:'oa'},
                  complete:function () {
                    $('#averages').removeClass('loading-large');
                  },
                  success:function (data, textStatus) {
                    console.log('success');
                    $('#averages').html(data);
                  },
                  error:function (XMLHttpRequest, textStatus, errorThrown) {
                  }});
    </g:if>
    <g:elseif test="${params?.raceType == 'Triathlon'}">
    jQuery.ajax({
                  type:'POST',
                  url:'${createLink(controller: 'visualization', action:'triathlonAveragePlaces', params:['user.id',params?.user?.id])}',
                  data:{ 'user.id':'${params?.user?.id}', div:'averagesOverallDiv', segmentType:'Overall', type: 'oa'},
                  complete:function () {
                    $('#averagesOverall').removeClass('loading-large');
                  },
                  success:function (data, textStatus) {
                    console.log('success');
                    $('#averagesOverall').html(data);
                  },
                  error:function (XMLHttpRequest, textStatus, errorThrown) {
                  }});
    <g:each in="${[SegmentType.Swim, SegmentType.Bike, SegmentType.Run]}" var="segmentType">
    jQuery.ajax({
                  type:'POST',
                  url:'${createLink(controller: 'visualization', action:'triathlonAveragePlaces', params:['user.id',params?.user?.id])}',
                  data:{ 'user.id':'${params?.user?.id}', div:'averages${segmentType}Div', segmentType:'${segmentType}', type:'oa'},
                  complete:function () {
                    $('#averages${segmentType}').removeClass('loading-large');
                  },
                  success:function (data, textStatus) {
                    console.log('success');
                    $('#averages${segmentType}').html(data);
                  },
                  error:function (XMLHttpRequest, textStatus, errorThrown) {
                  }});
    </g:each>
    </g:elseif>
  });
</script>
</body>
</html>