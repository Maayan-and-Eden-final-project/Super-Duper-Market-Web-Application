var ordersInterval;

function displayShopOwnerOrdersHistory(ordersHistory) {
    $(".dynamic-container").empty();

    $(".dynamic-container").append(
        "<div class=\"orderHistoryHolder\">" +
        "<div id=\"orderHistoryAccordion\">\n" +
        "<p class=\"ordersHistoryHeader\"> Orders History </p>" +
        "</div></div>");
    var orderImageUrl = "../../common/images/orderIcon.png";
    var itemImageUrl = "../../common/images/itemIcon.png";
    var storeImageUrl = "../../common/images/storeIcon.png";
    $.each(ordersHistory || [], function (index, storeOrders) {
        $("#orderHistoryAccordion").append(
            "  <div class=\"card orderCard singleStoreOrders\">\n" +
            "<div class=\"card-header\" data-toggle=\"collapse\" data-target=\"#collapseStore" + index + "\" aria-expanded=\"true\">     \n" +
            "       <div class=\"cnt-block equal-hight store-info-block\">\n" +
            "         <div class=\"store-information-row orderInformationRow\">" +
            "            <figure class=\"store-orders-img-holder\" ><img src=" + storeImageUrl + " class=\"img-responsive store-orders-img\"  alt=\"\"></figure>\n" +
            "         <div class=\"store-orders-info\">Store Id: " + storeOrders.storeId + "</div>\n" +
            "         <div class=\"store-orders-info\">Store Name: " + storeOrders.storeName + "</div>\n" +
            "         </div>" +
            "</div>" +
            "            </div>" +
            "    <div id=\"collapseStore" + index + "\" class=\"collapse collapse-order\" aria-labelledby=\"headingOne\" data-parent=\"#orderHistoryAccordion\">\n" +
            "      <div class=\"card-body\">\n" +
            "<div id=\"storeOrdersHistoryAccordion" + index + "\">\n" +
            "</div>" +
            "      </div>\n" +
            "    </div>\n" +
            "  </div>");

        if(storeOrders.orders.length !== 0 ) {
            $.each(storeOrders.orders || [], function (i, order) {
                $("#storeOrdersHistoryAccordion" + index).append(
                    "  <div class=\"card orderCard singleShopOrder\">\n" +
                    "<div class=\"card-header\" data-toggle=\"collapse\" data-target=\"#collapseOrder" + i + index + "\" aria-expanded=\"true\">     \n" +
                    "       <div class=\"cnt-block equal-hight store-info-block\">\n" +
                    "         <div class=\"store-information-row orderInformationRow\">" +
                    "            <figure class=\"order-img-holder\" ><img src=" + orderImageUrl + " class=\"img-responsive order-store-img\"  alt=\"\"></figure>\n" +
                    "         <div class=\"single-order-info\">Order Id: " + order.orderId + "</div>\n" +
                    "         <div class=\"single-order-info\">Order Date: " + order.orderDate + "</div>\n" +
                    "         <div class=\"single-order-info\">Purchaser Name: " + order.purchaserName + "</div>\n" +
                    "         <div class=\"single-order-info\">Customer Location: " + order.customerLocation.x + "," + order.customerLocation.y + "</div>\n" +
                    "         <div class=\"single-order-info\">Number Of Different Items: " + order.numberOfDifferentItems + "</div>\n" +
                    "         <div class=\"single-order-info\">Total Items Cost: " + order.totalItemsCost.toFixed(2) + "</div>\n" +
                    "         <div class=\"single-order-info\">Shipping Cost: " + order.shippingCost.toFixed(2) + "</div>\n" +
                    "             </div>" +
                    "</div>" +
                    "            </div>" +
                    "    <div id=\"collapseOrder" + i + index + "\" class=\"collapse collapse-order\" aria-labelledby=\"headingOne\" data-parent=\"#storeOrdersHistoryAccordion" + index + "\">\n" +
                    "      <div class=\"card-body orderCardBody\">\n" +
                    "        <ul id=\"orderItems-" + i + index + "\"  class=\"row orderItems\">\n" +
                    "        </ul>\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "  </div>");

                $.each(order.items || [], function (j, item) {
                    $("#orderItems-" + i + index).append(
                        "           <li class=\"col-12 col-md-6 col-lg-3 store-item-card order-item-card\">\n" +
                        "           <figure class=\"image-figure\"><img src=" + itemImageUrl + " class=\"img-responsive store-item-image\"  alt=\"\"></figure>\n" +
                        "           <h3 class=\"item-name\">" + item.itemName + "</h3>" +
                        "           <p class=\"area-list-item\">Item Id: " + item.itemId + " </p>\n" +
                        "           <p class=\"area-list-item\">Purchase Category: " + item.purchaseCategory + "</p>\n" +
                        "           <p class=\"area-list-item\">Amount: " + item.amount + "</p>\n" +
                        "           <p class=\"area-list-item\">Price Per Piece: " + item.pricePerPiece + "</p>\n" +
                        "           <p class=\"area-list-item\">Total Item Cost: " + item.totalItemCost + "</p>\n" +
                        "           <p class=\"area-list-item\">Is From Discount: " + item.isFromDiscount + "</p>\n" +
                        "           </li>\n");
                });
            });
        } else {
            $("#storeOrdersHistoryAccordion" + index).append("<div class=\"noOrders\">No Orders</div>");
        }
    });

}

function getShopOwnerOrderHistory() {
    $.ajax({
        method: 'GET',
        url: "../areaMainPage/shopOwner",
        data: "actionType=getOrdersHistory",
        timeout: 4000,
        dataType: "json",
        error: function (e) {
            console.error("test");
        },
        success: function (r) {
            clearOrderData();
            displayShopOwnerOrdersHistory(r);
        }
    });
}
function makeOrdersInterval() {
    ordersInterval = setInterval(getShopOwnerOrderHistory, 1500);
}
