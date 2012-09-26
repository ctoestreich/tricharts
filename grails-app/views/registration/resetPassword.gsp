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
      <h2>Change Password</h2>

      <p>Type your new password in the fields to reset your account password.</p>
    </div>

    <div class="span1">&nbsp;</div>

    <div class='span7'>
      <g:if test="${flash.message}">${flash.message}<br /></g:if>

      <g:form id="registrationForm" class="form-horizontal" action="password"  autocomplete="off" params="[t:params?.t]">
        <fieldset>
          <f:field property="password" bean="userInstance" input-type="password" value="" />
          <div class="control-group <g:if test="${userInstance.errors.getFieldError('password')}">error</g:if>">
            <label class="control-label" for="password2">Password Again</label>

            <div class="controls">
              <input type="password" name="password2" value="" <g:if test="${userInstance.errors.getFieldError('password')}">invalid</g:if> required id="password2"/>
              <g:if test="${userInstance.errors.getFieldError('password')}"><span class="help-inline"><g:message error="${userInstance.errors.getFieldError('password')}" /> </span></g:if>
            </div>
          </div>
          <div class="form-actions">
            <button type="submit" class="btn btn-primary">
              <i class="icon-ok icon-white"></i>
              <g:message code="default.button.next.label" default="Next"/>
            </button>
          </div>
        </fieldset>
      </g:form>
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