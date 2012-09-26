<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title><g:message code="springSecurity.login.title"/></title>
</head>

<body>
<div class="container-fluid">
  <div class="row-fluid">
    <div class="span2">&nbsp;</div>

    <div class="span8">
      <div class="page-header">
        <h1><g:message code="springSecurity.login.header"/> <small>start logging greatness</small></h1>
      </div>

      <g:if test="${flash.message}">
        <div class="alert alert-error">
          <h4 class="alert-heading">Failed</h4>
          <g:message code="${flash.message}" args="${flash.args}"
                     default="${flash.default}"/></div>
      </g:if>
      <g:if test='${flash.message}'>
        <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
      </g:if>

      <form action='${postUrl}' method='POST' id='loginForm' class='form-horizontal' autocomplete='off'>
        <fieldset>
          <div class="control-group">
            <label for='username' class="control-label"><g:message code="user.username.label"/>:</label>

            <div class="controls"><input type='email' name='j_username' id='username'/></div>
          </div>

          <div class="control-group">
            <label for='password' class="control-label"><g:message code="springSecurity.login.password.label"/>:</label>

            <div class="controls"><input type='password' name='j_password' id='password'/></div>
          </div>

          <div class="control-group">
            <label for='remember_me' class="control-label"><g:message code="springSecurity.login.remember.me.label"/></label>

            <div class="controls"><input type='checkbox' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/></div>
          </div>
          <div class="control-group">
            <p style="text-align: center;">Forgot Password? <g:link controller="registration" action="forgotPassword">Click here</g:link> to reset.</p>
            </div>

          <div class="form-actions">
            <button type="submit" class="btn btn-primary">${message(code: "springSecurity.login.button")}</button>
          </div>
        </fieldset>
      </form>
      <script type='text/javascript'>
        <!--
        (function () {
          document.forms['loginForm'].elements['j_username'].focus();
        })();
        // -->
      </script>
    </div>
  </div>
</div>
</body>
</html>
