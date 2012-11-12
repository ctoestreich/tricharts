<%@ page import="com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>Contact Us</title>
</head>

<body>

<div class="page-header">
  <h1>Contact <small>cool story bro</small></h1>
</div>

<g:if test="${flash.message}">
  <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
</g:if>

<div class="container-fluid">
  <div class="row-fluid">

    <div class="span3">
      <p>If you have any questions of comments feel free to use the form to contact me.</p>
    </div>

    <div class='span9'>

      <g:form id="contactForm" class="form-horizontal" action="contactSend">
        <fieldset>
          <div class="control-group">
            <label class="control-label" for="email">Email</label>
            <div class="controls">
              <input class="span10"  type="email" name="email" required id="email" value="<sec:username />" />
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" for="name">Name</label>
            <div class="controls">
              <input class="span10" type="text" name="name" required id="name" />
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" for="comment">Text</label>
            <div class="controls">
              <textarea class="span10"  name="comment" required id="comment" rows="10" cols="20"></textarea>
            </div>
          </div>

          <div class="form-actions">
            <button type="submit" class="btn btn-primary">
              <i class="icon-ok icon-white"></i>
              <g:message code="default.button.submit.label" default="Submit"/>
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