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
  <h1>Results For ${user.firstName} <small> give me the data</small></h1>
</div>
<div class="row-fluid">
  <div class="span4">
    <h2>Trending</h2>
    <p>View your results trending broken out by race.</p>
    <p align="right"><a href="#" class="btn-info">View Trending</a></p>
  </div>
  <div class="span4">asdf</div>
  <div class="span4">asdf</div>

</div>
</body>
</html>