<%@ page import="com.tgid.tri.auth.State" %>
<html>
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

    <div class='span7'>
      <g:if test="${flash.message}">${flash.message}<br /></g:if>

      <h2>Matched Users</h2>
      <g:form id="registrationForm" class="form-horizontal" action="completeImport">
        <g:hiddenField name="userID" id="userID" value="${user.id}" />
        <fieldset>
          <g:if test="${racers?.size() <= 0}">
            <div class="control-group ">
              No Users Matched
            </div>
          </g:if>
          <g:else>
          <g:each in="${racers}" var="usr">
            <div class="row-fluid">
            %{--[user: user, racerID: it.RacerID as Long, age: it?.Age, city: it.City, name: it.DisplayName]--}%
              <g:checkBox checked="true" name="racers" value="${usr.racerID}" />&nbsp;&nbsp;${usr.name} (age: ${usr.age}, city: ${usr.city})<br/>
            </div>
          </g:each>
          </g:else>
          <div class="form-actions">
            <button type="submit" class="btn btn-primary">
              <i class="icon-ok icon-white"></i>
              <g:message code="default.button.next.label" default="Next"/>
            </button>
            <a href="${createLink(controller:'user', action:'show', id:userID)}" class="btn">Cancel</a>
          </div>
        </fieldset>
      </g:form>
    </div>

  </div>
</div>
<script type="text/javascript">
  $(function () {
    $('.autopop').popover();
  });
</script>
</body>
</html>