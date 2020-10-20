function displayFillFeedback(feedbackStores) {

    $(".dynamic-container").empty();
    $(".dynamic-container").append(
        "<section class=\"our-webcoderskull padding-lg\">\n" +
        "    <div class=\"container feedback-container\">\n" +
        "        <div class=\"row heading heading-icon\">\n" +
        "            <p class=\"newOrderFeedbackHeader\">Items</p>\n" +
        "        </div>\n" +
        "        <ul class=\"row items-list newOrderFeedbackList\">\n" +
        "        </ul>\n" +
        "    </div>\n" +
        "</section>");


    $.each(feedbackStores.orderStores || [], function(index, singleFeedbackStore) {

        var feedbackImageUrl = "../../common/images/rating.png";

        $(".items-list").append(
            "<li class=\"col-12 col-md-6 col-lg-3 feedback-card\">\n" +
            "       <div class=\"cnt-block equal-hight newOrderFeedbackCard\">\n" +
            "            <figure><img src=" + feedbackImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
            "             <h3 class=\"store-name\">" + singleFeedbackStore.storeName + "</h3>" +
            "           <p id=\"storeId\" class=\"newOrderFeedbackStore\">Store Id: " + singleFeedbackStore.storeId + " </p>\n" +
            "<form id=\"new-order-feedback-form-" + singleFeedbackStore.storeId + "\">" +
            "<p>Rate Your Order Experience From " + singleFeedbackStore.storeName + "</p>" +
            "<label class=\"radio-inline\">\n" +
            "      <input type=\"radio\" name=\"ratingOption" + singleFeedbackStore.storeId + "\">1\n" +
            "    </label>\n" +
            "    <label class=\"radio-inline\">\n" +
            "      <input type=\"radio\" name=\"ratingOption" + singleFeedbackStore.storeId + "\">2\n" +
            "    </label>\n" +
            "    <label class=\"radio-inline\">\n" +
            "      <input type=\"radio\" name=\"ratingOption" + singleFeedbackStore.storeId + "\">3\n" +
            "    </label>" +
            "    <label class=\"radio-inline\">\n" +
            "      <input type=\"radio\" name=\"ratingOption" + singleFeedbackStore.storeId + "\">4\n" +
            "    </label>" +
            "    <label class=\"radio-inline\">\n" +
            "      <input type=\"radio\" name=\"ratingOption" + singleFeedbackStore.storeId + "\" checked>5\n" +
            "    </label>" +
            "<textarea id=\"feedbackReview" + singleFeedbackStore.storeId + "\" rows=\"20\" cols=\"20\" placeholder=\"Write Your Review\">\n" +
            "  </textarea>" +
            " <button id=\"addFeedback" + singleFeedbackStore.storeId + "\" type=\"submit\" class=\"btn btn-primary mb-2 newOrderFeedbackSubmit\">Add Feedback</button>\n" +
            "</form>" +
            "        <div id=\"add-item-popup\" class=\"popup\" >\n" +
            "            <span class=\"popuptext\" id=\"myPopup-" + singleFeedbackStore.storeId + "\" ></span>" +
            "        </div> \n" +
            "       </div>" +
            " </li>\n");
        $("#new-order-feedback-form-" + singleFeedbackStore.storeId).submit(function () {
            /*var amount =  $("#itemAmount" + singleItem.itemId).val();*/
            var popup = document.getElementById("myPopup-" + singleFeedbackStore.storeId);
            var chosenFeedbackRate = document.querySelector("input[name=ratingOption" + singleFeedbackStore.storeId + "]:checked").value;
            var feedbackReview = $("#feedbackReview" + singleFeedbackStore.storeId).val();

            $.ajax({
                method: 'POST',
                data: "&actionType=addStoreFeedback" + "&storeIdKey=" + singleFeedbackStore.storeId + "&chosenFeedbackRateKey=" + chosenFeedbackRate + "&feedbackReviewKey=" + feedbackReview + "&orderDateKey=" + feedbackStores.orderDate,
                url: "customer",
                error: function (jqXHR) {
                },
                success: function (r) {
                    popupSuccess("Feedback Successfully Sent To Shop Owner",popup);

                }
            });



            return false;
        });
    });


}