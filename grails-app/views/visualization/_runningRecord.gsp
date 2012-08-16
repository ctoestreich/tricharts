<%@ page import="org.joda.time.Duration; com.tgid.tri.race.RaceCategoryType" %>
<div class="span2"><div id="mileRecord" class="personalBestDiv">
    <div class="btn-group-wrap" >
        <h1><a href="javascript:void(0);" class="tooltips" rel="popover" title="${data?.get("'${RaceCategoryType.OneMile}'")?.race}" data-content=""><tri:formatDuration duration="${data?.get("'${RaceCategoryType.OneMile}'")?.duration}" /></a></h1>
        <h4>1 Mile PR</h4>
    </div>
</div></div>

<div class="span2"><div id="fivekRecord" class="personalBestDiv">
    <div class="btn-group-wrap">
        <h1><a href="javascript:void(0);" class="tooltips" rel="popover" title="${data?.get("'${RaceCategoryType.FiveKilometer}'")?.race} @ ${data?.get("'${RaceCategoryType.FiveKilometer}'")?.result?.pace}" data-content=""><tri:formatDuration duration="${data?.get("'${RaceCategoryType.FiveKilometer}'")?.duration}" /></a></h1>
        <h4>5k PR  <tri:displayPace pace="${data?.get("'${RaceCategoryType.FiveKilometer}'")?.result?.pace}"/></h4>
    </div>
</div></div>

<div class="span2"><div id="halfRecord" class="personalBestDiv">
    <div class="btn-group-wrap">
        <h2><a href="javascript:void(0);" class="tooltips" rel="popover" title="${data?.get("'${RaceCategoryType.TenKilometer}'")?.race}" data-content=""><tri:formatDuration duration="${data?.get("'${RaceCategoryType.TenKilometer}'")?.duration}" /></a></h2>
        <h4>10k PR  <tri:displayPace pace="${data?.get("'${RaceCategoryType.TenKilometer}'")?.result?.pace}"/></h4>
    </div>
</div></div>

<div class="span2"><div id="halfRecord" class="personalBestDiv">
    <div class="btn-group-wrap">
        <h2><a href="javascript:void(0);" class="tooltips" rel="popover" title="${data?.get("'${RaceCategoryType.TenMile}'")?.race}" data-content=""><tri:formatDuration duration="${data?.get("'${RaceCategoryType.TenMile}'")?.duration}" /></a></h2>
        <h4>10 Mile PR  <tri:displayPace pace="${data?.get("'${RaceCategoryType.TenMile}'")?.result?.pace}"/></h4>
    </div>
</div></div>

<div class="span2"><div id="halfRecord" class="personalBestDiv">
    <div class="btn-group-wrap">
        <h2><a href="javascript:void(0);" class="tooltips" rel="popover" title="${data?.get("'${RaceCategoryType.HalfMarathon}'")?.race}" data-content=""><tri:formatDuration duration="${data?.get("'${RaceCategoryType.HalfMarathon}'", Duration.standardSeconds(0))}" /></a></h2>
        <h4>13.1 PR <tri:displayPace pace="${data?.get("'${RaceCategoryType.HalfMarathon}'")?.result?.pace}"/></h4>
    </div>
</div></div>

<div class="span2"><div id="halfRecord" class="personalBestDiv">
    <div class="btn-group-wrap">
        <h2><a href="javascript:void(0);" class="tooltips" rel="popover" title="${data?.get("'${RaceCategoryType.Marathon}'")?.race}" data-content=""><tri:formatDuration duration="${data?.get("'${RaceCategoryType.Marathon}'", Duration.standardSeconds(0))}" /></a></h2>
        <h4>26.2 PR <tri:displayPace pace="${data?.get("'${RaceCategoryType.Marathon}'")?.result?.pace}"/></h4>
    </div>
</div></div>

<script type="text/javascript">
    $(function(){
        $(".tooltips").tooltip({title: 'Personal Record', content: ""});
    });
</script>

