var storeIdToItemPrice = new Map();

function displayStoresOption(stores) {

    $(".dynamic-container").append(
        "<section class=\"our-webcoderskull padding-lg\">\n" +
        "    <div class=\"container add-item-stores-container\">\n" +
        "        <div class=\"row heading heading-icon\">\n" +
        "            <p class=\"addItemStoresHeader\">Stores</p>\n" +
        "        </div>\n" +
        "        <ul class=\"row add-item-stores-list addItemStoresList\">\n" +
        "        </ul>\n" +
        "    </div>\n" +
        "</section>");


    $.each(stores || [], function(index, singleStore) {

        var storeImageUrl = "../../common/images/storeIcon.png";

        $(".add-item-stores-list").append(
            "<li class=\"col-12 col-md-6 col-lg-3 add-item-store-card\">\n" +
            "       <div class=\"cnt-block equal-hight addItemStoreCard\">\n" +
            "            <figure><img src=" + storeImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
            "             <h3 class=\"store-name\">" + singleStore.storeName + "</h3>" +
            "           <p id=\"storeId\" class=\"addItemSingleStore\">Store Id: " + singleStore.storeId + " </p>\n" +
            "<form id=\"add-item-store-form-" + singleStore.storeId + "\">" +
            "<input type=\"number\" min=0 class=\"form-control addStoreItemPrice\" id=\"itemPrice" + singleStore.storeId + "\" placeholder=\"Price\" required>" +
            " <button id=\"addItemToStore" + singleStore.storeId + "\" type=\"submit\" class=\"btn btn-primary mb-2 addStoreItemSubmit\">Add Item To Store</button>\n" +
            "</form>" +
            "        <div class=\"popup add-store-popup\" >\n" +
            "            <span class=\"popuptext\" id=\"myPopup-" + index + "\" ></span>" +
            "        </div> \n" +
            "       </div>" +
            " </li>\n");
        $("#add-item-store-form-" + singleStore.storeId).submit(function () {
            var itemPrice = $("#itemPrice" + singleStore.storeId).val();
            storeIdToItemPrice.set(singleStore.storeId, itemPrice); //TODO: empty when back clicked
            $("#itemPrice" + singleStore.storeId).val("");
            var popup = document.getElementById("myPopup-" + index);
            displayPopup("Item Successfully Added",popup);
            return false;
        });
    });
}


function makeAddNewItemForm(stores) {
    $(".dynamic-container").empty();
    $(".dynamic-container").append(
        "<p class=\"addItemHeader\">Add New Item</p>\n" +
        "<form id=\"newItemForm\" action=\"\" class=\"form-inline\">" +
        "<input type=\"text\" class=\"form-control addItemFormControl\" id=\"itemName\" placeholder=\"Item Name\" required>" +
        "  <select id=\"purchaseCategoryDropdown\" class=\"addItemFormControl\" required>\n" +
        "<option value=\"\">Choose Purchase Category</option>\n" +
        "<option>QUANTITY</option>\n" +
        "<option>WEIGHT</option>\n" +
        "  </select>\n" +
        "<button type=\"submit\" class=\"btn addItemFormControl\" id=\"addItemSubmit\" required> FINISH </button>" +
        "<div id = \"alert_placeholder\"></div>\n" +
        "</form>");


    $("#newItemForm").submit(function () {
        if(storeIdToItemPrice.size === 0) {
            $('#addItemSubmit').on('click', function() {
                bootstrap_alert.warning('You Must Choose At Least One Selling Store  ',"red");
            });
            $('#addItemSubmit').click();
        } else {

            var purchaseCategory = $("#purchaseCategoryDropdown").val();
            var itemName = $("#itemName").val();
            var storeIdToItemPriceString = JSON.stringify(Array.from(storeIdToItemPrice));

            $.ajax({
                method: 'POST',
                data: "purchaseCategoryKey=" + purchaseCategory + "&itemNameKey=" + itemName + "&storesListKey=" + storeIdToItemPriceString + "&actionType=addItem",
                url: "shopOwner",
                error: function (jqXHR) {
                    bootstrap_alert.warning(jqXHR.responseText,"red");
                },
                success: function (r) {
                    bootstrap_alert.warning(r,"green");
                    storeIdToItemPrice.clear();
                    $("#purchaseCategoryDropdown").val("");
                    $("#itemName").val("");

                    $(".addStoreItemPrice").val("");
                }
            });

        }
        return false;
    });
    displayStoresOption(stores);
}

function addNewItem() {
    $.ajax({
        method: 'GET',
        url: "../settingPage/areas",
        data: "actionType=storesForAddItem",
        timeout: 4000,
        dataType: "json",
        error: function (e) {
            console.error("test");
        },
        success: function (r) {
            makeAddNewItemForm(r);
        }
    });
}