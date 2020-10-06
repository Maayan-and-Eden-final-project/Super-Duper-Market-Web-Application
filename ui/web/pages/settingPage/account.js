function getUserTypeForAccount() {
    $.ajax({
        method: 'GET',
        url: "uploadFile",
        timeout: 4000,
        error: function (e) {
        },
        success: function (r) {
            if(r.indexOf("CUSTOMER") > -1) {
                $(".dynamic-container").append("<form id=\"form-account\" action=\"account\" class=\"form-inline\">\n" +
                    "  <div class=\"form-group mx-sm-3 mb-2\">\n" +
                    "    <input type=\"float\" class=\"form-control\" id=\"amount-input\" placeholder=\"Amount\" required \n>\n" +
                    "  </div>\n" +
                    "  <input type=\"date\" id=\"date-input\" name=\"birthday\" required>\n" +
                    "  <button type=\"submit\" class=\"btn btn-primary mb-2\">Add Money To My Account</button>\n" +
                    "</form>" +
                    "<div class=\"popup\" >\n" +
                    "     <span class=\"popuptext\" id=\"myPopup\"></span>" +
                    "</div> \n");

                $("#form-account").submit((function () {
                    var amount = $("#amount-input").val();
                    var date =  $("#date-input").val();
                    var formData = new FormData();
                    formData.append("fake-key-1", amount);
                    formData.append("fake-key-2", date);

                    $.ajax({
                        method: 'POST',
                        data: formData,
                        url: this.action,
                        processData: false,
                        contentType: false,
                        timeout: 4000,
                        error: function (e) {
                            var popup = document.getElementById("myPopup");
                            popup.innerText = e;
                            popup.classList.toggle("show");
                            setTimeout(function () {
                                popup.classList.toggle("show"); //close the tooltip
                            }, 2000);
                        },
                        success: function (r) {
                            var popup = document.getElementById("myPopup");
                            popup.innerText = r;
                            popup.classList.toggle("show");
                            setTimeout(function () {
                                popup.classList.toggle("show"); //close the tooltip
                            }, 2000);

                        }
                    });
                    return false;

                }));
            }
        }
    });
}

$(function () {
    $("#accountTab").click(function () {
        $(".dynamic-container").children().remove();
        getUserTypeForAccount();

    });
});