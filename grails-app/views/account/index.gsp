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

Your account information is below.  You may update your account at any time.  Your linked accounts will currently not be affected by any changes you make below.
<p>&nbsp;</p>
<g:form class="form-horizontal" controller="account" action="index">
  <fieldset>
    <f:field bean="${user}" property="username" input-class="span10" input-readonly="readonly"/>
    <f:field bean="${user}" property="firstName" input-class="span10"/>
    <f:field bean="${user}" property="lastName" input-class="span10"/>
    <f:field bean="${user}" property="dob"/>
    <f:field bean="${user}" property="genderType" required="true" input-class="span5"/>
    <div class="control-group ">
      <label class="control-label" for="states">State(s) <a href="javascript:void(0);" class="autopop" rel="popover" data-content="Select the state(s) in which you currently reside and race in. Hold CTRL to select multiple." data-original-title="State(s)"><i class="icon-question-sign"></i></a></label>

      <div class="controls">
        <g:select class="span10" optionKey="id" value="${user?.states}" optionValue="name" id="states" name="states" from="${State?.list()?.sort {it.name}}" multiple="true" size="8"/>
      </div>
    </div>

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