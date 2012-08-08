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

        my.deleteRaceResultConfirmation = function () {
            console.log('val',raceResultId);
            if (raceResultId) {
               $('#raceResultId').val(raceResultId);
               $('#raceResultDeleteForm').submit();
            }
        };
        return my;
    }(jQuery));
});