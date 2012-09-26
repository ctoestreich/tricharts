var app = {
    initialize:function () {
        $('.autopop').popover();
    },
    changeUser:function () {
        if($('#userForm').size() > 0) {
            $('#userForm').submit();
        }
    },
    setChart:function (chart, name, categories, data, color) {
        console.log('in method');
        chart.xAxis[0].setCategories(categories, false);
        chart.series[1].remove(false);
        chart.addSeries({
                            name:name,
                            data:data,
                            color:color || 'white'
                        }, false);
        chart.redraw();
    },
    getGetOrdinal:function (n) {
        var s = ["th", "st", "nd", "rd"], v = n % 100;
        return n + (s[(v - 20) % 10] || s[v] || s[0]);
    },
    removeLoadingClasses: function(o){
        if(o){
            o.removeClass('loading-large loading-small');
        }
    }
};

$(function () {
    app.initialize();
});