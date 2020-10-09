$(function () {
    $("#areasTab").click(function () {
        $(".dynamic-container").children().remove();
        $.ajax({
            method: 'GET',
            url: "areas",
            timeout: 4000,
            dataType: "json",
            error: function (e) {
                console.error("test");
            },
            success: function (r) {
                refreshAreasList(r);
            }
        });
    });
});

function refreshAreasList(areas) {
    $(".dynamic-container").append(
        "<div class=\"container areas-container\">\n" +
        "<h2 id=\"areas-header\">AREAS</h2>" +
        "    <div id=\"areasHolder\" class=\"card-deck mb-3 text-center\">\n" +
        "</div>\n" +
        "</div>\n"
        );

    $.each(areas || [], function(index, areaContainer) {

        $("#areasHolder").append(
        "<div class=\"card mb shadow-sm\">\n" +
        "    <div class=\"card-header\">\n" +
        "       <p class=\"my-0 font-weight-normal area-card-header\">" + areaContainer.areaName + "</p>\n" +
        "    </div>\n" +
        "    <div class=\"card-body\">\n" +
        "       <ul class=\"list-unstyled mt-3 mb-4 area-info\">\n" +
            "       <li class=\"area-list-item\">Area Owner: " + areaContainer.areaOwner + "</li>\n" +
            "       <li class=\"area-list-item\">Number Of Stores: " + areaContainer.storesInArea + " </li>\n" +
        "           <li class=\"area-list-item\">Number Of Items: " + areaContainer.itemsInArea + "</li>\n" +
        "           <li class=\"area-list-item\">Number Of Orders: " + areaContainer.ordersInArea + "</li>\n" +
        "           <li class=\"area-list-item\">Average Orders Costs: " + areaContainer.avgOrdersCostsInArea + "</li>\n" +
        "       </ul>\n" +
            "</div>\n" +
            "<form class=\"area-form\" action=\"areas\" method=\"post\">\n" +
            "       <button type=\"submit\" class=\"btn single-area-button\">Go To Area</button>\n" +
            "    <input type=\"hidden\" id=\"areaKey\" name=\"areaKey\" value=" + areaContainer.areaName + ">\n" +
            "</form>" +
        "</div>\n");
    });
}
