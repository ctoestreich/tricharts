<%@ page import="com.tgid.tri.auth.GenderType; com.tgid.tri.auth.State; com.tgid.tri.race.SegmentType; com.tgid.tri.race.RaceCategoryType; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>Averages</title>
  <r:require modules="dashboard,results, charting, widgets"/>
  <gvisualization:apiImport/>
  <style>
    /*.ui-autocomplete-input {width: 250px !important;}*/
    /*.ui-slider-horizontal .ui-slider-handle {height:2.1em !important;}*/
    /*.ui-slider-horizontal{height:1.7em !important;}*/
  </style>
</head>

<body>

<g:set var="num" value="${Math.floor((new Date().year - user?.dob?.year) / 5) * 5}"/>

%{--<div class="page-header">--}%
  %{--<h1>Mile Pace By State <small>dots...</small></h1>--}%
%{--</div>--}%

%{--<g:render template="/templates/admin/userSelect"/>--}%

%{--<g:render template="/templates/visualization/dateSelection"/>--}%

<g:render template="/templates/visualization/chartSelection"/>

<div class="row-fluid well">
  <div class="span6">
    <label for="slider-range">Age Range: <span id="age" style="border:0; color:#59BEDC; font-weight:bold;"></span></label>

    <div id="slider-range"></div>
    <label for="">&nbsp;</label>
    <g:checkBox value="1" name="male" checked="${user.genderType == GenderType.Male}"/> <label class="label-inline" for="male">Males</label>&nbsp;
  <g:checkBox value="1" name="female" checked="${user.genderType == GenderType.Female}"/> <label class="label-inline" for="female">Females</label>&nbsp;
  <g:checkBox value="1" name="you" checked="true"/> <label class="label-inline" for="you">You</label>&nbsp;
  <g:hiddenField name="ageMin" id="ageMin" value="${(ageMin ?: num) as Integer}"/>
  <g:hiddenField name="ageMax" id="ageMax" value="${(ageMax ?: num + 5) as Integer}"/>
  </div>

  <div class="span4">
    <label for="state">State:</label>
    <g:select name="state" class="span12" from="${State.list()}" optionKey="abbrev" optionValue="name" value="${user?.states?.toList()?.get(0)?.abbrev}"/><br/>
    <label for="state">Race Category:</label>
    <g:select name="raceCategoryType" class="span12" from="${races}" value="${raceCategoryType ?: com.tgid.tri.race.RaceCategoryType.OneMile}"/>
  </div>

  <div class="span2">
    <label form="submit">Refresh Data</label>
    <button onclick="loadScatterPlot()" type="submit" class="btn btn-action">Submit</button>
  </div>
</div>


<div class="row-fluid" id="averages" style="height: 500px;"></div>

<script>
  $(function () {
    loadScatterPlot();
//    $('#state').combobox();
  });

  function loadScatterPlot() {
    jQuery.ajax({
                  type:'POST',
                  url:'${createLink(controller: 'visualization', action:'runScatter', params:['user.id',params?.user?.id])}',
                  data:{ 'user.id':'${user?.id}', div:'averagesDiv', ageMin:$("#ageMin").val(), ageMax:$("#ageMax").val(),
                    state:$("#state").val(), t: '${raceType}',r:$("#raceCategoryType").val(), m:$("#male").is(":checked"), f:$("#female").is(":checked"), y:$("#you").is(":checked")
                  },
                  beforeSend:function (xhr) {
                    console.log($("#ageMin").val());
                    $('#averages').html('<g:img dir="/images" file="spinner.gif"/> Loading Chart times');
                  },
                  success:function (data, textStatus) {
                    $('#averages').html(data);
                  },
                  error:function (XMLHttpRequest, textStatus, errorThrown) {
                    $('#averages').html(textStatus);
                  }});
  }
</script>

<script>
  $(function () {
    $("#slider-range").slider({
                                range:true,
                                min:16,
                                max:85,
                                values:[ ${num}, ${num+5} ],
                                slide:function (event, ui) {
                                  $("#age").text(ui.values[ 0 ] + " - " + ui.values[ 1 ]);
                                  $("#ageMin").val(ui.values[0]);
                                  $("#ageMax").val(ui.values[1]);
                                },
                                change:function (event, ui) {
//                                    alert("stopped at " + ui.value)
                                }
                              });
    $("#age").text($("#slider-range").slider("values", 0) +
                   " - " + $("#slider-range").slider("values", 1));
  });
</script>
</body>
</html>