function validate() {
    let message = '* - обязательные для заполнения поля'
    let description = $('#description').val();
    if (description === '') {
        alert(message);
        return false;
    }
    return true;
}

$(document).ready(function () {
    const req = $.ajax({
        type: "POST",
        url: './showFilter',
        dataType: "json"
    })
    req.done(function (data) {
        let items = ""
        for (let i = 0; i < data.length; i++) {
            items += "<tr>"
                + "<td>" + data[i]["id"] + "</td>"
                + "<td>" + data[i]["description"] + "</td>"
                + "<td>" + data[i]["created"] + "</td>"
                + "<td>" + "Необходимо выполнить  "
                + "<input type='checkbox' id='setStatus' value=" + data[i]["id"] + ">"
                + "</td>"
                + "</tr>"
        }
        $('#items').html(items);
        $('input:checked:not(:disabled)').each(function () {
            for (let i = 0; i < data.length; i++) {
                $('#items').append(
                    "<tr>"
                    + "<td>" + data[i]["description"] + "</td>"
                    + "<td>" + data[i]["created"] + "</td>"
                    + "<td>" + "Выполнено  "
                    + "<input disabled type='checkbox' id='setStatus' checked value=" + data[i]["id"] + ">"
                    + "</td>"
                    + "</tr>");
            }
        });
    });
    $('#items').html(items);
})

function addItem() {
    if (validate()) {
        $.ajax({
                method: 'POST',
                url: './add',
                data: {desc: $("#description").val()},
                dataType: 'json'
            }
        );
    }
    location.reload()
}

function getSelectedValues() {
    let selectedValues =[]
    $('input[id=setStatus]:checked:not(:disabled)').each(function(i,e){
        selectedValues.push($(e).attr('value') )
    })
    return selectedValues;
}

function markAsDone() {
    let selectedValues = getSelectedValues()
    if (selectedValues.length > 0) {
        for (let i = 0; i < selectedValues.length; i++) {
            $.ajax({
                method: 'POST',
                url: './done',
                data: {id: selectedValues[i]},
                dataType: 'json'
            });
        }
        location.reload()
    } else {
        alert("Необходмио выбрать задания")
    }
}

function showAll() {
    $(document).ready(function () {
        const req = $.ajax({
            type: "POST",
            url: "./showAll",
            dataType: "json"
        })
        req.done(function (data) {
            let items = ""
            for (let i = 0; i < data.length; i++) {
                items += "<tr>"
                    + "<td>" + data[i]["id"] + "</td>"
                    + "<td>" + data[i]["description"] + "</td>"
                    + "<td>" + data[i]["created"] + "</td>"
                    + "<td>" + data[i]["done"] + "</td>"
                    + "</td>"
                    + "</tr>"
            }
            $('#items').html(items);
        })
    })
}
