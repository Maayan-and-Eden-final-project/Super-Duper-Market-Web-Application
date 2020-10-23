var feedbackInterval;

function displayFillFeedback(feedbackStores) {

    $(".dynamic-container").empty();
    $(".dynamic-container").append(
        "<section class=\"our-webcoderskull padding-lg\">\n" +
        "    <div class=\"container feedback-container\">\n" +
        "        <div class=\"row heading heading-icon\">\n" +
        "            <p class=\"newOrderFeedbackHeader\">Write A Feedback</p>\n" +
        "        </div>\n" +
        "        <ul class=\"row items-list newOrderFeedbackList\">\n" +
        "        </ul>\n" +
        "    </div>\n" +
        " <button id=\"finishOrder\" class=\"btn btn-primary mb-2 newOrderFinishSubmit\">Finish</button>\n" +
        "</section>");

    $("#finishOrder").click(function () {
        $(".dynamic-container").empty();
    });


    $.each(feedbackStores.orderStores || [], function(index, singleFeedbackStore) {

        var feedbackImageUrl = "../../common/images/rating.png";

        $(".items-list").append(
            "<li class=\"col-12 col-md-6 col-lg-3 feedback-card\">\n" +
            "       <div class=\"cnt-block equal-hight newOrderFeedbackCard\">\n" +
            "            <figure><img src=" + feedbackImageUrl + " class=\"img-responsive\"  alt=\"\"></figure>\n" +
            "             <h3 class=\"feedback-store-name\">" + singleFeedbackStore.storeName + "</h3>" +
            "           <p id=\"storeId\" class=\"newOrderFeedbackStore\">Store Id: " + singleFeedbackStore.storeId + " </p>\n" +
            "<form id=\"new-order-feedback-form-" + singleFeedbackStore.storeId + "\" class=\"feedbackCard\">" +
            "<p class=\"newOrderFeedbackStore\">Rate Your Order Experience From " + singleFeedbackStore.storeName + "</p>" +
            "<label class=\"radio-inline\">\n" +
            "      <input type=\"radio\" class=\"ratingOption" + singleFeedbackStore.storeId + "\" name=\"ratingOption" + singleFeedbackStore.storeId + "\">1\n" +
            "    </label>\n" +
            "    <label class=\"radio-inline\">\n" +
            "      <input type=\"radio\" class=\"ratingOption" + singleFeedbackStore.storeId + "\" name=\"ratingOption" + singleFeedbackStore.storeId + "\">2\n" +
            "    </label>\n" +
            "    <label class=\"radio-inline\">\n" +
            "      <input type=\"radio\" class=\"ratingOption" + singleFeedbackStore.storeId + "\" name=\"ratingOption" + singleFeedbackStore.storeId + "\">3\n" +
            "    </label>" +
            "    <label class=\"radio-inline\">\n" +
            "      <input type=\"radio\" class=\"ratingOption" + singleFeedbackStore.storeId + "\" name=\"ratingOption" + singleFeedbackStore.storeId + "\">4\n" +
            "    </label>" +
            "    <label class=\"radio-inline\">\n" +
            "      <input type=\"radio\" class=\"ratingOption" + singleFeedbackStore.storeId + "\" name=\"ratingOption" + singleFeedbackStore.storeId + "\" checked>5\n" +
            "    </label>" +
            "<textarea id=\"feedbackReview" + singleFeedbackStore.storeId + "\" rows=\"10\" cols=\"50\" class=\"feedbackReview\" placeholder=\"Write Your Review\"></textarea>\n" +
            " <button id=\"addFeedback" + singleFeedbackStore.storeId + "\" type=\"submit\" class=\"btn btn-primary mb-2 newOrderFeedbackSubmit\">Add Feedback</button>\n" +
            "</form>" +
            "        <div class=\"popup add-item-popup\" >\n" +
            "            <span class=\"popuptext\" id=\"myPopup-" + singleFeedbackStore.storeId + "\" ></span>" +
            "        </div> \n" +
            "       </div>" +
            " </li>\n");
        $("#new-order-feedback-form-" + singleFeedbackStore.storeId).submit(function () {
            var popup = document.getElementById("myPopup-" + singleFeedbackStore.storeId);
            var chosenFeedbackRate = document.querySelector("input[name=ratingOption" + singleFeedbackStore.storeId + "]:checked").labels[0].innerText;
            var feedbackReview = $("#feedbackReview" + singleFeedbackStore.storeId).val();

            $.ajax({
                method: 'POST',
                data: "&actionType=addStoreFeedback" + "&storeIdKey=" + singleFeedbackStore.storeId + "&chosenFeedbackRateKey=" + chosenFeedbackRate + "&feedbackReviewKey=" + feedbackReview + "&orderDateKey=" + feedbackStores.orderDate,
                url: "customer",
                error: function (jqXHR) {
                },
                success: function (r) {
                    displayPopup("Feedback Successfully Sent To Shop Owner",popup);
                    $("#addFeedback" + singleFeedbackStore.storeId).attr("disabled",true);
                    $(".ratingOption" + singleFeedbackStore.storeId).attr("disabled",true);
                    $("#feedbackReview" + singleFeedbackStore.storeId).attr("disabled",true);
                }
            });

            return false;
        });
    });
}


function displayShopOwnerFeedback(feedback) {
    $(".dynamic-container").empty();
    $(".dynamic-container").append(
        "<p class=\"feedback-header\"> Feedback </p>" +
        "<div class=\"row feedback-main-holder\">\n" +
        "<div class=\"col-sm-12\">\n" +
        "<div id=\"feedback-container\" class=\"review-block\">\n");


    $.each(feedback|| [], function(index, singleFeedbackStore) {

        var feedbackImageUrl = "../../common/images/customer.png";

        $("#feedback-container").append(
            "<div class=\"row\">\n" +
            "<div class=\"col-sm-3 feedback-card\">\n" +
            "<img src=" + feedbackImageUrl + " class=\"img-rounded feedbackImage\" >\n" +
            "<div class=\"review-block-name\">" + singleFeedbackStore.reviewerName + " </div>\n" +
            "<div class=\"review-block-date\">" + singleFeedbackStore.orderDate + " <br/> Store Id: " + singleFeedbackStore.storeId + " </div>\n" +
            "</div>\n" +
            "<div class=\"col-sm-9\">\n" +
            "<div class=\"review-block-rate\">\n" +
            "<button id=\"starbutton1" + index + "\" type=\"button\" class=\"btn btn-default btn-grey btn-xs star-button\" aria-label=\"Left Align\">\n" +
            "</button>\n" +
            "<button id=\"starbutton2" + index + "\" type=\"button\" class=\"btn btn-default btn-grey btn-xs star-button\" aria-label=\"Left Align\">\n" +
            "</button>\n" +
            "<button id=\"starbutton3" + index + "\" type=\"button\" class=\"btn btn-default btn-grey btn-xs star-button\" aria-label=\"Left Align\">\n" +
            "</button>\n" +
            "<button id=\"starbutton4" + index + "\" type=\"button\" class=\"btn btn-default btn-grey btn-xs star-button\" aria-label=\"Left Align\">\n" +
            "</button>\n" +
            "<button id=\"starbutton5" + index + "\" type=\"button\" class=\"btn btn-default btn-grey btn-xs star-button\" aria-label=\"Left Align\">\n" +
            "</button>\n" +
            "</div>\n" +
            "<div class=\"review-block-description\">" + singleFeedbackStore.review + " </div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<hr/>\n");

       var userRate = singleFeedbackStore.rate;

       for (var i=1; i<=userRate; i++) {
            document.getElementById("starbutton" + i + index).classList.toggle("btn-default");
           document.getElementById("starbutton" + i + index).classList.toggle("btn-grey");
           document.getElementById("starbutton" + i + index).classList.toggle("btn-warning");
       }
    });
}

function getShopOwnerFeedback() {

    $.ajax({
        method: 'GET',
        url: "shopOwner",
        data: "actionType=getFeedback",
        timeout: 4000,
        error: function (e) {
        },
        success: function (r) {
            displayShopOwnerFeedback(r);
        }
    });

}

function makeFeedbackInterval() {
    feedbackInterval = setInterval(getShopOwnerFeedback, 1500);
}
