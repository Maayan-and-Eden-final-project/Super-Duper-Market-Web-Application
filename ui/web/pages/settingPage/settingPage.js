/*var FILE_URL = buildUrlWithContextPath("uploadFile");*/

$(function() { // onload...do
    //add a function to the submit event
    $("#uploadFileTab").click(function() {
        $(".dynamic-container").children().remove();
        $(".dynamic-container").append(
            " <h3>Select a File:</h3>\n" +
            "        <form id=\"uploadFileForm\" action=\"uploadFile\" enctype=\"multipart/form-data\" method=\"POST\">\n" +
            "            <input id=\"file1\" type=\"file\" name=\"file1\"><br>\n" +
            "            <input type=\"Submit\" value=\"Upload File\"><br>\n" +
            "        </form>");
        $("#uploadFileForm").submit(function () {
            var file1 = this[0].files[0].name;

            var formData = new FormData();
            formData.append("fake-key-1", file1);

            $.ajax({
                method:'POST',
                data: "file1=" + file1,
                url: this.action,
                timeout: 4000,
                error: function(e) {
                    console.error("Failed to submit");
                },
                success: function(r) {
                }
            });

            // return value of the submit operation
            // by default - we'll always return false so it doesn't redirect the user.
            return false;
        })
    });
});
