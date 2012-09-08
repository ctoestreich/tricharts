$(function () {
    tri.progression = (function ($) {
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
            tri.results.deleteRaceResultConfirmation();
        };
        return my;
    }(jQuery));


});