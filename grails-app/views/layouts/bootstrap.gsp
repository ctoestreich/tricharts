<%@ page import="org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title><g:layoutTitle default="${meta(name: 'app.name')}"/></title>
  <meta name="description" content="">
  <meta name="author" content="Christian Oestreich">

  <meta name="viewport" content="initial-scale = 1.0">

  <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
  <!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

  <r:require modules="scaffolding"/>

  <!-- Le fav and touch icons -->
  <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
  <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
  <link rel="apple-touch-icon" sizes="72x72" href="${resource(dir: 'images', file: 'apple-touch-icon-72x72.png')}">
  <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-114x114.png')}">

  <g:layoutHead/>
  <r:layoutResources/>
</head>

<body>

<nav class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container-fluid">

      <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>

      <a class="brand" href="${createLink(uri: '/')}">Tri Harder Results</a>

      <div class="nav-collapse">
        <ul class="nav">
          <li<%=request.forwardURI == "${createLink(uri: '/')}" ? ' class="active"' : ''%>><a href="${createLink(uri: '/')}">Home</a></li>
          %{--<li<%=request.forwardURI == "${createLink(controller: 'demo', action: 'index')}" ? ' class="active"' : ''%>><a href="${createLink(controller: 'demo', action: 'index')}">Framework Demo</a></li>--}%
          %{--<li class="dropdown">--}%
          %{--<a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin <b class="caret"></b></a>--}%
          %{--<ul class="dropdown-menu">--}%
          %{--<li><a href="${createLink(controller:'admin',action:'index')}">Dashboard</a></li>--}%
          %{--<li><a href="${createLink(controller:'admin',action:'properties')}">System Properties</a></li>--}%
          %{--</ul>--}%
          %{--</li>--}%
        </ul>
      </div>

      <div class="btn-group pull-right">
        <sec:ifNotGranted roles="ROLE_FACEBOOK">
          <facebookAuth:connect permissions="${['email', 'user_about_me']}"/>
        </sec:ifNotGranted>
        <sec:ifAllGranted roles="ROLE_FACEBOOK">
          Welcome! <sec:username/>
        </sec:ifAllGranted>
      </div>
    </div>
  </div>
</nav>

<div class="container-fluid">
  <g:layoutBody/>

  <hr>

  <footer>
    <p>&copy; Two Guys In Design, LLC 2012</p>
  </footer>
</div>

<r:layoutResources/>

</body>
</html>