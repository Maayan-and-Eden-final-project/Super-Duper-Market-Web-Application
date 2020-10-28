$(function() {

    $("#goToSignUpPage").click(function () {
        $.ajax({
            url: "pages/login/login",
            success: function(data) {
            console.log("suc");
                window.location.replace("pages/login/login");
            },
            error: function(error) {
                console.log("err");
            }
        });
    });
});