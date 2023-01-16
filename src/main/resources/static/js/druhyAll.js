window.onload = function () {
    let select = document.getElementById("vyberDruh");
    setData();
    select.addEventListener("change", setData);
}

function setData() {
    let select = document.getElementById("vyberDruh");
    $.ajax ({
        type: "POST",
        url: 'http://localhost:8080/X-Tipping/getDruhData',
        data: select.options[select.selectedIndex].text,
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            document.getElementById("zaciatok").value = data.response[0];
            document.getElementById("koniec").value = data.response[1];
            document.getElementById("pocetCislic").value = data.response[2];
            document.getElementById("minPocetTipov").value = data.response[3];
        },
        error: function() {
            alert("error");
        }
    });
}