<%@ page import="com.tgid.tri.auth.User" %>

<sec:ifAnyGranted roles="ROLE_ADMIN">
  <g:form controller="dashboard" action="index" id="userForm" name="userForm">
    <div class="row">
      <button class="close" data-dismiss="alert">×</button>
      <g:select onchange="app.changeUser()" name="user.id" from="${User.listOrderByFirstName()}" id="userId" optionKey="id" value="${user?.id}"/>
    </div>
  </g:form>

</sec:ifAnyGranted>