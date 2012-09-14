<%@ page import="com.tgid.tri.race.RaceType" %>
<div class="row-fluid">
  <div class="span12">
    <div class="tabbable tabs">
      <ul class="nav nav-tabs">
        <li <g:if test="${request.forwardURI == createLink(controller:'visualization', action:'index')}"> class="active"</g:if>><a href="#tab0" data-toggle="tab">Overview</a></li>
        <li <g:if test="${request.forwardURI == createLink(controller:'visualization', action:'progression')}"> class="active"</g:if>><a href="#tab1" data-toggle="tab">Trending</a></li>
        <li <g:if test="${request.forwardURI == createLink(controller:'visualization', action:'averages')}"> class="active"</g:if>><a href="#tab2" data-toggle="tab">Averages</a></li>
        <li <g:if test="${request.forwardURI == createLink(controller:'visualization', action:'prs')}"> class="active"</g:if>><a href="#tab3" data-toggle="tab">PRs</a></li>
        <li <g:if test="${request.forwardURI.toString().toUpperCase().contains('BYSTATE')}"> class="active"</g:if>><a href="#tab4" data-toggle="tab">Comparison</a></li>
      </ul>

      <div class="tab-content">

        <div class="tab-pane <g:if test="${request.forwardURI == createLink(controller:'visualization', action:'index')}">active</g:if>" id="tab0">
          <p>Charts and other graphical tools to help represent your results and performance as an individual as well as compared to others is the heart of what TriCharts.com brings to the athletic community.</p>
          <p>Select from the chart types above to get started.</p>
        </div>

        <div class="tab-pane <g:if test="${request.forwardURI == createLink(controller:'visualization', action:'progression')}">active</g:if>" id="tab1">
          <p>View your result trending by distance.</p>
          <p>
            <tri:chartButtons raceTypes="${[RaceType.Running, RaceType.Triathlon]}" action="progression" title="Trending" />
          </p>
        </div>

        <div class="tab-pane <g:if test="${request.forwardURI == createLink(controller:'visualization', action:'averages')}">active</g:if>" id="tab2">
          <p>View your averages by year.</p>
          <p>
            <tri:chartButtons raceTypes="${[RaceType.Running, RaceType.Triathlon]}" action="averages" title="Averages" />
          </p>
        </div>

        <div class="tab-pane <g:if test="${request.forwardURI == createLink(controller:'visualization', action:'prs')}">active</g:if>" id="tab3">
          <p>View your event PRs by year.</p>
          <p>
            <tri:chartButtons raceTypes="${[RaceType.Running, RaceType.Triathlon]}" action="prs" title="PRs" />
          </p>
        </div>

        <div class="tab-pane <g:if test="${request.forwardURI.toString().toUpperCase().contains('BYSTATE')}">active</g:if>" id="tab4">
          <p>View global reports comparing yourself to others in your state or age group.</p>
          <p>
            <a href="${createLink(controller: 'visualization', action: 'runMileAverageByState', params: [raceType: RaceType.Running, 'user.id': params?.user?.id])}" class="btn btn-small ${request.forwardURI == createLink(controller: 'visualization', action: 'runMileAverageByState') ? 'btn-success' : ''}">Running Averages By State</a>
            <a href="${createLink(controller: 'visualization', action: 'triMileAverageByState', params: [raceType: RaceType.Triathlon, 'user.id': params?.user?.id])}" class="btn btn-small ${request.forwardURI == createLink(controller: 'visualization', action: 'triMileAverageByState') ? 'btn-success' : ''}">Triathlon Run Averages By State</a>
          </p>
        </div>
      </div>
    </div>
  </div>
</div>