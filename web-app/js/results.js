$(function () {
    tri.results = (function ($) {
        var raceResultId;
        var my = {};
        my.toggleTransitions = function () {
            $('.T1, .T2').toggle();
        };

        my.deleteRaceResult = function (id) {
            raceResultId = id;
            $('#deleteConfirmation').modal('toggle');
        };

        my.editRaceResult = function(id){
            $('#raceResultId').val(id);
            $('#raceResultEdit').val("true");
            $('#modifyRaceResultsForm').submit();
        };

        my.deleteRaceResultConfirmation = function () {
            console.log('val',raceResultId);
            if (raceResultId) {
               $('#raceResultId').val(raceResultId);
                $('#raceResultEdit').val("false");
               $('#modifyRaceResultsForm').submit();
            }
        };
        return my;
    }(jQuery));
});