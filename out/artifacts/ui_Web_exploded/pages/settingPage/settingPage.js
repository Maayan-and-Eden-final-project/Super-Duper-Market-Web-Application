$(function () {
    $("#usersTab").click(function () {
        clearInterval(accountTableInterval);
        clearInterval(areasInterval);
    });
    $("#uploadFileTab").click(function () {
        clearInterval(accountTableInterval);
        clearInterval(areasInterval);
        clearInterval(usersInterval);
    });
    $("#areasTab").click(function () {
        clearInterval(accountTableInterval);
        clearInterval(usersInterval);
    });
    $("#accountTab").click(function () {
        clearInterval(areasInterval);
        clearInterval(usersInterval);
    });
});