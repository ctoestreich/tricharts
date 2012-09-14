<%@ page import="com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>User Dashboard</title>
  <r:require modules="dashboard,results"/>
  <gvisualization:apiImport/>
</head>

<body>

<div class="page-header">
  <h1>Results For ${user.firstName} <small>results at a glance</small></h1>
</div>

<table width="100%" border="0" cellpadding="3" cellspacing="0" style="background-color:#fff;" class="table table-striped">
  <tbody>
  <tr>
    <th class="clsResultBL"></th>
    <th class="clsResultBL">Name</th>
    <th class="clsResultBL">Date</th>
    <th class="clsResultBL">Plc A</th>
    <th class="clsResultBL">Plc G</th>
    <th class="clsResultBL">Plc O</th>
    <th class="clsResultBL">Pace</th>
    <th class="clsResultBL">Time</th>
  </tr>
  <g:each in="${races.list().sort{a,b -> b.date <=> a.date}}" var="result">
    <tr>
      <td class="${result.race.raceType.raceType}"><small>${result.race.raceType.raceType.substring(0,1)}</small></td>
      <td class="">${result.race.name}</td>
      <td class="">${result.race.date.format('M-dd-yyyy')}</td>
      <td class="">${result.placeAgeGroup}</td>
      <td class="">${result.placeGender}</td>
      <td class="">${result.placeOverall}</td>
      <td class="">${result?.segmentResults?.size() == 1 ? result.segmentResults.toArray()[0].pace : ''}</td>
      <td class=""><tri:formatDuration duration="${result?.duration}" /></td>
    </tr>
  </g:each>
</table>

</body>
</html>