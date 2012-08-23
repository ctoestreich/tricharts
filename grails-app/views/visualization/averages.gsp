<%@ page import="com.tgid.tri.race.RaceCategoryType; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>User Dashboard</title>
  <r:require modules="dashboard,results, progression"/>
  <gvisualization:apiImport/>
</head>

<body>

<div class="page-header">
  <h1>Progression For ${user.firstName} <small>make me faster</small></h1>
</div>

<g:render template="/templates/admin/userSelect" />

<g:render template="/templates/visualization/chartSelection" />

<div class="row-fluid" id="averages"><g:img dir="/images" file="spinner.gif" /></div>

<script>
  $(function () {
    jQuery.ajax({
                  type:'POST',
                  <g:if test="${params?.raceType == 'Run'}">
                  url:'${createLink(controller: 'visualization', action:'runningAverages', params:['user.id',params?.user?.id])}',
                  </g:if>
                  <g:elseif test="${params?.raceType == 'Triathlon'}">
                  url:'${createLink(controller: 'visualization', action:'triathlonAverages', params:['user.id',params?.user?.id])}',
                  </g:elseif>
                  data:{ 'user.id': '${params?.user?.id}', div:'averagesDiv'},
                  success:function (data, textStatus) {
                    console.log('success');
                    $('#averages').html(data);
                  },
                  error:function (XMLHttpRequest, textStatus, errorThrown) {
                  }});
  });
</script>
</body>
</html>