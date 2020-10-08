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

            "</div>" +
            "            </li>\n");
    });
}