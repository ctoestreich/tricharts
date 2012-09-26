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

<div class="row-fluid">
  <h3>Tricharts.com</h3>

  <p>I started development on this site after I had been keeping results in a spreadsheet for many years and wanted a more automated and better tool to view and graph my progress.  My love for racing and ability to give back to the athletic communities through my technical skills prompted me to make this site a useful tool for others to enjoy as well.</p>

  <p>While there are sites that already provide race results, I took this record keeping a step further in allowing athletes to correct known mistakes with results, add their own results and to see graphical representations of their year to year progression, averages and placements.</p>

  <p>I am continually adding more graphs and features all the time.  Make sure to check back often!</p>

  <p>&nbsp;</p>

  <h3>Donations</h3>

  <p>This work is currently being done pro bono. if you wish to contribute to the cost in hosting the site and development time (or help me buy a new bike!), feel free to donate any amount at the link below!

  &nbsp;<br/><br/>

  <form action="https://www.paypal.com/cgi-bin/webscr" method="post">
    <input type="hidden" name="cmd" value="_s-xclick">
    <input type="hidden" name="hosted_button_id" value="WSJ4YPZN378PS">
    <input style="border:0px;" type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
    <img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
  </form></p>

</div>
</body>
</html>