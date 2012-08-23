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
  <h1>Results For ${user.firstName} <small>give me the data</small></h1>
</div>


</body>
</html>