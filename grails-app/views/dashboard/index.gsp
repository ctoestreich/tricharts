<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>User Dashboard</title>
</head>

<body>

<div class="container">

  <div class="row well">

  </div>

  <div class="row well">
    %{--<div class="span12">--}%
    <a href="#results-run" data-toggle="collapse" data-target="#results-run">Runs</a>
    <div id="results-run" class="collapse">
    <g:render template="/templates/raceResult" collection="${runs}" var="result"/>
    </div>
    %{--</div>--}%
  </div>

  <div class="row well">
    %{--<div class="span12">--}%
      <h2>Triathlons</h2>
      <div id="results-run" class="collapse">
      <g:render template="/templates/raceResult" collection="${triathlons}" var="result"/>
      </div>
    %{--</div>--}%
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

</div>
</body>
</html>