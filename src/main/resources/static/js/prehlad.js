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
    kontrolujZobrazenieInfo();
}

function kontrolujZobrazenieInfo() {
    setTimeout(function () {
        let infoHrefs = document.getElementsByClassName("infoHref");
        for (let i = 0; i < infoHrefs.length; i++) {
            let jeSkryty = infoHrefs[i].name.substring(0, infoHrefs[i].name.indexOf(' '));
            if (jeSkryty === "true") {
                let riadok = infoHrefs[i].parentElement.parentElement;
                let begin = riadok.children[1].innerHTML;
                let stav = riadok.children[3].innerText;
                let beginDate = Date.parse(begin.substring(6, 10) + "-" + begin.substring(3, 5) + "-" + begin.substring(0, 2) + " "
                 + begin.substring(11, 13) + ":" + begin.substring(14, 16));
                let koniec = infoHrefs[i].name.substring(infoHrefs[i].name.indexOf(' ') + 1);
                let koniecDate = Date.parse(koniec);
                if (new Date() - koniecDate > 0) {
                    window.location.reload();
                } else if (stav === "Nezačal" && new Date() - beginDate > 0) {
                    window.location.reload();
                }
            }
        }

        kontrolujZobrazenieInfo();
    }, 5000);
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