<div class="row-fluid">
    <div class="span4"><h2>${sport}</h2></div>

    <div class="span8">
        <div class="btn-group-wrap-right">
            <div class="btn-group">
                %{--<a class="btn"--}%
                   %{--href="javascript:app.${sport.toLowerCase()}Charts()"--}%
                   %{--id="dashboard-charts"><i class="icon-camera"></i>Common Charts</a>--}%
                <a class="btn"
                   href="${createLink(controller: 'dashboard', action: 'createResult', params: [type: sport])}"
                   id="add-race"><i class="icon-tag"></i>Add Result</a>
            </div>
        </div>
    </div>
</div>