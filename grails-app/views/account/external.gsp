<%@ page import="com.tgid.tri.auth.State; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="account"/>
  <title>My Account</title>
  <r:require modules="dashboard"/>
  <gvisualization:apiImport/>
  <g:set var="pageTitle" value="External Accounts"/>
  <g:set var="pageSubTitle" value="you've been googled"/>
</head>

<body>

Below are the accounts you have linked to your trigrid.com account.
<br/>
<br/>
<h5>Athlinks</h5>
<g:each in="${user.racers}" var="racer">
  <a href="http://athlinks.com/racer/${racer.racerID}" target="_blank">http://athlinks.com/racer/${racer.racerID}</a><br/>
</g:each>

</body>
</html>