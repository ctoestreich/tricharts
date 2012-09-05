<%@ page import="com.tgid.tri.auth.State" %>
<html xmlns="http://www.w3.org/1999/html">
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
      <h2>Step 1</h2>

      <p>Sign up and enter your username (email), password, dob & state (used to help find results at athlinks).  You will next be asked to confirm any matching
      users found at <a href="http://athlinks.com/" target="_blank">Athlinks.com</a> to start your initial results import process.</p>
<br />
      <p>If you have already registered but you have not recieved your confirmation email <g:link controller="registration" action="resend">click here</g:link></p>
    </div>

    <div class="span1">&nbsp;</div>

    <div class='span7'>

      <g:form id="registrationForm" class="form-horizontal" action="index">
        <fieldset>
          <f:field property="username" bean="userInstance" input-class="span10"/>
          <f:field property="firstName" bean="userInstance" input-class="span10"/>
          <f:field property="lastName" bean="userInstance" input-class="span10"/>
          <f:field property="password" bean="userInstance" input-type="password"/>
          <div class="control-group <g:if test="${userInstance.errors.getFieldError('password')}">error</g:if>">
            <label class="control-label" for="password2">Password Again</label>

            <div class="controls">
              <input type="password" name="password2" value="${params?.password2}" <g:if test="${userInstance.errors.getFieldError('password')}">invalid</g:if> required id="password2"/>
              <g:if test="${userInstance.errors.getFieldError('password')}"><span class="help-inline"><g:message error="${userInstance.errors.getFieldError('password')}" /> </span></g:if>
            </div>
          </div>
          <f:field property="dob" bean="userInstance"/>
          <div class="control-group ">
            <label class="control-label" for="states">State(s) <a href="javascript:void(0);" class="autopop" rel="popover" data-content="Select the state(s) in which you currently reside and race in." data-original-title="State(s)"><i class="icon-question-sign"></i></a></label>
            <div class="controls">
              <g:select class="span10" optionKey="id" value="${userInstance?.states}" optionValue="name" id="states" name="states" from="${State?.list()?.sort {a, b -> a.name <=> b.name}}" multiple="true" size="8"/>
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
  $(function(){
    $('.autopop').popover();
  });
</script>
</body>
</html>