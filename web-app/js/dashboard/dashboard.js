var app = {
    initialize:function () {
        $('.autopop').popover();
    },
    changeUser:function () {
        if($('#userForm').size() > 0) {
            $('#userForm').submit();
        }
    }
};

$(function () {
    app.initialize();
});