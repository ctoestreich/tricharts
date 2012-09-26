<ul class="nav nav-list">
  <li class="nav-header">Tricharts Account</li>
  <li <%=request.forwardURI == "${createLink(controller: 'account', action: 'index')}" ? ' class="active"' : ''%>><g:link controller="account" action="index">User Profile</g:link></li>
  <li <%=request.forwardURI == "${createLink(controller: 'account', action: 'password')}" ? ' class="active"' : ''%>><g:link controller="account" action="password">Change Password</g:link></li>
  <li class="nav-header">Results</li>
  <li <%=request.forwardURI == "${createLink(controller: 'results', action: 'index')}" ? ' class="active"' : ''%>><g:link controller="results" action="index">My Results</g:link></li>
  <li <%=request.forwardURI == "${createLink(controller: 'results', action: 'createResult')}" ? ' class="active"' : ''%>><g:link controller="results" action="createResult">Add Result</g:link></li>
  <li <%=request.forwardURI == "${createLink(controller: 'results', action: 'addRace')}" ? ' class="active"' : ''%>><g:link controller="results" action="addRace">Add Race</g:link></li>
  <li class="nav-header">External Accounts</li>
  <li <%=request.forwardURI == "${createLink(controller: 'account', action: 'external')}" ? ' class="active"' : ''%>><g:link controller="account" action="external">My Linked Accounts</g:link></li>
</ul>