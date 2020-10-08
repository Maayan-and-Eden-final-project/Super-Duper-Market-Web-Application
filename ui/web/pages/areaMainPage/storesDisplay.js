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
                displayStores(r);
            }
        });
    });
});


function displayStores(stores) {
    $(".dynamic-container").children().remove();
    $(".dynamic-container").append(
        "<section class=\"our-webcoderskull padding-lg\">\n" +
        "    <div class=\"container\">\n" +
        "        <div class=\"row heading heading-icon\">\n" +
        "            <h2>Stores</h2>\n" +
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
            "       <div class=\"cnt-block equal-hight\">\n" +
            "            <figure><img src=" + storeImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
            "             <h3 class=\"store-name\">" + singleStore.storeName + "</h3>" +
            "           <p class=\"area-list-store\">Store Id: " + singleStore.storeId + " </p>\n" +
            "           <p class=\"area-list-store\">Area Owner: " + singleStore.ownerName + "</p>\n" +
            "           <p class=\"area-list-store\">Location: " + singleStore.location.x + "," + singleStore.location.y + "</p>\n" +
            "           <p class=\"area-list-store\">Number Of Orders: " + singleStore.numOfOrders + "</p>\n" +
            "           <p class=\"area-list-store\">Purchased Items Cost: " + singleStore.purchasedItemsCost + "</p>\n" +
            "           <p class=\"area-list-store\">PPK: " + singleStore.deliveryPPK + "</p>\n" +
            "           <p class=\"area-list-store\">Total Delivery Payment: " + singleStore.totalDeliveryPayment + "</p>\n" +

            "<section class=\"our-webcoderskull padding-lg\">\n" +
            "    <div class=\"container\">\n" +
            "        <div class=\"row heading heading-icon\">\n" +
            "            <h2>Items</h2>\n" +
            "        </div>\n" +
            "        <ul class=\"row items-list\">\n" +
            "        </ul>\n" +
            "    </div>\n" +
            "</section>" +
            "        </div>" +
            "</li>\n");
        $.each(singleStore.storeItems || [], function(index, singleStoreItem) {
            $(".items-list").append(
                "           <li class=\"col-12 col-md-6 col-lg-3 item-card\">\n" +
                "           <figure><img src=" + itemImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
                "           <h3 class=\"item-name\">" + singleStoreItem.itemName + "</h3>" +
                "           <p class=\"area-list-item\">Item Id: " + singleStoreItem.itemId + " </p>\n" +
                "           <p class=\"area-list-item\">Purchase Category: " + singleStoreItem.purchaseCategory + "</p>\n" +
                "           <p class=\"area-list-item\">Price: " + singleStoreItem.itemPrice + "</p>\n" +
                "           <p class=\"area-list-item\">Amount Sold: " + singleStoreItem.amountSold + "</p>\n" +
                "           </li>\n");
        });
    });
}