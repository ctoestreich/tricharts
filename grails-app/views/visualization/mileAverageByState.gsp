<%@ page import="com.tgid.tri.race.SegmentType; com.tgid.tri.race.RaceCategoryType; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>Averages</title>
  <r:require modules="dashboard,results, progression, widgets"/>
  <gvisualization:apiImport/>
</head>

<body>

<div class="page-header">
  <h1>Running Averages By State <small>dots...</small></h1>
</div>

<g:render template="/templates/admin/userSelect"/>

%{--<g:render template="/templates/visualization/dateSelection"/>--}%

<g:render template="/templates/visualization/chartSelection"/>

<div class="row-fluid" id="averages"><g:img dir="/images" file="spinner.gif"/> Loading Chart times</div>

<script>
  $(function () {
    jQuery.ajax({
                  type:'POST',
                  url:'${createLink(controller: 'visualization', action:'runScatter', params:['user.id',params?.user?.id])}',
                  data:{ 'user.id':'${params?.user?.id}', div:'averagesDiv'},
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