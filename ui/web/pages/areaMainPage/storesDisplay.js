$(function () {
    $("#stores").click(function () {
        $.ajax({
            method: 'GET',
            url: "itemsAndStores",
            data: "actionType=stores",
            timeout: 4000,
            dataType: "json",
            error: function (e) {
                console.error("test");
            },
            success: function (r) {
                clearOrderData();
                displayStores(r);
            }
        });
    });
});


function displayStores(stores) {
    $(".dynamic-container").children().remove();
    $(".dynamic-container").append(
        "<section class=\"our-webcoderskull padding-lg\">\n" +
        "    <div class=\"container stores-container\">\n" +
        "        <div class=\"row heading heading-icon\">\n" +
        "            <h2 class=\"stores-header\">Stores</h2>\n" +
        "        </div>\n" +
        "        <ul class=\"row stores-list\">\n" +
        "        </ul>\n" +
        "    </div>\n" +
        "</section>");


    $.each(stores || [], function(index, singleStore) {

        var storeImageUrl = "../../common/images/storeIcon.png";
        var itemImageUrl = "../../common/images/itemIcon.png";

        $(".stores-list").append(
            "<li class=\"col-12 col-md-6 col-lg-3 store-card\">\n" +
            "       <div class=\"cnt-block equal-hight store-info-block\">\n" +
            "            <figure><img src=" + storeImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
            "             <h3 class=\"store-name\">" + singleStore.storeName + " " +
            "                 <small class=\"text-muted\"> Store Id: " + singleStore.storeId +
            "                 </small>" +
            "             </h3>" +
                    "     <div class=\"row store-information-row\">" +
                    "         <div class=\"area-list-store col-sm-2\">Area Owner: " + singleStore.ownerName + "</div>\n" +
                    "         <div class=\"area-list-store col-sm-2\">Location: " + singleStore.location.x + "," + singleStore.location.y + "</div>\n" +
                    "         <div class=\"area-list-store col-sm-2\">Number Of Orders: " + singleStore.numOfOrders + "</div>\n" +
                    "         <div class=\"area-list-store col-sm-2\">Purchased Items Cost: " + singleStore.purchasedItemsCost.toFixed(2) + "</div>\n" +
                    "         <div class=\"area-list-store col-sm-2\">PPK: " + singleStore.deliveryPPK + "</div>\n" +
                    "         <div class=\"area-list-store col-sm-2\">Total Delivery Payment: " + singleStore.totalDeliveryPayment.toFixed(2) + "</div>\n" +
            "             </div>" +
                    "<section class=\"our-webcoderskull padding-lg\">\n" +
                    "    <div class=\"container store-item-container\">\n" +
                    "        <div class=\"row heading heading-icon \">\n" +
                    "            <h2 class=\"store-items-header\">Items</h2>\n" +
                    "        </div>\n" +
                    "        <ul id=\"store-" + singleStore.storeId + "\"  class=\"row store-items-list\">\n" +
                    "        </ul>\n" +
                    "    </div>\n" +
                    "</section>" +
            "     </div>" +
            "</li>\n");
        $.each(singleStore.storeItems || [], function(index, singleStoreItem) {
            $("#store-" + singleStore.storeId).append(
                "           <li class=\"col-12 col-md-6 col-lg-3 store-item-card\">\n" +
                "           <figure class=\"image-figure\"><img src=" + itemImageUrl + " class=\"img-responsive store-item-image\"  alt=\"\"></figure>\n" +
                "           <h3 class=\"item-name\">" + singleStoreItem.itemName + "</h3>" +
                "           <p class=\"area-list-item\">Item Id: " + singleStoreItem.itemId + " </p>\n" +
                "           <p class=\"area-list-item\">Purchase Category: " + singleStoreItem.purchaseCategory + "</p>\n" +
                "           <p class=\"area-list-item\">Price: " + singleStoreItem.itemPrice + "</p>\n" +
                "           <p class=\"area-list-item\">Amount Sold: " + singleStoreItem.amountSold + "</p>\n" +
                "           </li>\n");
        });
    });
}
