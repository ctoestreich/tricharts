<div id="chartSelection" class="accordion">
  <div class="accordion-group">
    <div class="accordion-heading">
      <a class="accordion-toggle" data-toggle="collapse" href="#chartSelectionDiv"><b>Chart Selection</b></a>
    </div>

    <div id="chartSelectionDiv" class="accordion-body collapse in collapse-run" style="height:0px">
      <div class="row-fluid" style="padding: 10px;">
        <div class="span4">
          <h2>Trending</h2>

          <p>View your results trending broken out by race.</p>

          <p align="left"><a href="${createLink(controller: 'visualization', action: 'progression', params: [raceType: params?.raceType, 'user.id': params?.user?.id])}" class="btn btn-info">View Trending</a></p>
        </div>

        <div class="span4">
          <h2>Averages</h2>

          <p>View your averages by year</p>

          <p align="left"><a href="#" class="btn btn-info">View Averages</a></p></div>

        <div class="span4">
          <h2>PRs</h2>

          <p>View your PRs by year</p>

          <p align="left"><a href="#" class="btn btn-info">View PRs</a></p></div>
      </div>
    </div>
  </div>
</div>