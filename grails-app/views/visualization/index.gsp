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
  %{--<h1>Charts For ${user.firstName} <small> all your charts r belong to us</small></h1>--}%
%{--</div>--}%

%{--<g:render template="/templates/admin/userSelect"/>--}%

<g:render template="/templates/visualization/chartSelection"/>

<div class="row-fluid well">
  <div>
    <h3>Sample Content</h3>
    <br>
  </div>

  <div class="row-fluid">
    <div class="span6"><g:img dir="images/home" file="chart.png"/></div>

    <div class="span6"><g:img dir="images/home" file="chart2.png"/></div>
  </div>

  <div class="row-fluid">
    &nbsp;
  </div>

  <div class="row-fluid">
    <div class="span6"><g:img dir="images/home" file="chart3.png"/></div>

    <div class="span6"><g:img dir="images/home" file="chart4.png"/></div>
  </div>
</div>

</body>
</html>