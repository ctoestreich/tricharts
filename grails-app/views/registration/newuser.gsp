<%@ page import="com.tgid.tri.auth.State" %>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title><g:message code="springSecurity.login.title"/></title>
</head>

<body>
<div class="page-header">
  <h1>Registration <small>I want this!</small></h1>
</div>

<div class="container-fluid">
  <div class="row-fluid">

    <div class="span4">
      <h2>Step 2</h2>

      <p>You should receive an email shortly with a registration link.  Once you click the link your account will be confirmed and any existing results will be imported.</p>
    </div>

    <div class="span1">&nbsp;</div>

    <div class='span7'>

      <div class="hero-unit">
        <h1>Almost Done</h1>
        <br>
        <p><g:if test="${flash.message}">${flash.message}</g:if></p>
        <p>Temp: ${body}</p>
      </div>
    </div>

  </div>
</div>
<script type="text/javascript">
  $(function () {
    $('.autopop').popover();
  });
</script>
</body>
</html>