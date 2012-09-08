<%@ page import="com.tgid.tri.auth.User; com.tgid.tri.race.StatusType; com.tgid.tri.race.Race" %>
<g:set var="statusType" value="${params?.int('statusType') ?: 2}"/>
<g:set var="statusTypeText" value="${StatusType.getStatusType(params?.statusType ?: StatusType.Pending.value())}"/>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap">
  <title>User Data Import</title>
</head>

<body>

<div class="row-fluid">

  <div class="span12">

    <div class="page-header">
      <h1>${name} Log</h1>
    </div>

   <g:textArea name="logFile" id="logFile" class="span12" rows="30" cols="10">${data?.text}</g:textArea>

  </div>

</div>
<script>
  $(function(){
    var textarea = document.getElementById('logFile');
    textarea.scrollTop = textarea.scrollHeight;
  });
</script>
</body>
</html>
