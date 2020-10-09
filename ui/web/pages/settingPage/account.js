var accountTableInterval;

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
                    "  <div class=\"form-group mx-sm-3 mb-2 amount-text-field\">\n" +
                    "    <input type=\"float\" class=\"form-control\" id=\"amount-input\" placeholder=\"Amount\" required \n>\n" +
                    "  </div>\n" +
                    "  <input type=\"date\" id=\"date-input\" name=\"birthday\" required>\n" +
                    "  <button id=\"add-funds-button\" type=\"submit\" class=\"btn btn-primary mb-2 \">Add Funds To My Wallet</button>\n" +
                    "<div class=\"add-fund popup\" >\n" +
                    "     <span class=\"popuptext\" id=\"myPopup\"></span>" +
                    "</div> \n" +
                    "</form>");

                $("#form-account").submit((function () {
                    var amount = $("#amount-input").val();
                    var date =  $("#date-input").val();
                    var formData = new FormData();
                    formData.append("fake-key-1", amount);
                    formData.append("fake-key-2", date);

                    $.ajax({
                        method: 'POST',
                        data: "amountKey=" + amount + "&dateKey=" + date,
                        url: this.action,
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
                    $("#amount-input").val("");
                    $("#date-input").val("");
                    return false;

                }));
            }
            $(".dynamic-container").append("<div class=\"account-actions\"><table id=\"actions-table\" class=\"table table-hover\">\n" +
                "  <thead>\n" +
                "    <tr>\n" +
                "      <th scope=\"col\">Date</th>\n" +
                "      <th scope=\"col\">Action</th>\n" +
                "      <th scope=\"col\">Amount</th>\n" +
                "      <th scope=\"col\">Balance Before Action</th>\n" +
                "      <th scope=\"col\">Balance After Action</th>\n" +
                "    </tr>\n" +
                "  </thead>\n" +
                "  <tbody id=\"actions-holder\">\n" +
                "  </tbody>\n" +
                "</table></div>");
            refreshAccountTable();
        }
    });
}

function refreshAccountActionsTable(accountActions) {
    $("#actions-holder").empty();
    $.each(accountActions.actions || [], function(index, action) {
        $("#actions-holder").append(
            "    <tr>\n" +
            "      <th scope=\"row\">" + action.actionDate + "</th>\n" +
            "      <td>" + action.accountAction + "</td>\n" +
            "      <td>" + action.actionAmount + "</td>\n" +
            "      <td>" + action.balanceBeforeAction + "</td>\n" +
            "      <td>" + action.balanceAfterAction + "</td>\n" +
            "    </tr>\n"
        );
    });
}

function refreshAccountTable() {
    $.ajax({
        method: 'GET',
        url: "account",
        timeout: 4000,
        dataType: "json",
        error: function (e) {
        },
        success: function (r) {
            refreshAccountActionsTable(r);
        }
    });
}

$(function () {
    $("#accountTab").click(function () {
        $(".dynamic-container").children().remove();
        getUserTypeForAccount();
        accountTableInterval = setInterval(refreshAccountTable, 1500);

    });
});
