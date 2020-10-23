var itemIdToItemPrice = new Map();

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
            "<input type=\"number\" min=0 class=\"form-control addStoreItemPrice\" id=\"itemPrice" + singleItem.itemId + "\" placeholder=\"Price\" required>" +
            " <button id=\"addItem" + singleItem.itemId + "\" type=\"submit\" class=\"btn btn-primary mb-2 addStoreItemSubmit\">Add Item</button>\n" +
            "</form>" +
            "        <div class=\"popup add-item-popup\" >\n" +
            "            <span class=\"popuptext\" id=\"myPopup-" + index + "\" ></span>" +
            "        </div> \n" +
            "       </div>" +
            " </li>\n");
        $("#add-store-item-form-" + singleItem.itemId).submit(function () {
            var itemPrice = $("#itemPrice" + singleItem.itemId).val();
            itemIdToItemPrice.set(singleItem.itemId, itemPrice); //TODO: empty when back clicked
            $("#itemPrice" + singleItem.itemId).val("");
            var popup = document.getElementById("myPopup-" + index);
            displayPopup("Item Successfully Added",popup);
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
    $(".dynamic-container").empty();
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
        "<input type=\"number\" min=0 class=\"form-control addStoreFormControl\" id=\"storeId\" placeholder=\"Store Id\" required>");

    $("#newStoreForm").append(
        "<input type=\"text\" class=\"form-control addStoreFormControl\" id=\"xStoreLocation\" placeholder=\"X Coordinate\" required pattern=\"^(50|[1-4]?[0-9])$\">" +
        "<input type=\"text\" class=\"form-control addStoreFormControl\" id=\"yStoreLocation\" placeholder=\"Y Coordinate\" required pattern=\"^(50|[1-4]?[0-9])$\">");
    $("#newStoreForm").append(
        "<input type=\"number\" min=0 class=\"form-control addStoreFormControl\" id=\"storePpk\" placeholder=\"PPK\" required>");
    $("#newStoreForm").append(
        "<button type=\"submit\" class=\"btn addStoreFormControl\" id=\"addStoreSubmit\" required> Add Store </button>" +
        "<div id = \"alert_placeholder\"></div>\n");
    $("#newStoreForm").submit(function () {
        if(itemIdToItemPrice.size === 0) {
            $('#addStoreSubmit').on('click', function() {
                bootstrap_alert.warning('You Must Choose At Least One Item  ',"red");
            });
            $('#addStoreSubmit').click();
        } else {

            var area = $("#areasDropdown").val();
            var storeName = $("#storeName").val();
            var storeId = $("#storeId").val();
            var xLocation = $("#xStoreLocation").val();
            var yLocation = $("#yStoreLocation").val();
            var ppk = $("#storePpk").val();
            var itemsListJsonString = JSON.stringify(Array.from(itemIdToItemPrice));

            $.ajax({
                method: 'POST',
                data: "areaKey=" + area + "&storeNameKey=" + storeName + "&storeIdKey=" + storeId + "&xLocationKey=" + xLocation + "&yLocationKey=" + yLocation + "&ppkKey=" + ppk + "&itemsListKey=" + itemsListJsonString + "&actionType=addStore",
                url: "shopOwner",
                error: function (jqXHR) {
                    bootstrap_alert.warning(jqXHR.responseText,"red");
                },
                success: function (r) {
                    bootstrap_alert.warning(r,"green");
                    itemIdToItemPrice.clear();
                    $("#areasDropdown").val("");
                    $("#storeName").val("");
                    $("#storeId").val("");
                    $("#xStoreLocation").val("");
                    $("#yStoreLocation").val("");
                    $("#storePpk").val("");
                    $(".addStoreItemPrice").val("");
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