<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap"/>
		<title>Tri Harder Results</title>
	</head>

	<body>
		<div class="row-fluid">
			<section id="main" class="span12">

				<div class="hero-unit">
					<h1>Race Results Tracking</h1>
                          <br>
					<p>Currently the site is in Prototype phase and new account creation is disabled.</p>
					<p>${com.tgid.tri.results.RaceResult.count()} Results & Counting</p>
				</div>

                <div class="row-fluid">
                    <div class="span4">
                        <g:img dir="/images/site" file="glyphicons_042_group.png" class="hp-icon" />
                        <h2>Athletes</h2>
                        <p>Whether you are a runner, swimmer, cyclist or triathlete this site will aggregate your results.</p>
                    </div>
                    <div class="span4">
                        <g:img dir="/images/site" file="glyphicons_079_podium.png" class="hp-icon" />
                        <h2>Results</h2>
                        <p>Results will be imported automatically from Athlinks when you register and then weekly there after.  You may also manually input results which is easy and quick.  </p>
                    </div>
                    <div class="span4">
                        <g:img dir="/images/site" file="glyphicons_082_roundabout.png" class="hp-icon" />
                        <h2>Graphs!</h2>
                        <p>After your results are entered, you will be able to see stats and graphs at a glance that you have been tracking in spreadsheets for years.  We are adding new graphs ALL THE TIME!</p>
                    </div>
                </div>
			</section>
		</div>

    <div class="row-fluid">

    </div>

	</body>
</html>
