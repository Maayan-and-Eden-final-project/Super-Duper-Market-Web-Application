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
        data: "&actionType=pullMessages",
        dataType: 'json',
        success: function(data) {

            if (data.length > 0) {
                appendToMessageArea(data);
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

$(function() {
    triggerAjaxMessageContent();
});
