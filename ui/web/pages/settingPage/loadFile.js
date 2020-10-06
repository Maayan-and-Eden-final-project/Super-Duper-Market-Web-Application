function getUserType() {
    $.ajax({
        method: 'GET',
        url: "uploadFile",
        timeout: 4000,
        error: function (e) {
        },
        success: function (r) {
            if(r.indexOf("CUSTOMER") > -1) {
                $("#uploadFileTab").addClass("customer-type");
            }
        }
    });
}

$(function () {
   getUserType();
});

$(function () { // onload...do
    //add a function to the submit event
    $("#uploadFileTab").click(function () {
        $(".dynamic-container").children().remove();
        $(".dynamic-container").append(
            " <h3>Select a File:</h3>\n" +
            "        <form id=\"uploadFileForm\" action=\"uploadFile\" enctype=\"multipart/form-data\" method=\"POST\">\n" +
            "            <input id=\"file1\" type=\"file\" class='btn btn-info btn-outline-info' accept=\".xml\"><br>" +
            "            <input type=\"Submit\"  class=\"btn btn-info btn-outline-info\" value=\"Upload File\"> " +
            "        </form> \n" +
            "        <div class=\"popup\" >\n" +
            "            <span class=\"popuptext\" id=\"myPopup\"></span>" +
            "        </div> \n");

        $("#uploadFileForm").submit(function () {
            var file1 = this[0].files[0];

            var formData = new FormData();
            formData.append("fake-key-1", file1);

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
        });

    });
});

