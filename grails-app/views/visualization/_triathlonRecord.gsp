<%@ page import="com.tgid.tri.race.SegmentType; org.joda.time.Duration; com.tgid.tri.race.RaceCategoryType" %>
<%
    def keys = ["${RaceCategoryType.Sprint}_${SegmentType.Swim}": "Sprint Swim",
            "${RaceCategoryType.Sprint}_${SegmentType.Bike}": "Sprint Bike",
            "${RaceCategoryType.Sprint}_${SegmentType.Run}": "Sprint Run",
            "${RaceCategoryType.Olympic}_${SegmentType.Swim}": "Olympic Swim",
            "${RaceCategoryType.Olympic}_${SegmentType.Bike}": "Olympic Bike",
            "${RaceCategoryType.Olympic}_${SegmentType.Run}": "Olympic Run"]
%>

<g:each in="${keys}" var="key">
    <div class="span2"><div id="${key.key}" class="personalBestDiv">
        <div class="btn-group-wrap">
            <h3><a href="#" class="tooltips" rel="popover" title="${data?.get("'${key.key}'")?.raceResult?.race}"
                   data-content="">${data?.get("'${key.key}'")?.pace}&nbsp;</a></h3>
            <h4>${key.value}</h4>
        </div>
    </div></div>
</g:each>



%{--<div class="span2"><div id="fivekRecord" class="personalBestDiv">--}%
%{--<div class="btn-group-wrap">--}%
%{--<h1><a href="#" class="tooltips" rel="popover" title="${data?.get("'${RaceCategoryType.Sprint}_${SegmentType.Bike}'")?.pace}" data-content=""><tri:formatDuration duration="${data?.get("'${RaceCategoryType.FiveKilometer}'")?.duration}" />&nbsp;</a></h1>--}%
%{--<h4>Sprint Bike</h4>--}%
%{--</div>--}%
%{--</div></div>--}%

%{--<div class="span2"><div id="halfRecord" class="personalBestDiv">--}%
%{--<div class="btn-group-wrap">--}%
%{--<h2><a href="#" class="tooltips" rel="popover" title="${data?.get("'${RaceCategoryType.Sprint}_${SegmentType.Run}'")?.pace}" data-content=""><tri:formatDuration duration="${data?.get("'${RaceCategoryType.TenKilometer}'")?.duration}" />&nbsp;</a></h2>--}%
%{--<h4>Sprint Run</h4>--}%
%{--</div>--}%
%{--</div></div>--}%

%{--<div class="span2"><div id="halfRecord" class="personalBestDiv">--}%
%{--<div class="btn-group-wrap">--}%
%{--<h2><a href="#" class="tooltips" rel="popover" title="${data?.get("'${RaceCategoryType.Olympic}_${SegmentType.Swim}'")?.pace}" data-content=""><tri:formatDuration duration="${data?.get("'${RaceCategoryType.TenMile}'")?.duration}" />&nbsp;</a></h2>--}%
%{--<h4>Olympic Swim</h4>--}%
%{--</div>--}%
%{--</div></div>--}%

%{--<div class="span2"><div id="halfRecord" class="personalBestDiv">--}%
%{--<div class="btn-group-wrap">--}%
%{--<h2><a href="#" class="tooltips" rel="popover" title="${data?.get("'${RaceCategoryType.Olympic}_${SegmentType.Bike}'")?.pace}" data-content=""><tri:formatDuration duration="${data?.get("'${RaceCategoryType.HalfMarathon}'")?.duration}" />&nbsp;</a></h2>--}%
%{--<h4>Olympic Bike</h4>--}%
%{--</div>--}%
%{--</div></div>--}%

%{--<div class="span2"><div id="halfRecord" class="personalBestDiv">--}%
%{--<div class="btn-group-wrap">--}%
%{--<h2><a href="#" class="tooltips" rel="popover" title="${data?.get("'${RaceCategoryType.Olympic}_${SegmentType.Run}'")?.pace}" data-content=""><tri:formatDuration duration="${data?.get("'${RaceCategoryType.HalfMarathon}'")?.duration}" />&nbsp;</a></h2>--}%
%{--<h4>Olympic Run</h4>--}%
%{--</div>--}%
%{--</div></div>--}%

<script type="text/javascript">
    $(function () {
        $(".tooltips").tooltip({title:'Personal Record', content:""});
    });
</script>

