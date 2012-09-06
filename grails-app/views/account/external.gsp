<%@ page import="com.tgid.tri.auth.State; com.tgid.tri.auth.User" %>
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
  <h1>External Accounts <small>you've been googled</small></h1>
</div>

<g:if test="${flash.message}">
  <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
</g:if>
<div class="row-fluid">
  <div class="span4 well">
    <cache:render template="/templates/account/accountNav" key="${request.forwardURI}" />
  </div>

  <div class="span8">
    Below are the accounts you have linked to your trigrid.com account.
    <br />
    <br />
    <h5>Athlinks</h5>
    <g:each in="${user.racers}" var="racer">
    <a href="http://athlinks.com/racer/${racer.racerID}" target="_blank">http://athlinks.com/racer/${racer.racerID}</a><br/>
    </g:each>
  </div>
</div>
</body>
</html>