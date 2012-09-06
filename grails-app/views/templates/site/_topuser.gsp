<div class="row">
  <p align="right">
    <sec:ifNotGranted roles="ROLE_USER">
      <g:link controller="registration" action="index">register</g:link>&nbsp;|&nbsp;<g:link controller="login" action="index">login</g:link>
    </sec:ifNotGranted>
    <sec:ifAnyGranted roles="ROLE_USER">
      <sec:username/>&nbsp;|&nbsp;<g:link controller="account" action="index">account</g:link>&nbsp;|&nbsp;<g:link controller="logout" action="index">logout</g:link>
    </sec:ifAnyGranted>
  </p>
</div>