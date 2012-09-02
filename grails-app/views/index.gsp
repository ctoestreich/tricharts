<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>TriCharts.com</title>
</head>

<body>
<div class="row-fluid">
  <section id="main" class="span12">

    <div class="hero-unit">
      <h1>Race Results Tracking</h1>
      <br>

      <p><a href="${createLink(controller: 'login', action: 'auth')}">Login</a> or <a href="${createLink(controller: 'registration', action: 'index')}">create account</a> to get started.</p>

      <p>${com.tgid.tri.results.RaceResult.count()} Results & Counting</p>
    </div>

    <div class="row-fluid">
      <div class="span4">
        <g:img dir="/images/site" file="glyphicons_042_group.png" class="hp-icon"/>
        <h2>Athletes</h2>

        <p>Whether you are a runner, swimmer, cyclist or triathlete this site will aggregate your results.</p>
      </div>

      <div class="span4">
        <g:img dir="/images/site" file="glyphicons_079_podium.png" class="hp-icon"/>
        <h2>Results</h2>

        <p>Results will be imported automatically from Athlinks when you register and then weekly there after.  You may also manually input results which is easy and quick.</p>
      </div>

      <div class="span4">
        <g:img dir="/images/site" file="glyphicons_082_roundabout.png" class="hp-icon"/>
        <h2>Graphs!</h2>

        <p>After your results are entered, you will be able to see stats and graphs at a glance that you have been tracking in spreadsheets for years.  We are adding new graphs ALL THE TIME!</p>
      </div>
    </div>
  </section>
</div>

<div class="row-fluid">
  <hr>
</div>

<div class="row-fluid well">
  <div>
    <h3>Sample Content</h3>
    <br>
  </div>

  <div class="row-fluid">
    <div class="span6"><g:img dir="images/home" file="chart.png"/></div>

    <div class="span6"><g:img dir="images/home" file="chart2.png"/></div>
  </div>
  <div class="row-fluid">
    &nbsp;
  </div>
  <div class="row-fluid">
    <div class="span6"><g:img dir="images/home" file="chart3.png"/></div>

    <div class="span6"><g:img dir="images/home" file="chart4.png"/></div>
  </div>
</div>

</body>
</html>
