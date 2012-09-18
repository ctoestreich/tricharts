<ul class="nav nav-list">
  <li class="nav-header">Admin</li>
  <li><a href="${createLink(controller: 'admin', action: 'index')}">Admin Dashboard</a></li>
  <li class="nav-header">Race Admin</li>
  <li><a href="${createLink(controller: 'admin', action: 'raceList')}">Approve Races</a></li>
  <li><a href="${createLink(controller: 'race', action: 'index')}">Races</a></li>
  <li><a href="${createLink(controller: 'raceSegment', action: 'index')}">Race Segments</a></li>
  <li><a href="${createLink(controller: 'segment', action: 'index')}">Segments</a></li>
  <li><a href="${createLink(controller: 'raceCategory', action: 'index')}">Race Categories</a></li>
  <li><a href="${createLink(controller: 'coursePattern', action: 'index')}">Course Patterns</a></li>
  %{--<li class="nav-header">Results Admin</li>--}%
  <li><a href="${createLink(controller: 'raceResult', action: 'index')}">Race Results</a></li>
  <li><a href="${createLink(controller: 'segmentResult', action: 'index')}">Segment Results</a></li>
  %{--<li class="nav-header">User Admin</li>--}%
  <li><a href="${createLink(controller: 'user', action: 'index')}">Users</a></li>
  <li><a href="${createLink(controller: 'userRole', action: 'index')}">User Role</a></li>
  <li><a href="${createLink(controller: 'racer', action: 'index')}">Racers</a></li>
  <li><a href="${createLink(controller: 'state', action: 'index')}">States</a></li>
  <li><a href="${createLink(controller: 'country', action: 'index')}">Countries</a></li>
  <li class="nav-header">Cache Admin</li>
  <li><a href="${createLink(controller: 'admin', action: 'clearRecordsCaches')}">Clear PR Caches</a></li>
  <li><a href="${createLink(controller: 'admin', action: 'clearChartCaches')}">Clear Chart Caches</a></li>
  <li><a href="${createLink(controller: 'admin', action: 'clearSiteCaches')}">Clear Site Caches</a></li>
  <li class="nav-header">Job Admin</li>
  <li><a href="${createLink(controller: 'admin', action: 'jobSettings')}">Update Job Settings</a></li>
  <li><a href="${createLink(controller: 'admin', action: 'runJob')}">Run Jobs</a></li>
  <li><a href="${createLink(controller: 'jobLog', action: 'index')}">Job Log</a></li>
  <li class="nav-header">Logs</li>
  <li><a href="${createLink(controller: 'admin', action: 'viewDebugLog')}">View Info Log</a></li>
  <li><a href="${createLink(controller: 'admin', action: 'viewErrorLog')}">View Error Log</a></li>
</ul>