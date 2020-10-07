$(function () {
    $("#items").click(function () {
        $(".dynamic-container").children().remove();
        $.ajax({
            method: 'GET',
            url: "areas",
            timeout: 4000,
            dataType: "json",
            error: function (e) {
                console.error("test");
            },
            success: function (r) {
                refreshAreasList(r);
            }
        });
    });
});