  <html>
  <head>
    <meta name="layout" content="bootstrap"/>
    <title><g:message code="springSecurity.login.title"/></title>
  </head>

  <body>
  <div class="container-fluid">
    <div class="row-fluid">
      <div class="span4">&nbsp;</div>

      <div class="span4">
        <g:if test="${flash.message}">
          <div class="alert alert-error">
            <h4 class="alert-heading">Failed</h4>
            <g:message code="${flash.message}" args="${flash.args}"
                       default="${flash.default}"/></div>
        </g:if>

        <div id='login'>
          <div class='well'>
		<div class='fheader'><g:message code="springSecurity.login.header"/></div>

		<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
		</g:if>

		<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
			<p>
				<label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
				<input type='text' class='text_' name='j_username' id='username'/>
			</p>

			<p>
				<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
				<input type='password' class='text_' name='j_password' id='password'/>
			</p>

			<p id="remember_me_holder">
				<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
				<label for='remember_me'><g:message code="springSecurity.login.remember.me.label"/></label>
			</p>

			<p>
				<input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'/>&nbsp;<facebookAuth:connect permissions="email,user_about_me"/>
			</p>
		</form>
	</div>
</div>
<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
</script>
</body>
</html>
