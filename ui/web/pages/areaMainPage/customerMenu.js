var itemIdToAmount = new Map();
var method;
var store;
var date;
var xLocation;
var yLocation;

function displayMinimalCartSummery(minimalCartStores) {

    $(".dynamic-container").empty();
    $(".dynamic-container").append(
        "<section class=\"our-webcoderskull padding-lg\">\n" +
        "    <div class=\"container stores-container\">\n" +
        "        <div class=\"row heading heading-icon\">\n" +
        "            <p class=\"minimalCartHeader\">Minimal Cart</p>\n" +
        "        </div>\n" +
        "        <ul class=\"row items-list minimalCartList\">\n" +
        "        </ul>\n" +
        "    </div>\n" +
        "</section>" +
        " <button id=\"displayDiscountsButton\" class=\"btn btn-primary mb-2\">Next</button>\n");

    $("#displayDiscountsButton").click(function () {
        getDiscounts();
    });


    $.each(minimalCartStores || [], function(index, singleStore) {

        var storeImageUrl = "../../common/images/storeIcon.png";

        $(".items-list").append(
            "<li class=\"col-12 col-md-6 col-lg-3 store-card\">\n" +
            "       <div class=\"cnt-block equal-hight minimalCartStoreCard\">\n" +
            "            <figure><img src=" + storeImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
            "             <h3 class=\"store-name\">" + singleStore.storeName + "</h3>" +
            "           <p id=\"storeId\" class=\"minimalCartSingleStore\">Store Id: " + singleStore.storeId + " </p>\n" +
            "           <p class=\"minimalCartSingleStore\">Location: [" + singleStore.location.x + "," + singleStore.location.y + "] </p>\n" +
            "           <p class=\"minimalCartSingleStore\">Distance: " + singleStore.distanceFromCustomer + "</p>\n" +
            "           <p class=\"minimalCartSingleStore\">PPK: " + singleStore.ppk + "</p>\n" +
            "           <p class=\"minimalCartSingleStore\">Delivery Cost: " + singleStore.customerShippingCost + "</p>\n" +
            "           <p class=\"minimalCartSingleStore\">Number Of Items: " + singleStore.numOfDifferentItem + "</p>\n" +
            "           <p class=\"minimalCartSingleStore\">Total Items Cost: " + singleStore.totalItemsCost + "</p>\n" +
            "       </div>" +
            " </li>\n");
    });

}

function getMinimalCart() {
    var itemsListJsonString = JSON.stringify(Array.from(itemIdToAmount));

    $.ajax({
        method: 'GET',
        data: "&xLocationKey=" + xLocation + "&yLocationKey=" + yLocation + "&actionType=getMinimalCart" +"&itemIdToAmountKey=" +itemsListJsonString ,
        url: "customer",
        error: function (jqXHR) {
           bootstrap_alert.warning(jqXHR.responseText,"red");
        },
        success: function (r) {
            /* itemIdToItemPrice.clear();*/
           displayMinimalCartSummery(r);
        }
    });
}

function addItemToMap(itemId,amount) {
    if (itemIdToAmount.get(itemId) === undefined) {
        itemIdToAmount.set(itemId, amount); //TODO: empty when back clicked
    } else {
        itemIdToAmount.set(itemId, itemIdToAmount.get(itemId) + amount); //TODO: empty when back clicked
    }
}


function displayDynamicItemsOption(items) {

    $(".dynamic-container").append(
        "<section class=\"our-webcoderskull padding-lg\">\n" +
        "    <div class=\"container items-container\">\n" +
        "        <div class=\"row heading heading-icon\">\n" +
        "            <p class=\"newOrderItemsHeader\">Items</p>\n" +
        "        </div>\n" +
        "        <ul class=\"row items-list newOrderItemsList\">\n" +
        "        </ul>\n" +
        "    </div>\n" +
        "</section>");


    $.each(items.itemIdToItemInfo || [], function(index, singleItem) {

        var itemImageUrl = "../../common/images/itemIcon.png";

        $(".items-list").append(
            "<li class=\"col-12 col-md-6 col-lg-3 item-card\">\n" +
            "       <div class=\"cnt-block equal-hight newOrderItemCard\">\n" +
            "            <figure><img src=" + itemImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
            "             <h3 class=\"item-name\">" + singleItem.itemName + "</h3>" +
            "           <p id=\"itemId\" class=\"newOrderSingleItem\">Item Id: " + singleItem.itemId + " </p>\n" +
            "           <p class=\"newOrderSingleItem\">Purchase Category: " + singleItem.purchaseCategory + "</p>\n" +
            "<form id=\"new-order-item-form-" + singleItem.itemId + "\">" +
            "<input type=\"number\" step=\"0.01\" min=0 class=\"form-control newOrderItemAmount\" id=\"itemAmount" + singleItem.itemId + "\" placeholder=\"Amount\" required>" +
            " <button id=\"addItem" + singleItem.itemId + "\" type=\"submit\" class=\"btn btn-primary mb-2 newOrderItemSubmit\">Add Item</button>\n" +
            "</form>" +
            "        <div class=\"popup\" >\n" +
            "            <span class=\"popuptext\" id=\"myPopup\"></span>" +
            "        </div> \n" +
            "       </div>" +
            " </li>\n");
        $("#new-order-item-form-" + singleItem.itemId).submit(function () {
            var amount =  $("#itemAmount" + singleItem.itemId).val();

            if(singleItem.purchaseCategory.indexOf("QUANTITY") > -1) {
                if(amount.indexOf(".") > -1) {
                    var popup = document.getElementById("myPopup");
                    popup.innerText = "Please enter whole number";
                    popup.classList.toggle("show");
                    setTimeout(function () {
                        popup.classList.toggle("show"); //close the tooltip
                    }, 2000);
                } else {
                    addItemToMap(singleItem.itemId,parseInt(amount));
                }
            } else {
                addItemToMap(singleItem.itemId,parseInt(amount));
            }
            return false;
        });
    });
}

function getDiscounts() {
    var itemsListJsonString = JSON.stringify(Array.from(itemIdToAmount));

    $.ajax({
        method: 'GET',
        data: "methodKey=" + method + "&storeKey=" + store + "&dateKey=" + date + "&xLocationKey=" + xLocation + "&yLocationKey=" + yLocation + "&actionType=getDiscounts" +"&itemIdToAmountKey=" +itemsListJsonString ,
        url: "customer",
        error: function (jqXHR) {
            setTimeout(bootstrap_alert.warning(jqXHR.responseText,"red"),3000);
        },
        success: function (r) {
            /* itemIdToItemPrice.clear();*/
            $("#orderMethodDropdown").attr("disabled",true);
            $("#storesDropdown").attr("disabled",true);
            $("#date-input").attr("disabled",true);
            $("#xStoreLocation").attr("disabled",true);
            $("#yStoreLocation").attr("disabled",true);
            $("#orderOptionSubmit").attr("disabled",true);
            if(method.indexOf("Static Order") > -1) {
                displayStaticItemsOption(r);
            } else if(method.indexOf("Dynamic Order") > -1) {
                displayDynamicItemsOption(r);
            }
        }
    });
}

function displayStaticItemsOption(items) {

    $(".dynamic-container").append(
        "<section class=\"our-webcoderskull padding-lg\">\n" +
        "    <div class=\"container items-container\">\n" +
        "        <div class=\"row heading heading-icon\">\n" +
        "            <p class=\"newOrderItemsHeader\">Items</p>\n" +
        "        </div>\n" +
        "        <ul class=\"row items-list newOrderItemsList\">\n" +
        "        </ul>\n" +
        "    </div>\n" +
        "</section>" +
        " <button id=\"displayDiscountsButton\" class=\"btn btn-primary mb-2\">Next</button>\n");

    $("#displayDiscountsButton").click(function () {
        getDiscounts();
    });

    $.each(items || [], function(index, singleItem) {

        var itemImageUrl = "../../common/images/itemIcon.png";

        $(".items-list").append(
            "<li class=\"col-12 col-md-6 col-lg-3 item-card\">\n" +
            "       <div class=\"cnt-block equal-hight newOrderItemCard\">\n" +
            "            <figure><img src=" + itemImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
            "             <h3 class=\"item-name\">" + singleItem.itemName + "</h3>" +
            "           <p id=\"itemId\" class=\"newOrderSingleItem\">Item Id: " + singleItem.itemId + " </p>\n" +
            "           <p class=\"newOrderSingleItem\">Purchase Category: " + singleItem.purchaseCategory + "</p>\n" +
            "           <p class=\"newOrderSingleItem\">Price: " + singleItem.itemPrice + "</p>\n" +
            "<form id=\"new-order-item-form-" + singleItem.itemId + "\">" +
            "<input type=\"number\" step=\"0.01\" min=0 class=\"form-control newOrderItemAmount\" id=\"itemAmount" + singleItem.itemId + "\" placeholder=\"Amount\" required>" +
            " <button id=\"addItem" + singleItem.itemId + "\" type=\"submit\" class=\"btn btn-primary mb-2 newOrderItemSubmit\">Add Item</button>\n" +
            "</form>" +
            "        <div class=\"popup\" >\n" +
            "            <span class=\"popuptext\" id=\"myPopup-" + singleItem.itemId + "\" ></span>" +
            "        </div> \n" +
            "       </div>" +
            " </li>\n");
        $("#new-order-item-form-" + singleItem.itemId).submit(function () {
            var amount =  $("#itemAmount" + singleItem.itemId).val();

            if(singleItem.purchaseCategory.indexOf("QUANTITY") > -1) {
                if(amount.indexOf(".") > -1) {
                    var popup = document.getElementById("myPopup-" + singleItem.itemId);
                    popup.innerText = "Please enter whole number";
                    popup.classList.toggle("show");
                    setTimeout(function () {
                        popup.classList.toggle("show"); //close the tooltip
                    }, 2000);
                }
            } else {
                if(itemIdToAmount.get(singleItem.itemId) === undefined) {
                    itemIdToAmount.set(singleItem.itemId,amount); //TODO: empty when back clicked
                } else {
                    itemIdToAmount.set(singleItem.itemId,itemIdToAmount.get(singleItem.itemId) + amount); //TODO: empty when back clicked
                }
            }
            $("#itemAmount" + singleItem.itemId).val("");
            return false;
        });
    });
}

function getSelectedMethod() {
    var orderMethod = $("#orderMethodDropdown").val();
    if(orderMethod.indexOf("Static Order") > -1 ) {
        $("#storesDropdown").attr("disabled",false);
    } else if(orderMethod.indexOf("Dynamic Order") > -1 ) {
        $("#storesDropdown").attr("disabled",true);
    }
}

function makeNewOrderForm(areaStores) {
    $(".dynamic-container").empty();
    $(".dynamic-container").append(
        "<p class=\"newOrderHeader\">Make New Order</p>\n" +
        "<form id=\"newOrderForm\" action=\"\" class=\"form-inline\">" +
        "  <select id=\"orderMethodDropdown\" class=\"orderFormControl\" onchange=\"getSelectedMethod()\" required>\n" +
        "<option value=\"\">Choose Method</option>\n" +
        "<option>Static Order</option>\n" +
        "<option>Dynamic Order</option>\n" +
        "  </select>\n" +
        "  <select id=\"storesDropdown\" class=\"orderFormControl\" disabled=\"disabled\" required>\n" +
        "<option value=\"\">Choose Store</option>\n" +
        "  </select>\n" +
        "</form>");

    $.each(areaStores.stores || [], function(index, store) {
        $("#storesDropdown").append(
            "<option>" + store.storeId + ": " + store.storeName + "</option>\n");
    });

    $("#newOrderForm").append(
        "<input type=\"date\" id=\"date-input\" name=\"orderDate\" required>\n" +
        "<input type=\"text\" class=\"form-control orderFormControl\" id=\"xStoreLocation\" placeholder=\"X Coordinate\" required pattern=\"^(50|[1-4]?[0-9])$\">" +
        "<input type=\"text\" class=\"form-control orderFormControl\" id=\"yStoreLocation\" placeholder=\"Y Coordinate\" required pattern=\"^(50|[1-4]?[0-9])$\">" +


        "<button type=\"submit\" class=\"btn orderFormControl\" id=\"orderOptionSubmit\" required> Show Items </button>" +
        " <button id=\"displayMinimalCartButton\" class=\"btn btn-primary mb-2\">Next</button>\n" +
        "<div id = \"alert_placeholder\"></div>\n");

    $("#displayMinimalCartButton").click(function () {
        getMinimalCart();
    });

    $("#newOrderForm").submit(function () {
       /* if(itemIdToItemPrice.size === 0) {
            $('#addStoreSubmit').on('click', function() {
                bootstrap_alert.warning('You Must Choose At Least One Item  ',"red");
            });
            $('#addStoreSubmit').click();
        } else {*/


            method = $("#orderMethodDropdown").val();
            store = $("#storesDropdown").val();
            date = $("#date-input").val();
            xLocation = $("#xStoreLocation").val();
            yLocation = $("#yStoreLocation").val();

            /*var itemsListJsonString = JSON.stringify(Array.from(itemIdToItemPrice));*/

            $.ajax({
                method: 'GET',
                data: "methodKey=" + method + "&storeKey=" + store + "&dateKey=" + date + "&xLocationKey=" + xLocation + "&yLocationKey=" + yLocation + "&actionType=getOrderItemsOption",
                url: "customer",
                error: function (jqXHR) {
                   setTimeout(bootstrap_alert.warning(jqXHR.responseText,"red"),3000);
                },
                success: function (r) {
                   /* itemIdToItemPrice.clear();*/
                    $("#orderMethodDropdown").attr("disabled",true);
                    $("#storesDropdown").attr("disabled",true);
                    $("#date-input").attr("disabled",true);
                    $("#xStoreLocation").attr("disabled",true);
                    $("#yStoreLocation").attr("disabled",true);
                    $("#orderOptionSubmit").attr("disabled",true);
                    if(method.indexOf("Static Order") > -1) {
                        displayStaticItemsOption(r);
                    } else if(method.indexOf("Dynamic Order") > -1) {
                        displayDynamicItemsOption(r);
                    }
                }
            });

        return false;
    });
   /* getItemsOption();*/
}

function getNewOrderOptions() {
    $.ajax({
        method: 'GET',
        url: "../areaMainPage/customer",
        data: "actionType=newOrder",
        timeout: 4000,
        dataType: "json",
        error: function (e) {
            console.error("test");
        },
        success: function (r) {
            makeNewOrderForm(r);
        }
    });
}