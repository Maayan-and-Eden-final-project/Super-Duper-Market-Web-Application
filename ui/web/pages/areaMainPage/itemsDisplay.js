$(function () {
    $("#items").click(function () {
        $(".dynamic-container").children().remove();
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
                displayItems(r);
            }
        });
    });
});


function displayItems(items) {
    $("#dynamic-container").append(
        "<section class=\"our-webcoderskull padding-lg\">\n" +
        "    <div class=\"container\">\n" +
        "        <div class=\"row heading heading-icon\">\n" +
        "            <h2>Items</h2>\n" +
        "        </div>\n" +
        "        <ul class=\"row items-list\">\n" +
        "        </ul>\n" +
        "    </div>\n" +
        "</section>");


    $.each(items.itemIdToItemInfo || [], function(index, singleItem) {

             var itemImageUrl = "../../common/images/itemIcon.png";

        $(".items-list").append(
            "<li class=\"col-12 col-md-6 col-lg-3\">\n" +
            "       <div class=\"cnt-block equal-hight\">\n" +
            "            <figure><img src=" + itemImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
            "             <h3 class=\"item-name\">" + singleItem.itemName + "</h3>" +
            "           <p class=\"area-list-item\">Item Id:" + singleItem.itemId + " </p>\n" +
            "           <p class=\"area-list-item\">Purchase Category:" + singleItem.purchaseCategory + "</p>\n" +
            "           <p class=\"area-list-item\">Number Of Selling Stores:" + singleItem.numOfSellingStores + "</p>\n" +
            "           <p class=\"area-list-item\">Average Item Cost:" + singleItem.avgPrice + "</p>\n" +
            "           <p class=\"area-list-item\">Total Item Purchases:" + singleItem.totalPurchases + "</p>\n" +
            "</div>" +
    "            </li>\n");
    });
}