function displayCustomerOrdersHistory(ordersHistory) {
    $(".dynamic-container").empty();

    $(".dynamic-container").append(
        "<div id=\"orderHistoryAccordion\">\n" +

        "</div>");
    var storeImageUrl = "../../common/images/storeIcon.png";
    var itemImageUrl = "../../common/images/itemIcon.png";

    $.each(ordersHistory || [], function(index, order) {
        $("#orderHistoryAccordion").append(
            "  <div class=\"card orderCard\">\n" +
            "<div class=\"card-header\" data-toggle=\"collapse\" data-target=\"#collapseOne" + index + "\" aria-expanded=\"true\">     \n" +
            "       <div class=\"cnt-block equal-hight store-info-block\">\n" +
            "         <div class=\"row store-information-row\">" +
            "            <figure><img src=" + storeImageUrl + " class=\"img-responsive col-sm-4 order-store-img\"  alt=\"\"></figure>\n" +
            "         <div class=\"area-list-store col-sm-1\">Order Id: " + order.orderId + "</div>\n" +
            "         <div class=\"area-list-store col-sm-1\">Order Date: " + order.orderDate + "</div>\n" +
            "         <div class=\"area-list-store col-sm-1\">Customer Location: " + order.customerLocation.x + "," + order.customerLocation.y + "</div>\n" +
            "         <div class=\"area-list-store col-sm-1\">Number Of Ordered Stores: " + order.numOfOrderedStores + "</div>\n" +
            "         <div class=\"area-list-store col-sm-1\">Number Of Different Items: " + order.numberOfDifferentItems + "</div>\n" +
            "         <div class=\"area-list-store col-sm-1\">Total Items Cost: " + order.totalItemsCost.toFixed(2) + "</div>\n" +
            "         <div class=\"area-list-store col-sm-1\">Shipping Cost: " + order.shippingCost.toFixed(2) + "</div>\n" +
            "         <div class=\"area-list-store col-sm-1\">Total Order Cost: " + order.totalOrderCost.toFixed(2) + "</div>\n" +
            "             </div>" +
            "</div>" +
            "            </div>" +
            "    <div id=\"collapseOne" + index + "\" class=\"collapse show collapse-order\" aria-labelledby=\"headingOne\" data-parent=\"#orderHistoryAccordion\">\n" +
            "      <div class=\"card-body orderCardBody\">\n" +
            "        <ul id=\"orderItems-" + index + "\"  class=\"row orderItems\">\n" +
            "        </ul>\n" +
            "      </div>\n" +
            "    </div>\n" +
            "  </div>");

        $.each(order.items || [], function(index, item) {
            $("#orderItems-" + index).append(
                "           <li class=\"col-12 col-md-6 col-lg-3 store-item-card order-item-card\">\n" +
                "           <figure class=\"image-figure\"><img src=" + itemImageUrl + " class=\"img-responsive store-item-image\"  alt=\"\"></figure>\n" +
                "           <h3 class=\"item-name\">" + item.itemName + "</h3>" +
                "           <p class=\"area-list-item\">Item Id: " + item.itemId + " </p>\n" +
                "           <p class=\"area-list-item\">Purchase Category: " + item.purchaseCategory + "</p>\n" +
                "           <p class=\"area-list-item\">Store Id: " + item.storeId + "</p>\n" +
                "           <p class=\"area-list-item\">Store Name: " + item.storeName + "</p>\n" +
                "           <p class=\"area-list-item\">Amount: " + item.amount + "</p>\n" +
                "           <p class=\"area-list-item\">Price Per Piece: " + item.pricePerPiece + "</p>\n" +
                "           <p class=\"area-list-item\">Total Item Cost: " + item.totalItemCost + "</p>\n" +
                "           <p class=\"area-list-item\">Is From Discount: " + item.isFromDiscount + "</p>\n" +
                "           </li>\n");
        });
    });

}



function getCustomerOrderHistory() {
    $.ajax({
        method: 'GET',
        url: "../areaMainPage/customer",
        data: "actionType=getOrdersHistory",
        timeout: 4000,
        dataType: "json",
        error: function (e) {
            console.error("test");
        },
        success: function (r) {
            clearOrderData();
            displayCustomerOrdersHistory(r);
        }
    });
}