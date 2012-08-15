<div class="row-fluid">
    <div class="btn-group-wrap-right" style="margin:10px;">
        <div class="btn-group">
            <a class="btn btn-info"
               href="#"
               onclick="tri.results.editRaceResult(${result?.id})"
               id="editRaceResults"><i class="icon-edit"></i>Edit</a>
        </div>

        <div class="btn-group">
            <a class="btn btn-danger"
               href="#"
               onclick="tri.results.deleteRaceResult(${result?.id})"
               id="deleteRaceResults"><i class="icon-remove-circle"></i>Delete</a>
        </div>
    </div>
</div>