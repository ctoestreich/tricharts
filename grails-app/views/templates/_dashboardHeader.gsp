<div class="row-fluid">
    <div class="span4"><h2>${sport}</h2></div>
    <div class="span8">
        <div class="btn-group-wrap-right">
            <div class="btn-group">
                <a class="btn"
                   href="${createLink(controller:'dashboard', action:'progression', params: [raceType:sport,'user.id':params?.user?.id])}"
                   id="view-progression"><i class="icon-camera"></i>&nbsp;${sport} Charts</a>
                <a class="btn"
                   href="${createLink(controller: 'dashboard', action: 'createResult', params: [raceType: sport,'user.id':params?.user?.id])}"
                   id="add-race"><i class="icon-tag"></i>&nbsp;Add Result</a>
            </div>
        </div>
    </div>
</div>