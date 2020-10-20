
bootstrap_alert = function() {}
bootstrap_alert.warning = function(message, color) {
    $('#alert_placeholder').html('<div id="addStoreAlert" class="alert addStoreAlert" style="color:' + color + "!important;\"" + '><a class="close addStoreAlertClose" data-dismiss="alert">x</a><span>'+message+'</span></div>')
}

function getUserType() {
    $.ajax({
        method: 'GET',
        url: "../settingPage/uploadFile",
        timeout: 4000,
        error: function (e) {
        },
        success: function (r) {
            if(r.indexOf("CUSTOMER") > -1) {
                $("#menu-items").append(
                    "<li id=\"makeOrder\" class=\"main-menu-item\">Make Order</li>\n");
                $("#menu-items").append(
                    "<li id=\"customerOrdersHistory\" class=\"main-menu-item\">Orders History</li>\n");
                $("#makeOrder").click(function () {
                    getNewOrderOptions();
                });
            } else if(r.indexOf("SHOP_OWNER") > -1) {
                $("#menu-items").append(
                    "<li id=\"shopOwnerOrdersHistory\" class=\"main-menu-item\">Orders History</li>\n");
                $("#menu-items").append(
                    "<li id=\"showFeedback\" class=\"main-menu-item\">Show Feedback</li>\n");
                $("#menu-items").append(
                    "<li id=\"openNewStore\" class=\"main-menu-item\">Open New Store</li>\n");
                $("#openNewStore").click(function () {
                    getAreasInfo();
                });

                $("#showFeedback").click(function () {
                    getShopOwnerFeedback();
                });

            }
        }
    });
}

$(function () {
    getUserType();
});











