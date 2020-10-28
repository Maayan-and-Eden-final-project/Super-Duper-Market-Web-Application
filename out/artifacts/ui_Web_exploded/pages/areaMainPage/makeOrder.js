var itemIdToAmount = new Map();
var discountNameToOffersList= new Map();
var minimalCartJson;
var method;
var store;
var date;
var xLocation;
var yLocation;
var discountRegex = /[$&+,:;=?@#|'<>.^*()%!]/g;
var orderSummeryInfo;

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

    minimalCart = minimalCartStores.storeIdToItemsList;
    minimalCartJson = JSON.stringify(minimalCart);

    $.each(minimalCartStores.dynamicStores || [], function(index, singleStore) {

        var storeImageUrl = "../../common/images/storeIcon.png";

        $(".items-list").append(
            "<li class=\"col-12 col-md-6 col-lg-3 store-card minimalCartStoreCard\">\n" +
            "       <div class=\"cnt-block equal-hight\">\n" +
            "            <figure><img src=" + storeImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
            "             <h3 class=\"store-name minimalCartStoreHeader\">" + singleStore.storeName + "</h3>" +
            "           <p id=\"storeId\" class=\"minimalCartSingleStore\">Store Id: " + singleStore.storeId + " </p>\n" +
            "           <p class=\"minimalCartSingleStore\">Location: [" + singleStore.location.x + "," + singleStore.location.y + "] </p>\n" +
            "           <p class=\"minimalCartSingleStore\">Distance: " + singleStore.distanceFromCustomer.toFixed(2) + "</p>\n" +
            "           <p class=\"minimalCartSingleStore\">PPK: " + singleStore.ppk + "</p>\n" +
            "           <p class=\"minimalCartSingleStore\">Delivery Cost: " + singleStore.customerShippingCost.toFixed(2) + "</p>\n" +
            "           <p class=\"minimalCartSingleStore\">Number Of Items: " + singleStore.numOfDifferentItem + "</p>\n" +
            "           <p class=\"minimalCartSingleStore\">Total Items Cost: " + singleStore.totalItemsCost.toFixed(2) + "</p>\n" +
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

function displayPopup(message,popup) {
    popup.innerText = message;
    popup.classList.toggle("show");
    setTimeout(function () {
        popup.classList.toggle("show"); //close the tooltip
    }, 2000);
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
            "        <div class=\"popup add-item-popup\" >\n" +
            "            <span class=\"popuptext\" id=\"myPopup-" + singleItem.itemId + "\" ></span>" +
            "        </div> \n" +
            "       </div>" +
            " </li>\n");
        $("#new-order-item-form-" + singleItem.itemId).submit(function () {
            var amount =  $("#itemAmount" + singleItem.itemId).val();
            var popup = document.getElementById("myPopup-" + singleItem.itemId);
            if(singleItem.purchaseCategory.indexOf("QUANTITY") > -1) {
                if(amount.indexOf(".") > -1) {
                    displayPopup("Please enter whole number",popup);
                } else {
                    addItemToMap(singleItem.itemId,parseFloat(amount));
                    displayPopup("Item Successfuly Added",popup);
                    if(itemIdToAmount.size > 0) {
                        $("#nextStepButton").attr("disabled",false);
                    }
                }
            } else {
                addItemToMap(singleItem.itemId,parseFloat(amount));
                displayPopup("Item Successfuly Added",popup);
                if(itemIdToAmount.size > 0) {
                    $("#nextStepButton").attr("disabled",false);
                }
            }
            $("#itemAmount" + singleItem.itemId).val("");
            return false;
        });
    });
}

function addToDiscountMap(offerItemId,offerItemQuantity,offerForAdditional,singleDiscountName) {
    var discountName = singleDiscountName.replaceAll("-"," ").trim();
    if(discountNameToOffersList.get(discountName) !== undefined) {
        var offersList = discountNameToOffersList.get(discountName);
        offersList.push({itemId:offerItemId,quantity:offerItemQuantity,forAdditional:offerForAdditional});
        discountNameToOffersList.set(discountName,offersList);
    } else {
        discountNameToOffersList.set(discountName, new Array(
            {itemId:offerItemId,quantity:offerItemQuantity,forAdditional:offerForAdditional}));
    }
}

function addOfferDetailToMap(offerDetail,singleDiscountName) {
    var offerItemId = offerDetail[0].innerText.split(":")[1].trim();
    var offerItemQuantity = offerDetail[1].innerText.split(":")[1].trim();
    var offerForAdditional = offerDetail[2].innerText.split(":")[1].trim();
    addToDiscountMap(offerItemId,offerItemQuantity,offerForAdditional,singleDiscountName);
}

function clearOrderData() {
    itemIdToAmount.clear();
    discountNameToOffersList.clear();
    minimalCartJson = undefined ;
    method = undefined;
    store = undefined;
    date = undefined;
    xLocation = undefined;
    yLocation = undefined;
    orderSummeryInfo = undefined;
}

function displayOrderSummery(orderSummery) {
        $(".dynamic-container").children().remove();
        $(".dynamic-container").append(
            "<section id=\"orderSummerySection\" class=\"our-webcoderskull padding-lg\">\n" +
            "    <div class=\"container orderSummery-container\">\n" +
            "        <div class=\"row heading heading-icon\">\n" +
            "            <h2 class=\"orderSummery-header\">Order Summery</h2>\n" +
            "        </div>\n" +
            "        <ul class=\"row stores-list orderSummery-storesList\">\n" +
            "        </ul>\n" +
            "    </div>\n" +
            "<div class=\"orderSummeryButtons\">" +
            "<button id=\"confirmOrderButton\" class=\"btn btn-primary mb-2 \">Confirm</button>" +
            "<button id=\"cancelOrderButton\" class=\"btn btn-primary mb-2 \">Cancel</button>" +
                "</div>" +
            "</section>");

        $("#cancelOrderButton").click(function () {
            $(".dynamic-container").children().remove();
            clearOrderData();

        });

        $("#confirmOrderButton").click(function () {
            $(".dynamic-container").children().remove();
            var data;

            data = "&actionType=confirmOrder" + "&orderSummeryKey=" + orderSummeryInfo + "&dateKey=" + date + "&methodKey=" + method;

            $.ajax({
                method: 'POST',
                data: data ,
                url: "customer",
                error: function (jqXHR) {
                    setTimeout(bootstrap_alert.warning(jqXHR.responseText,"red"),3000);
                },
                success: function (r) {
                    clearOrderData();
                    displayFillFeedback(r);
                }
            });
        });

        $.each(orderSummery.storeIdToStoreInfo || [], function(index, singleStore) {

            var storeImageUrl = "../../common/images/storeIcon.png";
            var itemImageUrl = "../../common/images/itemIcon.png";

            $(".stores-list").append(
                "<li class=\"col-12 col-md-6 col-lg-3 store-card summeryStoreCard\">\n" +
                "       <div class=\"cnt-block equal-hight store-info-block\">\n" +
                "            <figure><img src=" + storeImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
                "             <h3 class=\"store-name\">" + singleStore.storeName + " " +
                "                 <small class=\"text-muted\"> Store Id: " + singleStore.storeId +
                "                 </small>" +
                "             </h3>" +
                "     <div class=\"row store-information-row summery-store-information\">" +
                "         <div class=\"area-list-store col-sm-4\">PPK: " + singleStore.ppk + "</div>\n" +
                "         <div class=\"area-list-store col-sm-4\">Distance From Customer: " + singleStore.distanceFromCustomer.toFixed(2) + "</div>\n" +
                "         <div class=\"area-list-store col-sm-4\">Shipping Cost: " + singleStore.customerShippingCost.toFixed(2) + "</div>\n" +
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
            $.each(singleStore.progressItems || [], function(index, singleStoreItem) {
                $("#store-" + singleStore.storeId).append(
                    "           <li class=\"col-12 col-md-6 col-lg-3 store-item-card\">\n" +
                    "           <figure class=\"image-figure\"><img src=" + itemImageUrl + " class=\"img-responsive store-item-image\"  alt=\"\"></figure>\n" +
                    "           <h3 class=\"item-name\">" + singleStoreItem.itemName + "</h3>" +
                    "           <p class=\"area-list-item\">Item Id: " + singleStoreItem.itemId + " </p>\n" +
                    "           <p class=\"area-list-item\">Purchase Category: " + singleStoreItem.purchaseCategory + "</p>\n" +
                    "           <p class=\"area-list-item\">Amount: " + singleStoreItem.amount + "</p>\n" +
                    "           <p class=\"area-list-item\">Price Per Piece: " + singleStoreItem.pricePerPiece + "</p>\n" +
                    "           <p class=\"area-list-item\">Total Price: " + singleStoreItem.totalPrice.toFixed(2) + "</p>\n" +
                    "           <p class=\"area-list-item\">Is From Discount: " + singleStoreItem.isFromDiscount + "</p>\n" +
                    "           </li>\n");
            });
        });

        $(".orderSummery-container").append(
            "     <div class=\"row order-summery-information-row\">" +
            "         <div class=\"area-list-store order-summery-info col-sm-4\">Total Items Cost: " + orderSummery.totalOrderCostWithoutShipping.toFixed(2) + "</div>\n" +
            "         <div class=\"area-list-store order-summery-info col-sm-4\">Total Shipping Cost: " + orderSummery.totalShippingCost.toFixed(2) + "</div>\n" +
            "         <div class=\"area-list-store order-summery-info col-sm-4\">Total Order Cost: " + orderSummery.totalOrderCost.toFixed(2) + "</div>\n" +
            "             </div>"
        );
}

function displayDiscounts(discounts) {
    $(".dynamic-container").empty();

    $(".dynamic-container").append(
        "<section class=\"our-webcoderskull padding-lg\">\n" +
        "    <div class=\"container discounts-container\">\n" +
        "<button id=\"goToSummeryButton\" class=\"btn btn-primary mb-2 \">Go To Order Summery</button>" +
        "        <div class=\"row heading heading-icon\">\n" +
        "            <p class=\"newOrderDiscountsHeader\">Discounts</p>\n" +
        "        </div>\n" +
        "        <ul class=\"row items-list newOrderDiscountsList\">\n" +
        "        </ul>\n" +
        "    </div>\n" +
        "</section>");

    $("#goToSummeryButton").click(function () {
        var itemsListJsonString = JSON.stringify(Array.from(itemIdToAmount));

        var discountNameToOffersListString = JSON.stringify(Array.from(discountNameToOffersList));
        var data;

        if (method.indexOf("Static Order") > -1) {
            data = "methodKey=" + method + "&storeKey=" + store + "&xLocationKey=" + xLocation + "&yLocationKey=" + yLocation + "&actionType=getOrderSummery" + "&itemIdToAmountKey=" + itemsListJsonString + "&discountListKey=" + discountNameToOffersListString;
        } else if (method.indexOf("Dynamic Order") > -1) {
            data = "methodKey=" + method + "&xLocationKey=" + xLocation + "&yLocationKey=" + yLocation + "&actionType=getOrderSummery" + "&itemIdToAmountKey=" + itemsListJsonString + "&discountListKey=" + discountNameToOffersListString + "&minimalCartKey=" + minimalCartJson;
        }

        $.ajax({
            method: 'POST',
            data: data,
            url: "customer",
            dataType: "json",
            error: function (jqXHR) {
                setTimeout(bootstrap_alert.warning(jqXHR.responseText, "red"), 3000);
            },
            success: function (r) {
                orderSummeryInfo = JSON.stringify(r);
                displayOrderSummery(r);
            }
        });
    });

    $.each(discounts || [], function (i, singleDiscount) {

        var discountImageUrl = "../../common/images/discount-tag.png";
        var singleDiscountName = singleDiscount.name.replaceAll(discountRegex, "");
        singleDiscountName = singleDiscountName.replaceAll(" ", "-");
        $(".items-list").append(
            "<li class=\"col-12 col-md-6 col-lg-3 discount-card\">\n" +
            "       <div class=\"cnt-block equal-hight newOrderDiscountCard\">\n" +
            "            <figure><img src=" + discountImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
            "             <h3 class=\"discount-name\">" + singleDiscount.name + "</h3>" +
            "           <p id=\"ifYouBuy\" class=\"newOrderSingleDiscount discountHeaders\">If You Buy</p>\n" +
            "           <p id=\"ifYouBuyItemId\" class=\"newOrderSingleDiscount\">Item Id: " + singleDiscount.ifYouBuy.itemId + "</p>\n" +
            "           <p id=\"ifYouBuyQuantity\" class=\"newOrderSingleDiscount\">Item Quantity: " + singleDiscount.ifYouBuy.quantity + "</p>\n" +
            "           <p id=\"thenYouGet\" class=\"newOrderSingleDiscount discountHeaders\">Then You Get</p>\n" +
            "           <p id=\"thenYouGetOperator\" class=\"newOrderSingleDiscount\">" + singleDiscount.thenYouGet.operator.replaceAll("_", " ") + "</p><br>\n" +
            "           <div id=\"offersList" + singleDiscountName + i + "\" class=\"row newOrderSingleDiscount\"></div>\n" +
            " <button id=\"addDiscount" + singleDiscountName + i + "\" class=\"btn btn-primary mb-2 newOrderDiscountSubmit\">Add Discount</button>\n" +
            "        <div class=\"popup add-item-popup\" >\n" +
            "            <span class=\"popuptext\" id=\"myPopup" + i + "\"></span>" +
            "        </div> \n" +
            "       </div>" +
            " </li>\n");


        if (singleDiscount.thenYouGet.operator.indexOf("ONE_OF") > -1) {
            $("#offersList" + singleDiscountName + i).append(
                "<form id=\"oneOfForm" + singleDiscountName + i + "\" >\n" +
                "</form>");

            $.each(singleDiscount.thenYouGet.offers || [], function (j, singleOffer) {
                $("#oneOfForm" + singleDiscountName + i).append(
                    "  <input type=\"radio\" class=\"oneOfRadio" + i + "\" value=\"Offer" + singleOffer.itemId + j + i + "\" name=\"oneOfOffer" + i + "\">\n" +
                    "  <label class=\"offer-label\" for=\"Offer" + singleOffer.itemId + j + i + "\">Item Id: " + singleOffer.itemId + "</label><br>\n" +
                    "  <label class=\"offer-label\" for=\"Offer" + singleOffer.itemId + j + i + "\">Item Quantity: " + singleOffer.quantity + "</label><br>\n" +
                    "  <label class=\"offer-label\" for=\"Offer" + singleOffer.itemId + j + i + "\">For Additional: " + singleOffer.forAdditional + "</label><br>\n");
            });
        } else {
            $.each(singleDiscount.thenYouGet.offers || [], function (index, singleOffer) {
                $("#offersList" + singleDiscountName + i).append(
                    "<div id=\"item" + index + i + "\">" +
                    "  <p  class=\"newOrderSingleDiscount\">Item Id: " + singleOffer.itemId + "</p>\n" +
                    "  <p  class=\"newOrderSingleDiscount\">Item Quantity: " + singleOffer.quantity + "</p>\n" +
                    "  <p  class=\"newOrderSingleDiscount\">For Additional: " + singleOffer.forAdditional + "</p><br>\n" +
                    "</div>");
            });
        }

        $("#addDiscount" + singleDiscountName + i).click(function () {
            if (singleDiscount.thenYouGet.operator.indexOf("ONE_OF") > -1) {
                if (document.querySelector("input[name=oneOfOffer" + i + "]:checked") !== null) {
                    var chosenOffer = document.querySelector("input[name=oneOfOffer" + i + "]:checked").value;
                    var offerDetail = $("label[for=" + chosenOffer + "]");
                    addOfferDetailToMap(offerDetail, singleDiscountName);
                    $(".oneOfRadio" + i).attr("disabled", true);
                    $("#addDiscount" + singleDiscountName + i).attr("disabled", true);
                } else {
                    var popup = document.getElementById("myPopup" + i);
                    displayPopup("Select One Option",popup);
                }
            } else {
                var listItems = $("#offersList" + singleDiscountName + i).children();
                $.each(listItems || [], function (index, item) {
                    var offerDetail = $("#item" + index + i).children();
                    addOfferDetailToMap(offerDetail, singleDiscountName);
                });
                $(".oneOfRadio" + i).attr("disabled", true);
                $("#addDiscount" + singleDiscountName + i).attr("disabled", true);
            }
        });
    });
}

function getDiscounts() {
    var itemsListJsonString = JSON.stringify(Array.from(itemIdToAmount));
    var data;

    if(method.indexOf("Static Order") > -1) {
        data = "&methodKey=" + method + "&storeKey=" + store + "&actionType=getDiscounts" + "&itemIdToAmountKey=" + itemsListJsonString;
    } else if(method.indexOf("Dynamic Order") > -1) {
        data = "&methodKey=" + method + "&storeKey=" + store + "&actionType=getDiscounts" + "&itemIdToAmountKey=" + itemsListJsonString + "&minimalCartKey=" + minimalCartJson;

    }

    $.ajax({
        method: 'POST',
        data: data ,
        url: "customer",
        dataType: "json",
        error: function (jqXHR) {
            bootstrap_alert.warning(jqXHR.responseText,"red");
        },
        success: function (r) {
            displayDiscounts(r);
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
        "</section>");

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
            "        <div class=\"popup add-item-popup\" >\n" +
            "            <span class=\"popuptext\" id=\"myPopup-" + singleItem.itemId + "\" ></span>" +
            "        </div> \n" +
            "       </div>" +
            " </li>\n");
        $("#new-order-item-form-" + singleItem.itemId).submit(function () {
            var amount =  $("#itemAmount" + singleItem.itemId).val();
            var popup = document.getElementById("myPopup-" + singleItem.itemId);
            if(singleItem.purchaseCategory.indexOf("QUANTITY") > -1) {
                if(amount.indexOf(".") > -1) {
                    displayPopup("Please enter whole number",popup);
                } else {
                    addItemToMap(singleItem.itemId,parseFloat(amount));
                    displayPopup("Item Successfuly Added",popup);
                    if(itemIdToAmount.size > 0) {
                        $("#nextStepButton").attr("disabled",false);
                    }
                }
            } else {
                addItemToMap(singleItem.itemId,parseFloat(amount));
                displayPopup("Item Successfuly Added",popup);
                if(itemIdToAmount.size > 0) {
                    $("#nextStepButton").attr("disabled",false);
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
        "</form>" +
        "<div class=\"row\"> <button id=\"nextStepButton\" class=\"btn btn-primary mb-2\" disabled=\"disabled\" >Next</button></div>\n");

    $.each(areaStores.stores || [], function(index, store) {
        $("#storesDropdown").append(
            "<option>" + store.storeId + ": " + store.storeName + "</option>\n");
    });

    $("#newOrderForm").append(
        "<input type=\"date\" id=\"date-input\" class=\"orderFormControl\" name=\"orderDate\" required>\n" +
        "<input type=\"text\" class=\"form-control orderFormControl\" id=\"xStoreLocation\" placeholder=\"X Coordinate\" required pattern=\"^(50|[1-4]?[0-9])$\">" +
        "<input type=\"text\" class=\"form-control orderFormControl\" id=\"yStoreLocation\" placeholder=\"Y Coordinate\" required pattern=\"^(50|[1-4]?[0-9])$\">" +
        "<button type=\"submit\" class=\"btn orderFormControl\" id=\"orderOptionSubmit\" required> Show Items </button>" +
        "<div id = \"alert_placeholder\"></div>\n");

    $("#newOrderForm").submit(function () {
            method = $("#orderMethodDropdown").val();
            store = $("#storesDropdown").val();
            date = $("#date-input").val();
            xLocation = $("#xStoreLocation").val();
            yLocation = $("#yStoreLocation").val();

            $.ajax({
                method: 'GET',
                data: "methodKey=" + method + "&storeKey=" + store + "&dateKey=" + date + "&xLocationKey=" + xLocation + "&yLocationKey=" + yLocation + "&actionType=getOrderItemsOption",
                url: "customer",
                error: function (jqXHR) {
                   bootstrap_alert.warning(jqXHR.responseText,"red");
                },
                success: function (r) {
                    $("#orderMethodDropdown").attr("disabled",true);
                    $("#storesDropdown").attr("disabled",true);
                    $("#date-input").attr("disabled",true);
                    $("#xStoreLocation").attr("disabled",true);
                    $("#yStoreLocation").attr("disabled",true);
                    $("#orderOptionSubmit").attr("disabled",true);
                    if(method.indexOf("Static Order") > -1) {
                        $("#nextStepButton").click(function () {
                            getDiscounts();
                        });
                        displayStaticItemsOption(r);
                    } else if(method.indexOf("Dynamic Order") > -1) {
                        $("#nextStepButton").click(function () {
                            getMinimalCart();
                        });
                        displayDynamicItemsOption(r);
                    }
                }
            });
        return false;
    });
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
            clearOrderData();
            makeNewOrderForm(r);
        }
    });
}