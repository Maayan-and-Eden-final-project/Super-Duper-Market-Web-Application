$(function() { // onload...do
    //add a function to the submit event
    $("#uploadFileTab").click(function() {
        $(".dynamic-container").children().remove();
        $(".dynamic-container").append(
            " <h3>Select a File:</h3>\n" +
            "        <form id=\"uploadFileForm\" action=\"uploadFile\" enctype=\"multipart/form-data\" method=\"POST\">\n" +
            "            <input id=\"file1\" type=\"file\" name=\"file1\" accept=\".xml\"><br>\n" +
            "            <div id=\"popup\"></div> \n" +
            "<input type=\"Submit\" value=\"Upload File\"><br>\n" +
            "        </form>");


        $("#uploadFileForm").submit(function () {
            var file1 = this[0].files[0];

            var formData = new FormData();
            formData.append("fake-key-1", file1);

            $.ajax({
                method:'POST',
                data: formData,
                url: this.action,
                processData: false,
                contentType: false,
                timeout: 4000,
                error: function(e) {
                    $("#popup").append(e);
                    /*  $("#myPopup").classList.toggle("show");*/
                    console.error("Failed to submit");
                },
                success: function(r) {
                    $("#popup").append(r);
                    /* $("#myPopup").classList.toggle("show");*/
                }
            });
            return false;
        });

    });


    });

