<%@ page import="com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>About Us</title>
  <r:require modules="dashboard,results"/>
  <gvisualization:apiImport/>
</head>

<body>

<div class="page-header">
  <h1>About Us <small>we are legit</small></h1>
</div>

<g:if test="${flash.message}">
  <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
</g:if>

<div class="row well_clear">
  <div class="row-fluid">
    <h3>Tricharts.com</h3>

    <p>Christian started this site after he had been keeping results in a spreadsheet for many years.  He took his technical talents combined with his love for racing and created
    this site to help himself and others create meaningful statistics for themselves to be able to track year to year progress.</p>

    <p>There were some sites that already provide race results, but he took it a step further in allowing athletes to correct known mistakes and see graphical representations of their year to year progression.</p>

    <p>We will continually be adding more graphs and features as time goes on.  Make sure to check back for more details!</p>

    <p>&nbsp;</p>
    <h3>Donations</h3>

    <p>This work is currently being done pro bono, if you wish to contribute to the cost in hosting the site and development feel free to donate at the link below!
      <br>

    <form action="https://www.paypal.com/cgi-bin/webscr" method="post">
      <input type="hidden" name="cmd" value="_s-xclick">
      <input type="hidden" name="hosted_button_id" value="WSJ4YPZN378PS">
      <input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
      <img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
    </form></p>

  </div>
</div>
</body>
</html>