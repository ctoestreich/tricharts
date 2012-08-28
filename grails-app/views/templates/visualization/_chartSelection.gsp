<div class="row-fluid" style="padding: 10px;">
  <div class="span4">
    <h3>${params?.raceType} Trending</h3>

    <p>View your results trending broken out by race.</p>

    <p align="left"><a href="${createLink(controller: 'visualization', action: 'progression', params: [raceType: params?.raceType, 'user.id': params?.user?.id])}" class="btn btn-info">View Trending</a></p>
  </div>

  <div class="span4">
    <h3>${params?.raceType} Averages</h3>

    <p>View your averages by year.</p>

    <p align="left"><a href="${createLink(controller: 'visualization', action: 'averages', params: [raceType: params?.raceType, 'user.id': params?.user?.id])}" class="btn btn-info">View Averages</a></p></div>

  <div class="span4">
    <h3>${params?.raceType} All</h3>

    <p>View all your race segment splits.</p>

    <p align="left"><a href="#" class="btn btn-info">View All</a></p></div>
</div>