<%@ page import="com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>User Dashboard</title>
</head>

<body>

<div class="page-header">
  <h1>Results For ${user.firstName} <small> results at a glance</small></h1>
</div>

<sec:ifAnyGranted roles="ROLE_ADMIN">
  <div class="row">
    <g:select name="user.id" from="${User.list().sort()}" id="user.id" value="${user?.id}" />
  </div>
</sec:ifAnyGranted>

<div class="row">
  %{--<div class="span12">--}%
  <g:render template="/templates/dashboardHeader" model="[sport:'Run']" />

  <div id="results-run" class="accordion">
    <g:render template="/templates/runResults" collection="${runs.list().sort {a, b -> b.date <=> a.date}}" var="result"/>
  </div>
  %{--</div>--}%
</div>
<hr>

<div class="row">
  %{--<div class="span12">--}%
  <g:render template="/templates/dashboardHeader" model="[sport:'Triathlon']" />

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