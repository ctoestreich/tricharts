<div class="row-fluid">
  <div class="span3 clsResultBig"><a class="accordion-toggle" data-toggle="collapse" href="#result-collapse-${result?.id}">${result?.race}</a></div>
  <div class="span3 clsResultBig"><span class="accordion-toggle">${result?.race?.raceCategoryType} ${result?.race?.distance ? ("${result?.race?.distance} ${result?.race?.distanceType}") : ''}</span></div>
  <div class="span3 clsResultBig"><span class="accordion-toggle">${result?.date?.format("M/dd/yyyy")}</span></div>
  <div class="span3 clsResultBig"><span class="accordion-toggle"><tri:formatDuration duration="${result?.duration}"/></span></div>
</div>