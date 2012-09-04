<div class="row-fluid">
  <div class="span4 clsResultBig"><a class="accordion-toggle" data-toggle="collapse" href="#result-collapse-${result?.id}">${result?.race}</a></div>
  <div class="span2 clsResultBig"><span class="accordion-toggle">${result?.race?.raceCategoryType}</span></div>
  <div class="span2 clsResultBig"><span class="accordion-toggle">${result?.date?.format("M/dd/yyyy")}</span></div>
  <div class="span2 clsResultBig"><span class="accordion-toggle"><a href="javascript:void(0);" class="autopop" rel="popover" data-content="Age/Gender/Overall" data-original-title="Place">${result?.placeAgeGroup}/${result?.placeGender}/${result?.placeOverall}</a></span></div>
  <div class="span2 clsResultBig"><span class="accordion-toggle"><tri:formatDuration duration="${result?.duration}"/> <g:if test="${result?.race?.raceType == com.tgid.tri.race.RaceType.Running}"><tri:displayPace pace="${result.result?.pace}" /></g:if></span></div>
</div>