<%@ page import="com.tgid.tri.race.SegmentType; org.joda.time.Duration; com.tgid.tri.race.RaceCategoryType" %>
<%
  def keys = ["${RaceCategoryType.Sprint}_${SegmentType.Swim}": "Sprint Swim",
          "${RaceCategoryType.Sprint}_${SegmentType.Bike}": "Sprint Bike",
          "${RaceCategoryType.Sprint}_${SegmentType.Run}": "Sprint Run",
          "${RaceCategoryType.Olympic}_${SegmentType.Swim}": "Olympic Swim",
          "${RaceCategoryType.Olympic}_${SegmentType.Bike}": "Olympic Bike",
          "${RaceCategoryType.Olympic}_${SegmentType.Run}": "Olympic Run"]
%>

<div class="span12">
  <div class="overview">
    <ul>
      <g:each in="${keys}" var="key">
      <li>
        <h5>${key.value}</h5>
        <h3><a href="${createLink(controller:'results', action:'index')}" class="tooltips" rel="popover" title="${data?.get("'${key.key}'")?.raceResult?.race}"
               data-content="">${data?.get("'${key.key}'")?.pace}&nbsp;</a></h3>
      </li>
     </g:each>
    </ul>
  </div>
</div>

%{--<div class="span2"><div id="${key.key}" class="personalBestDiv">--}%
%{--<div class="btn-group-wrap">--}%
%{--<h5></h5>--}%
%{--</div>--}%
%{--</div></div>--}%

<script type="text/javascript">
  $(function () {
    $(".tooltips").tooltip({title:'Personal Record', content:""});
  });
</script>

