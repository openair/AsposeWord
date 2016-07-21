
var query = "";
var selectMethod = "";

$(document).ready(function() {
    $("#chooseFile").on("click", function() {
        if ($(this).val() == "Choose File")
            $(this).val("");
    });
});


function selectConversionFormat() {
    var selectCtrl = document.getElementById("conversionFormat");
    var selectedItem = selectCtrl.options[selectCtrl.selectedIndex];
    query += selectedItem.text;
}

function fireRestMethods() {
    debugger;
    var selectCtrl = document.getElementById("restMethods");
    var selectedItem = selectCtrl.options[selectCtrl.selectedIndex];
    if (selectedItem.text == "PUT /words/convert") {
        query = "";
        selectMethod="";
        document.getElementById("conversionFormat").style.display = "block";
        query += selectedItem.text + ", ";
        selectMethod = "putConvert";
    }
    else if (selectedItem.text == "POST /words/{name}/protection") {
        query = "";
        selectMethod="";
        document.getElementById("conversionFormat").style.display = "none";
        query += selectedItem.text + ", ";
        selectMethod = "postProtect";
        //alert("POST /words/{name}/protection");
    }
    else if (selectedItem.text == "GET /words/{name}/protection") {
        query = "";
        selectMethod="";
        document.getElementById("conversionFormat").style.display = "none";
        query += selectedItem.text + ", ";
        selectMethod = "getProtect";
       // alert("GET /words/{name}/protection");
    }
}

function findFeatures() {
    //document.getElementById("output").style.display = "block";
    debugger;
    if (selectMethod == "putConvert") {
        query += ", " + document.getElementById("chooseFile").value;
        //alert(query);
        putConvertMethod(query);
    }
    else if (selectMethod == "postProtect") {
        query += document.getElementById("chooseFile").value;
        // alert(query);
        postProtectMethod(query);
    }
    else if (selectMethod == "getProtect") {
        query += document.getElementById("chooseFile").value;
        // alert(query);
        getProtectMethod(query);
    }

}

function putConvertMethod(query) { //put convert method
    $.get('ServletClass?name=put&name=' + query, function(responseJson) {    // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
        $("#output span").text("Response Code: " + responseJson.toString()); // Create HTML <option> element, set its value with currently iterated key and its text content with currently iterated item and finally append it to the <select>.

    });
}

function postProtectMethod(query) { //post protect method
    debugger;
    $.get('ServletClass?name=post&name=' + query, function(responseJson) {    // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
        $("#output span").text("Response Code: " + responseJson.toString()); // Create HTML <option> element, set its value with currently iterated key and its text content with currently iterated item and finally append it to the <select>.
       });
    }
    
function getProtectMethod(query) { //get protect method
    $.get('ServletClass?name=get&name=' + query, function(responseJson) {    // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
        $("#output span").text("Response Code: " + responseJson.toString()); // Create HTML <option> element, set its value with currently iterated key and its text content with currently iterated item and finally append it to the <select>.
        });
    }


