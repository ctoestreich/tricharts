<%@ page import="com.tgid.tri.auth.User" %>
<g:set var="today" value="${new Date()}"/>
<g:set var="start" value="${1999}"/>
<g:set var="end" value="${new java.util.Date().year+1900}"/>

<g:form controller="${controllerName}" action="${actionName}" id="dateForm" name="dateForm">
  <g:hiddenField name="raceType" value="${params?.raceType}" />
  <g:hiddenField name="user.id" value="${params?.user?.id}" />
  <div class="row">
    <button class="close" data-dismiss="alert">×</button>
    From<g:select from="${start..end}" value="${params?.int('startDate') ?: start}" name="startDate" class="input-small"/>&nbsp;To<g:select from="${start..end}" class="input-small" value="${params?.int('endDate') ?: today.getYear()+1900}" name="endDate"/>&nbsp;<button type="submit" class="btn btn-small">Change Rance</button>
  </div>
</g:form>

<script>
</script>
