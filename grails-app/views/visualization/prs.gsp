<%@ page import="com.tgid.tri.race.SegmentType; com.tgid.tri.race.RaceCategoryType; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>Records</title>
  <r:require modules="dashboard,results, charting,widgets"/>
  <gvisualization:apiImport/>
</head>

<body>

%{--<div class="page-header">--}%
  %{--<h1>Records For ${user.firstName} <small> my best efforts</small></h1>--}%
%{--</div>--}%

%{--<g:render template="/templates/admin/userSelect"/>--}%

<g:render template="/templates/visualization/chartSelection"/>

<g:if test="${params?.raceType == 'Running'}">
  <div class="row-fluid chart loading-small" id="averages"><h5>Loading Run PRs</h5></div>
</g:if>
<g:elseif test="${params?.raceType == 'Triathlon'}">
  <div class="row-fluid chart loading-small" id="averages"><h5>Loading Triathlon PRs</h5></div>
</g:elseif>

<script>
  $(function () {
    <g:if test="${params?.raceType == 'Running'}">
    jQuery.ajax({
                  type:'POST',
                  url:'${createLink(controller: 'visualization', action:'runningPrs', params:['user.id',params?.user?.id])}',
                  data:{ 'user.id':'${params?.user?.id}', div:'averagesDiv'},
                  complete:function () {
                    app.removeLoadingClasses($('#averages'));
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
                  url:'${createLink(controller: 'visualization', action:'triathlonPrs', params:['user.id',params?.user?.id])}',
                  data:{ 'user.id':'${params?.user?.id}', div:'averagesDiv'},
                  complete:function () {
                    app.removeLoadingClasses($('#averages'));
                  },
                  success:function (data, textStatus) {
                    console.log('success');
                    $('#averages').html(data);
                  },
                  error:function (XMLHttpRequest, textStatus, errorThrown) {
                  }});
    </g:elseif>
  });
</script>
</body>
</html>