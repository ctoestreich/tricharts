<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>User Dashboard</title>
</head>

<body>

<div class="row">

</div>

<div class="row">
  %{--<div class="span12">--}%
  <h2>Runs</h2>

  <div id="results-run" class="accordion">
    <g:render template="/templates/runResults" collection="${runs.list().sort {a, b -> b.date <=> a.date}}" var="result"/>
  </div>
  %{--</div>--}%
</div>
<hr>

<div class="row">
  %{--<div class="span12">--}%
  <h2>Triathlons</h2>

  <div id="results-triathlon" class="accordion">
    <g:render template="/templates/triathlonResults" collection="${triathlons.list().sort {a, b -> b.date <=> a.date}}" var="result"/>
  </div>
</div>


%{--<div class="row">--}%
%{--<div class="span12">--}%
%{--<table class="table table-striped">--}%
%{--<thead>--}%
%{--<tr>--}%
%{--<th>Date</th>--}%
%{--<th>asdfasd</th>--}%
%{--<th>Last Name</th>--}%
%{--<th>Username</th>--}%
%{--</tr>--}%
%{--</thead>--}%
%{--<tbody>--}%
%{--<tr>--}%
%{--<td>1</td>--}%
%{--<td>Mark</td>--}%
%{--<td>Otto</td>--}%
%{--<td>@mdo</td>--}%
%{--</tr>--}%
%{--</tbody>--}%
%{--</table>--}%
%{--</div>--}%
%{--</div>--}%

</body>
</html>