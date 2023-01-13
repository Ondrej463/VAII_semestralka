var nameOFDeletedEvent = "";
var reloadNeed = false;

window.onload = function () {
    document.body.addEventListener("click", zavriOknoCheck);
    document.getElementById("okButton").addEventListener("click", zavriOkno);
    document.getElementById("zrusButton").addEventListener("click", zavriOkno);
    document.getElementById("vymazatButton").addEventListener("click", vymaz);
    let hrefs = document.getElementsByClassName("vymaz");
    for (let i = 0; i < hrefs.length; i++) {
        hrefs[i].addEventListener("click", potvrd);
    }
}


function zobrazOkno() {
    setTimeout(function() {
        document.getElementById("deleteModal").classList.remove("hidden");
        document.getElementById("main_content").classList.add("blur");
    }, 20);
}

function zavriOknoCheck(event) {
    let modal = document.getElementById("deleteModal");
    if (!modal.classList.contains("hidden")) {
        if (!modal.contains(event.target)) {
            zavriOkno();
        }
    }
}

function zavriOkno() {
    let modal = document.getElementById("deleteModal");
    modal.classList.add("hidden");
    document.getElementById("main_content").classList.remove("blur");
    if (reloadNeed) {
        window.location.reload();
    }
}

function potvrd(event) {
    nameOFDeletedEvent = event.target.parentNode.name;

    document.getElementById("modalNadpis").innerText = "Potvrdenie";
    let body = document.getElementById("modalBody");
    if (!body.classList.contains("text-red-600")) {
        body.classList.add("text-ted-600");
    }
    body.innerText = "Naozaj chcete vymazať udalosť ?";
    document.getElementById("okButton").classList.add("hidden");
    document.getElementById("zrusButton").classList.remove("hidden");
    document.getElementById("vymazatButton").classList.remove("hidden");
    zobrazOkno();
}

function vymaz() {
    zavriOkno();
    $.ajax ({
        type: "POST",
        url: 'http://localhost:8080/X-Tipping/delete',
        dataType: "json",
        data: nameOFDeletedEvent,
        contentType: "application/json",
        success: function(data) {
            document.getElementById("modalNadpis").innerText = "Správa";
            document.getElementById("zrusButton").classList.add("hidden");
            document.getElementById("okButton").classList.remove("hidden");
            document.getElementById("vymazatButton").classList.add("hidden");
            if (data.response !== "") {
                document.getElementById("modalBody").classList.add("text-red-600");
                document.getElementById("modalBody").innerText = data.response;

            } else {
                document.getElementById("modalBody").classList.add("text-green-600");
                document.getElementById("modalBody").innerText = "Podujatie " + nameOFDeletedEvent + " úspešne vymazané!";
                reloadNeed = true;
            }
            zobrazOkno();
        },
        error: function() {
            alert("error");
        }
    });
}