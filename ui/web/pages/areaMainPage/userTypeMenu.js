var itemIdToItemPrice = new Map();
bootstrap_alert = function() {}
bootstrap_alert.warning = function(message) {
    $('#alert_placeholder').html('<div class="alert addStoreAlert"><a class="close addStoreAlertClose" data-dismiss="alert">x</a><span>'+message+'</span></div>')
}


function displayItemsOption(items) {

    $(".dynamic-container").append(
        "<section class=\"our-webcoderskull padding-lg\">\n" +
        "    <div class=\"container items-container\">\n" +
        "        <div class=\"row heading heading-icon\">\n" +
        "            <p class=\"addStoreItemsHeader\">Items</p>\n" +
        "        </div>\n" +
        "        <ul class=\"row items-list addStoreItemsList\">\n" +
        "        </ul>\n" +
        "    </div>\n" +
        "</section>");


    $.each(items.itemIdToItemInfo || [], function(index, singleItem) {

        var itemImageUrl = "../../common/images/itemIcon.png";

        $(".items-list").append(
            "<li class=\"col-12 col-md-6 col-lg-3 item-card\">\n" +
            "       <div class=\"cnt-block equal-hight addStoreItemCard\">\n" +
            "            <figure><img src=" + itemImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
            "             <h3 class=\"item-name\">" + singleItem.itemName + "</h3>" +
            "           <p id=\"itemId\" class=\"addStoreSingleItem\">Item Id: " + singleItem.itemId + " </p>\n" +
            "           <p class=\"addStoreSingleItem\">Purchase Category: " + singleItem.purchaseCategory + "</p>\n" +
            "<form id=\"add-store-item-form-" + singleItem.itemId + "\">" +
            "<input type=\"number\" class=\"form-control addStoreItemPrice\" id=\"itemPrice" + singleItem.itemId + "\" placeholder=\"Price\" required>" +
            " <button id=\"addItem" + singleItem.itemId + "\" type=\"submit\" class=\"btn btn-primary mb-2 addStoreItemSubmit\">Add Item</button>\n" +
            "</form>" +
            "       </div>" +
            " </li>\n");
        $("#add-store-item-form-" + singleItem.itemId).submit(function () {
            var itemPrice = $("#itemPrice" + singleItem.itemId).val();
                itemIdToItemPrice.set(singleItem.itemId, itemPrice); //TODO: empty when back clicked
             return false;
        });
    });
}

function getItemsOption() {
    $.ajax({
        method: 'GET',
        url: "itemsAndStores",
        data: "actionType=items",
        timeout: 4000,
        dataType: "json",
        error: function (e) {
            console.error("test");
        },
        success: function (r) {
            displayItemsOption(r);
        }
    });
}

function makeNewStoreForm(areas) {

    $(".dynamic-container").append(
        "<p class=\"addStoreHeader\">Add New Store</p>\n" +
        "<form id=\"newStoreForm\" action=\"\" class=\"form-inline\">" +
        "  <select id=\"areasDropdown\" class=\"addStoreFormControl\" required>\n" +
        "<option value=\"\">Choose Area</option>\n" +
        "  </select>\n" +
        "</form>");
    $.each(areas || [], function(index, area) {
        $("#areasDropdown").append(
            "<option>" + area.areaName + "</option>\n");
    });
    $("#newStoreForm").append(
        "<input type=\"text\" class=\"form-control addStoreFormControl\" id=\"storeName\" placeholder=\"Store Name\" required>" +
        "<input type=\"number\" class=\"form-control addStoreFormControl\" id=\"storeId\" placeholder=\"Store Id\" required>");

    $("#newStoreForm").append(
        "<input type=\"text\" class=\"form-control addStoreFormControl\" id=\"xStoreLocation\" placeholder=\"X Coordinate\" required pattern=\"^(50|[1-4]?[0-9])$\">" +
        "<input type=\"text\" class=\"form-control addStoreFormControl\" id=\"yStoreLocation\" placeholder=\"Y Coordinate\" required pattern=\"^(50|[1-4]?[0-9])$\">");
    $("#newStoreForm").append(
        "<input type=\"number\" class=\"form-control addStoreFormControl\" id=\"storePpk\" placeholder=\"PPK\" required>");
    $("#newStoreForm").append(
        "<button type=\"submit\" class=\"btn addStoreFormControl\" id=\"addStoreSubmit\" required> Add Store </button>" +
        "<div id = \"alert_placeholder\"></div>\n");
    $("#newStoreForm").submit(function () {
        if(itemIdToItemPrice.size === 0) {
            $('#addStoreSubmit').on('click', function() {
                bootstrap_alert.warning('You Must Choose At Least One Item  ');
            });
            $('#addStoreSubmit').click();
        } else {

            var area = $("#areasDropdown").val();
            var storeName =  $("#storeName").val();
            var storeId =  $("#storeId").val();
            var xLocation =  $("#xStoreLocation").val();
            var yLocation =  $("#yStoreLocation").val();
            var ppk =  $("#storePpk").val();
            var itemsListJsonString = JSON.stringify(Array.from(itemIdToItemPrice));


            $.ajax({
                method: 'POST',
                data: "areaKey=" + area + "&storeNameKey=" + storeName + "&storeIdKey=" + storeId + "&xLocationKey=" + xLocation + "&yLocationKey=" + yLocation + "&ppkKey=" + ppk + "&itemsListKey=" + itemsListJsonString + "&actionKey=addStore",
                url: "shopOwner",
                error: function (e) {
                    bootstrap_alert.warning(e);
                },
                success: function (r) {
                    bootstrap_alert.warning(r);
                }
            });
        }
        return false;
    });
    getItemsOption();
}

function getAreasInfo() {
    $.ajax({
        method: 'GET',
        url: "../settingPage/areas",
        timeout: 4000,
        dataType: "json",
        error: function (e) {
            console.error("test");
        },
        success: function (r) {
            makeNewStoreForm(r);
        }
    });
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
            } else if(r.indexOf("SHOP_OWNER") > -1) {
                $("#menu-items").append(
                    "<li id=\"shopOwnerOrdersHistory\" class=\"main-menu-item\">Orders History</li>\n");
                $("#menu-items").append(
                    "<li id=\"showFeedback\" class=\"main-menu-item\">Show Feedback</li>\n");
                $("#menu-items").append(
                    "<li id=\"openNewStore\" class=\"main-menu-item\">Open New Store</li>\n");
                $("#openNewStore").click(function () {
                    getAreasInfo();
                    /*makeNewStoreForm();*/

                });
            }
        }
    });
}

$(function () {
    $(".dynamic-container").empty();
    getUserType();
});











