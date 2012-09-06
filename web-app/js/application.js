if(!window.console) console = {log:function () {}};

if (typeof jQuery !== 'undefined') {
    (function ($) {
        $('#spinner').ajaxStart(function () {
            $(this).fadeIn();
        }).ajaxStop(function () {
                $(this).fadeOut();
            });
    })(jQuery);
}

var tri = (function () {
    var my = {},
        privateVariable = 1;

    my.moduleProperty = 1;
    my.initialize = function () {
        console.log('initilized app');
    };

    return my;
}());

$(function () {
    tri.initialize();
});
