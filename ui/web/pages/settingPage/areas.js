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
        "<div class=\"container\">\n" +
        "    <div id=\"areasHolder\" class=\"card-deck mb-3 text-center\">\n" +
        "</div>\n" +
        "</div>\n"
        );

    $.each(areas || [], function(index, areaContainer) {

        $("#areasHolder").append(
        "<div class=\"card mb shadow-sm\">\n" +
        "    <div class=\"card-header\">\n" +
        "       <h4 class=\"my-0 font-weight-normal\">" + areaContainer.areaName + "</h4>\n" +
        "    </div>\n" +
        "    <div class=\"card-body\">\n" +
        "       <h3 class=\"card-title pricing-card-title\">Area Owner: <small class=\"text-muted\">" + areaContainer.areaOwner + " </small></h3>\n" +
        "       <ul class=\"list-unstyled mt-3 mb-4\">\n" +
        "           <li class=\"area-list-item\">Number Of Stores:" + areaContainer.storesInArea + " </li>\n" +
        "           <li class=\"area-list-item\">Number Of Items:" + areaContainer.itemsInArea + "</li>\n" +
        "           <li class=\"area-list-item\">Number Of Orders:" + areaContainer.ordersInArea + "</li>\n" +
        "           <li class=\"area-list-item\">Average Orders Costs:" + areaContainer.avgOrdersCostsInArea + "</li>\n" +
        "       </ul>\n" +
        "       <button type=\"button\" class=\"btn btn-lg btn-block btn-outline-primary\">Go To Area</button>\n" +
        "    </div>\n" +
        "</div>\n");
    });
}

