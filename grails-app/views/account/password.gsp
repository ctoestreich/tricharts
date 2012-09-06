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
  <h1>Change Password <small>keep it fresh</small></h1>
</div>

<g:if test="${flash.message}">
  <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
</g:if>
<div class="row-fluid">
  <div class="span4 well">
    <cache:render template="/templates/account/accountNav" key="${request.forwardURI}" />
  </div>

  <div class="span8">
    Change your password below.
    <p>&nbsp;</p>
    <g:form class="form-horizontal" controller="account" action="password">
      <fieldset>
        <f:field bean="${passwordChangeCommand}" input-type="password" property="password" input-class="span10" />
        <f:field bean="${passwordChangeCommand}" input-type="password" property="passwordAgain" input-class="span10" />
        <div class="form-actions">
          <button type="submit" class="btn btn-primary">
            <i class="icon-ok icon-white"></i>
            <g:message code="default.button.update.label" default="Update"/>
          </button>
        </div>
      </fieldset>
    </g:form>

  </div>
</div>
<script type="text/javascript">
  $(function(){
    $('.autopop').popover();
  });
</script>
</body>
</html>