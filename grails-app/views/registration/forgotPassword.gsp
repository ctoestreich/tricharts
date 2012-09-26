<%@ page import="com.tgid.tri.auth.State" %>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title><g:message code="springSecurity.login.title"/></title>
</head>

<body>
<div class="page-header">
  <h1>Registration <small>fallen and I can't log in...</small></h1>
</div>

<div class="container-fluid">
  <div class="row-fluid">

    <div class="span4">
      <h2>Forgot Password</h2>

      <p>If you forgot or need to reset your password, enter your email address and we will send you instructions on helping you regain access.</p>
    </div>

    <div class="span1">&nbsp;</div>

    <div class='span7'>

      <g:formRemote name="resendForm" id="resendForm" class="form-horizontal" url="[controller:'registration', action:'resendPasswordEmail']"
                    onSuccess="promptUser()" update="modal-body" before="resetModal()">
        <fieldset>
          <f:field property="username" bean="userInstance" input-class="span10"/>
          <div class="form-actions">
            <button class="btn btn-primary">
              <i class="icon-ok icon-white"></i>
              <g:message code="default.button.resend.label" default="Resend"/>
            </button>
          </div>
        </fieldset>
      </g:formRemote>
    </div>

  </div>
</div>
<script type="text/javascript">
  $(function(){
    $('.autopop').popover();
  });

  function resetModal(){
    $('#modal-body').html('');
    $('#email')
  }

  function promptUser(response){
//    console.log(response);
//    $('#modal-body').html(response);
    $('#sendModal').modal('show');
//    console.log('modal!');
  }
</script>

<div class="modal hide fade" id="sendModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h3>Password Reset Link</h3>
  </div>
  <div class="modal-body" id="modal-body"></div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
  </div>
</div>
</body>
</html>