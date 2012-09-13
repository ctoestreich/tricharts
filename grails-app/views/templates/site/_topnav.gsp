<%@ page import="com.tgid.tri.race.RaceType" %>
<g:set var="paramMap" value="${[:]}"/>
<sec:ifAllGranted roles="ROLE_ADMIN">
  <g:set var="paramMap" value="${['user.id': params?.user?.id]}"/>
</sec:ifAllGranted>
<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">

      <!-- .btn-navbar is used as the toggle for collapsed navbar content -->
      <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
      %{--<a class="brand" href="./index.html">Bootstrap</a>--}%
      <div class="nav-collapse">
        <ul class="nav">
          <li><a <%=request.forwardURI == "${createLink(uri: '/')}" ? 'class="btn disabled"' : ''%> href="${createLink(uri: '/')}">Home</a></li>
          <li><a <%=request.forwardURI == "${createLink(controller: 'site', action: 'aboutus')}" ? ' class="btn disabled"' : ''%> href="${createLink(controller: 'site', action: 'aboutus', params: paramMap)}">About Us</a></li>
          <li><a <%=request.forwardURI == "${createLink(controller: 'site', action: 'contact')}" ? ' class="btn disabled"' : ''%> href="${createLink(controller: 'site', action: 'contact', params: paramMap)}">Contact</a></li>
          <li><a <%=request.forwardURI == "${createLink(controller: 'site', action: 'faq')}" ? ' class="btn disabled"' : ''%> href="${createLink(controller: 'site', action: 'faq', params: paramMap)}">FAQ</a></li>
          <sec:ifNotLoggedIn>
            <li><a <%=request.forwardURI == "${createLink(controller: 'registration', action: 'index')}" ? ' class="btn disabled"' : ''%> href="${createLink(controller: 'registration', action: 'index')}">Register</a></li>
            <li><a <%=request.forwardURI == "${createLink(controller: 'login', action: 'auth')}" ? ' class="btn disabled"' : ''%> href="${createLink(controller: 'login', action: 'auth')}">Login</a></li>
          </sec:ifNotLoggedIn>
          <sec:ifLoggedIn>
            <li><a <%=request.forwardURI == "${createLink(controller: 'dashboard', action: 'index')}" ? ' class="btn disabled"' : ''%> href="${createLink(controller: 'dashboard', action: 'index', params: paramMap)}">Dashboard</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Charts <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="${createLink(controller: 'visualization', action: 'progression', params: ['raceType': RaceType.Running] << paramMap)}">Run Trending</a></li>
                <li><a href="${createLink(controller: 'visualization', action: 'averages', params: ['raceType': RaceType.Running] << paramMap)}">Run Averages</a></li>
                <li><a href="${createLink(controller: 'visualization', action: 'prs', params: ['raceType': RaceType.Running] << paramMap)}">Run PRs</a></li>
                <li><hr></li>
                <li><a href="${createLink(controller: 'visualization', action: 'progression', params: ['raceType': RaceType.Triathlon] << paramMap)}">Triathlon Trending</a></li>
                <li><a href="${createLink(controller: 'visualization', action: 'averages', params: ['raceType': RaceType.Triathlon] << paramMap)}">Triathlon Averages</a></li>
                <li><a href="${createLink(controller: 'visualization', action: 'prs', params: ['raceType': RaceType.Triathlon] << paramMap)}">Triathlon PRs</a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Manage Results <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="${createLink(controller: 'dashboard', action: 'createResult', params: ['raceType': RaceType.Triathlon] << paramMap)}">Add Triathlon Result</a></li>
                <li><a href="${createLink(controller: 'dashboard', action: 'createResult', params: ['raceType': RaceType.Running] << paramMap)}">Add Running Result</a></li>
                <li><a href="${createLink(controller: 'dashboard', action: 'createResult', params: ['raceType': RaceType.Biking] << paramMap)}">Add Biking Result</a></li>
                <li><a href="${createLink(controller: 'dashboard', action: 'createResult', params: ['raceType': RaceType.Swimming] << paramMap)}">Add Swimming Result</a></li>
                <li><hr></li>
                <li><a href="${createLink(controller: 'dashboard', action: 'addRace')}">Add Race Course</a></li>
              </ul>
            </li>
          </sec:ifLoggedIn>
          <sec:ifAnyGranted roles="ROLE_ADMIN">
            <li><a <%=request.forwardURI == "${createLink(controller: 'admin')}" ? 'class="btn disabled"' : ''%> href="${createLink(controller: 'admin', action: 'index')}">Site Admin</a></li>
          </sec:ifAnyGranted>
        </ul>
      </div>

    </div>
  </div>
</div>