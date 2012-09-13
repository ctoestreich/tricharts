<%@ page import="com.tgid.tri.auth.State; com.tgid.tri.race.SegmentType; com.tgid.tri.race.RaceCategoryType; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>Averages</title>
  <r:require modules="dashboard,results, progression, widgets"/>
  <gvisualization:apiImport/>
  <style>
    /*.ui-autocomplete-input {width: 250px !important;}*/
    /*.ui-slider-horizontal .ui-slider-handle {height:2.1em !important;}*/
    /*.ui-slider-horizontal{height:1.7em !important;}*/
  </style>
</head>

<body>

<div class="page-header">
  <h1>Running Averages By State <small>dots...</small></h1>
</div>

<g:render template="/templates/admin/userSelect"/>

%{--<g:render template="/templates/visualization/dateSelection"/>--}%

<g:render template="/templates/visualization/chartSelection"/>

<div class="row-fluid well">
  <div class="span6">
    <label for="slider-range">Age Range: <span id="age" style="border:0; color:#59BEDC; font-weight:bold;"></span></label>

    <div id="slider-range"></div>
    <g:hiddenField name="ageMin" value="${ageMin ?: 30}"/>
    <g:hiddenField name="ageMax" value="${ageMax ?: 35}"/>
  </div>

  <div class="span4">
    <label for="state">State:</label>
    <g:select name="state" class="span12" from="${State.list()}" optionKey="abbrev" optionValue="name" value="${user?.states?.toList()?.get(0)?.abbrev}"/><br/>
    <label for="state">Race Category:</label>
    <g:select name="raceCategoryType" class="span12" from="${races}" value="${raceCategoryType ?: com.tgid.tri.race.RaceCategoryType.OneMile}"/>
  </div>

  <div class="span2">
    <label form="submit">Refresh Data:</label>
    <button onclick="loadScatterPlot()" type="submit" class="btn btn-action">Submit</button>
  </div>
</div>


<div class="row-fluid" id="averages"></div>

<script>
  $(function () {
    loadScatterPlot();
//    $('#state').combobox();
  });

  function loadScatterPlot() {
    jQuery.ajax({
                  type:'POST',
                  url:'${createLink(controller: 'visualization', action:'runScatter', params:['user.id',params?.user?.id])}',
                  data:{ 'user.id':'${params?.user?.id}', div:'averagesDiv', ageMin:$("#ageMin").val(), ageMax:$("#ageMax").val(), state:$("#state").val(), r:$("#raceCategoryType").val()},
                  beforeSend:function ( xhr ) {
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
                                values:[ 30, 35 ],
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