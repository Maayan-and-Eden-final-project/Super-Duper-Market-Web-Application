var messageVersion = 0;
var MESSAGE_LIST_URL = buildUrlWithContextPath("pages/areaMainPage/shopOwner");


function appendToMessageArea(entries) {
    $("#messages-body").empty();
    // add the relevant entries
    $.each(entries || [], createMessageModal);
    $("#messageModal").modal("show");

}


function createMessageModal(index, entry){
    $("#messages-body").append("<p>" + entry + "</p>");
}


function ajaxMessageContent() {
    $.ajax({
        url: MESSAGE_LIST_URL,
        data: "messageVersion=" + messageVersion + "&actionType=pullMessages",
        dataType: 'json',
        success: function(data) {

            if (data.version !== messageVersion) {
                messageVersion = data.version;
                appendToMessageArea(data.entries);
            }
            triggerAjaxMessageContent();
        },
        error: function(error) {
            triggerAjaxMessageContent();
        }
    });
}


function triggerAjaxMessageContent() {
    setTimeout(ajaxMessageContent, 2000);
}

/*$(function() {
    $("#messageButton").click(function () {
        /!*$("#closed-button").click();
        $("#messageModal").modal({show:true});*!/
        $("#messageModal").modal("show");

    });
});*/

$(function() {
    triggerAjaxMessageContent();
});
