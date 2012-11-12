<%@ page import="com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>About Us</title>
</head>

<body>

<div class="page-header">
  <h1>FAQ <small>is it full of stars?</small></h1>
</div>

<g:if test="${flash.message}">
  <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
</g:if>

<div class="row-fluid well">
    <h5>How frequent are results inported?</h5>
    <p>New results will be auto-imported once a week from athlinks.</p>
</div>
<div class="row-fluid well">
  <h5>Can I enter my own results?</h5>
  <p>Yes, you can create your own results.  You also can modify your existing results that have been imported.  We know that sometimes not all the imported results or entered data matches with the official times.</p>
</div>
<div class="row-fluid well">
  <h5>Can I add races that are not listed?</h5>
  <p>Yes, you can <g:link controller="results" action="addRace">create races</g:link> that are missing.  These races will go into review status and will need to be approved by an admin before you can enter results.  This process should only take a day or two.</p>
</div>
<div class="row-fluid well">
  <h5>Can I delete my results?</h5>
  <p>Yes, you can delete your results.  Just know that any results from athlinks will be imported again and may reappear in your list within a week.  The only results that will stay deleted will be the ones you create manually.  If the imported ones are incorrectly affecting your PRs, simply modify the results to fix or remove them from showing up as a PR.</p>
</div>
<div class="row-fluid well">
  <h5>I have not received my confirmation email, now what?</h5>
  <p>Sometimes the email gremlins steal outbound emails.  You should <g:link controller="registration" action="resend">click here</g:link> and have your registration email resent to you.</p>
</div>
<div class="row-fluid well">
  <h5>My results look off, my PRs are too fast/slow, or my graphs show an odd result.</h5>
  <p>Sometimes during the import something can go awry and the wrong value can be entered.  Also there are times when the data available is just wrong.  Simply go to the dashboard, expand the race by clicking on the title and click the edit button to modify the results to fix the result in question.</p>
</div>
</body>
</html>