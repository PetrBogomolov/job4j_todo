const req = $.ajax({
    type: "POST",
    url: "./categories",
    dataType: "json"
})
req.done(function (data) {
    let categories = ""
    for (let i = 0; i < data.length; i++) {
        categories += "<option value="+data[i]["id"]+">"
            + data[i]["name"]
            + "</option>>"
    }
    $('#cIds').html(categories)
})