<%@ page import="com.tgid.tri.auth.State; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="account"/>
  <title>My Account</title>
  <r:require modules="dashboard"/>
  <gvisualization:apiImport/>
</head>

<body>

Change your password below.
<p>&nbsp;</p>
<g:form class="form-horizontal" controller="account" action="password">
  <fieldset>
    <f:field bean="${passwordChangeCommand}" input-type="password" property="password" input-class="span10"/>
    <f:field bean="${passwordChangeCommand}" input-type="password" property="passwordAgain" input-class="span10"/>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">
        <i class="icon-ok icon-white"></i>
        <g:message code="default.button.update.label" default="Update"/>
      </button>
    </div>
  </fieldset>
</g:form>

<script type="text/javascript">
  $(function () {
    $('.autopop').popover();
  });
</script>
</body>
</html>