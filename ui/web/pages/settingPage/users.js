$(function () {
    $("#usersTab").click(function () {
        $(".dynamic-container").children().remove();
        $.ajax({
            method: 'GET',
            url: "users",
            timeout: 4000,
            dataType: "json",
            error: function (e) {
            },
            success: function (r) {
                refreshUsersList(r);
            }
        });
    });
    $("#usersTab").click();
});

/*
             data will arrive in the next form:
             {

                "SingleUser": [
                    {
                        "userName":"Moshe",
                        "userType":"CUSTOMER",
                        "userId":1485548397514,
                        "areas": [
                            { "Hasharon" },
                            { "Galil Maarvi" }
                                 ]
                    }
                ]
             }
             */
function refreshUsersList(users) {
    $(".dynamic-container").append(
    "<section class=\"our-webcoderskull padding-lg\">\n" +
    "    <div class=\"container\">\n" +
    "        <div class=\"row heading heading-icon\">\n" +
    "            <h2 class=\"users-header\">Users</h2>\n" +
    "        </div>\n" +
    "        <ul class=\"row users-list\">\n" +
    "        </ul>\n" +
    "    </div>\n" +
    "</section>");

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(users || [], function(index, singleUser) {

        var imageUrl;
        var userType;
        if(singleUser.userType.indexOf("CUSTOMER") > -1 ) {
            imageUrl = "../../common/images/soccer-player.png";
            userType = "Customer";
        } else if(singleUser.userType.indexOf("SHOP_OWNER") > -1 ) {
            imageUrl = "../../common/images/shop_owner.png";
            userType = "Shop Owner";
        }

        $(".users-list").append(
            "<li class=\"col-12 col-md-6 col-lg-3\">\n" +
        "                <div class=\"cnt-block equal-hight\" style=\"height: 321px;\">\n" +
        "                    <figure><img src=" + imageUrl + " class=\"img-responsive rounded-circle\"  alt=\"\"></figure>\n" +
        "                    <p class=\"user-name\">" + singleUser.userName + "</p> <!--userName-->\n" +
        "                    <p class=\"user-type\">" + userType + "</p> <!--type-->\n" +
        "                </div>\n" +
        "            </li>\n");

    });
}

