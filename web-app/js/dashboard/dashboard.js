var app = {
    initialize:function () {
        //anything common to pages
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