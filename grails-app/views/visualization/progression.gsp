<%@ page import="com.tgid.tri.race.RaceCategoryType; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>Trrending</title>
  <r:require modules="dashboard,results, charting,widgets"/>
  <gvisualization:apiImport/>
</head>

<body>

%{--<div class="page-header">--}%
  %{--<h1>Trending For ${user.firstName} <small>getting faster</small></h1>--}%
%{--</div>--}%

%{--<g:render template="/templates/admin/userSelect" />--}%

<g:render template="/templates/visualization/chartSelection" />

<div class="row-fluid">
  <div class="well">
    <div class="btn-group-wrap">
      <div class="btn-group">
        <g:each in="${races}" var="race">
          <a href="#${race.raceCategoryType.replace(" ", "_")}" id="${race.raceCategoryType.replace(" ", "_")}Btn" class="btn">${race.raceCategoryType}</a>
        </g:each>
      </div>
    </div>
  </div>
</div>

<g:each in="${races}" var="race">
  <div class="row-fluid" id="${race.raceCategoryType.replace(" ", "_")}"><g:img dir="/images" file="spinner.gif" /></div>
  <br>
</g:each>
<script>
  $(function () {
    <g:each in="${races}" var="race">
    jQuery.ajax({
                  type:'POST',
                  <g:if test="${params?.raceType == 'Running'}">
                  url:'${createLink(controller: 'visualization', action:'runningProgression', params:['user.id',params?.user?.id])}',
                  </g:if>
                  <g:elseif test="${params?.raceType == 'Triathlon'}">
                  url:'${createLink(controller: 'visualization', action:'triathlonProgression', params:['user.id',params?.user?.id])}',
                  </g:elseif>
                  data:{ 'user.id': '${params?.user?.id}', raceCategoryType:'${race.raceCategoryType}', div:'${race.raceCategoryType.replace(" ", "_")}Div'},
                  success:function (data, textStatus) {
                    console.log('success');
                    $('#${race.raceCategoryType.replace(" ", "_")}').html(data);
                  },
                  error:function (XMLHttpRequest, textStatus, errorThrown) {
                  }});
    </g:each>
  });
</script>
</body>
</html>