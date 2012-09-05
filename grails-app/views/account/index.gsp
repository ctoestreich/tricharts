<%@ page import="com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>My Account</title>
  <r:require modules="dashboard"/>
  <gvisualization:apiImport/>
</head>

<body>

<div class="page-header">
  <h1>My Account <small>it is all about me</small></h1>
</div>

<g:if test="${flash.message}">
  <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
</g:if>
<div class="row-fluid">
  <div class="span4 well">
    <ul class="nav nav-list">
      <li class="nav-header">Tricharts Account</li>
      <li <%=request.forwardURI == "${createLink(controller: 'account', action: 'index')}" ? ' class="active"' : ''%>><a href="#">User Profile</a></li>
      <li><a href="#">Email Preferences</a></li>
      <li class="nav-header">External Accounts</li>
      <li><a href="#">Athlinks Accounts</a></li>
    </ul>
  </div>

  <div class="span8">
    this is the content area

  </div>
</div>

</body>
</html>