<%@ page import="grails.util.Environment; com.tgid.tri.race.RaceType; org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title><g:layoutTitle default="${meta(name: 'app.name')}"/></title>
  <meta name="description" content="">
  <meta name="author" content="Christian Oestreich">
  %{--<meta name="viewport" content="initial-scale = 1.0">--}%
  <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
  <!--[if lt IE 9]>
  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->
  <r:require modules="scaffolding, application"/>
  <g:if test="${Environment.current == Environment.PRODUCTION}">
    <r:require modules="analytics"/>
  </g:if>
  <!-- Le fav and touch icons -->
  <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
  <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
  <link rel="apple-touch-icon" sizes="72x72" href="${resource(dir: 'images', file: 'apple-touch-icon-72x72.png')}">
  <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-114x114.png')}">
  <g:layoutHead/>
  <r:layoutResources/>
  <script type="text/javascript">if(!window.console) console = {log:function () {
  }}; </script>
</head>

<body>

<g:render template="/templates/site/topnav" />

<div class="container content">

  <div class="row">
    <p align="right">
      <sec:ifNotGranted roles="ROLE_USER">
        <g:link controller="registration" action="index">register</g:link>&nbsp;|&nbsp;<g:link controller="login" action="index">login</g:link>
      </sec:ifNotGranted>
      <sec:ifAllGranted roles="ROLE_USER">
        <sec:username/>&nbsp;|&nbsp;<g:link controller="logout" action="index">logout</g:link>
      </sec:ifAllGranted>

    <div class="btn-group pull-right">
      %{--<sec:ifNotGranted roles="ROLE_FACEBOOK">--}%
      %{--<facebookAuth:connect permissions="${['email', 'user_about_me']}"/>--}%
      %{--</sec:ifNotGranted>--}%
      <sec:ifAllGranted roles="ROLE_FACEBOOK">
        Welcome! <sec:username/>
      </sec:ifAllGranted>
    </div>
  </p>
  </div>

    <g:layoutBody/>
</div>

<g:render template="/templates/site/footer" />

<r:script>
  $(function () {
    $(".collapse").collapse();
  });
</r:script>

<r:layoutResources/>

</body>
</html>