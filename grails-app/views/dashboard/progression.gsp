<%@ page import="com.tgid.tri.race.RaceCategoryType; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>User Dashboard</title>
  <r:require modules="results, progression"/>
  <gvisualization:apiImport/>
</head>

<body>

<div class="page-header">
  <h1>Progression For ${user.firstName} <small>make me faster</small></h1>
</div>

<sec:ifAnyGranted roles="ROLE_ADMIN">
  <div class="row">
    <button class="close" data-dismiss="alert">×</button>
    <g:select name="user.id" from="${User.list().sort()}" id="user.id" value="${user?.id}"/>
  </div>
</sec:ifAnyGranted>

<g:set var="races" value="${[RaceCategoryType.OneMile, RaceCategoryType.FiveKilometer, RaceCategoryType.EightKilometer, RaceCategoryType.TenKilometer, RaceCategoryType.TenMile, RaceCategoryType.HalfMarathon, RaceCategoryType.Marathon]}"/>

<div class="row-fluid">
  <div class="well">
    <div class="btn-group-wrap">
      <div class="btn-group">
        <g:each in="${races}" var="race">
          <a href="#" id="${race.raceCategoryType.replace(" ", "_")}" class="btn">${race.raceCategoryType}</a>
        </g:each>
      </div>
    </div>
  </div>
</div>
<div class="row-fluid" id="placeHolder"></div>
<div id="chartDiv" class="span4"></div>
<script>
  $(function () {
    <g:each in="${races}" var="race">
    $("#${race.raceCategoryType.replace(" ","_")}").on('click', function () {
      jQuery.ajax({
                    type:'POST',
                    url:'${createLink(controller: 'visualization', action:'runningProgression')}',
                    data:{ raceCategoryType:'${race.raceCategoryType}', div:'${race.raceCategoryType.replace(" ","_")}Div'},
                    success:function (data, textStatus) {
                      cloneGridResults(data, '${race.raceCategoryType.replace(" ","_")}Results');
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                    }});
    });
    </g:each>
  });

  function cloneGridResults(data, id) {
    var newDiv;
    if($('#' + id).length == 0) {
      newDiv = $('#chartDiv').clone().attr('id', id);
      $(newDiv).appendTo('#placeHolder');
      $(newDiv).html(data);
    } else {
      $('#' + id).toggle();
    }
  }
</script>
</body>
</html>