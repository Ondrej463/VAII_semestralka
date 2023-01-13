window.onload = function () {
    let registerForm = document.getElementById("registerForm");
    if (registerForm != null) {
        registerForm.addEventListener("submit", (e) => {
            if (!validateRegistration()) {
                e.preventDefault();
            }
        });
    }

    let loginForm = document.getElementById("loginForm");
    if (loginForm != null) {
        loginForm.addEventListener("submit", (e) => {
            if (!validateLogin()) {
                e.preventDefault();
            }
        })
    }

    let spravcaPodujatiForm = document.getElementById("spravcaPodujatiForm");
    if (spravcaPodujatiForm != null) {
        spravcaPodujatiForm.addEventListener("submit", (e) => {
            e.preventDefault();
            submitKoeficientForm();
            sendDruh();
            setTimeout(function() {
                if (validateSpravcaPodujatiForm()) {
                    checkTippingAllForm();
                }
            }, 200);
        })
        let select = document.getElementById("selectDruh");
        if (document.getElementById("selectDruh").value !== "hlaska") {
            document.getElementById("datumy").className = "mb-4";
            document.getElementById("cisla").className = "mb-4";
        }
        select.addEventListener("change", zmenaSelectOptions);
    }

    let koeficienty = document.getElementById("koeficienty");
    if (koeficienty != null) {
        document.body.addEventListener("click", zavriOknoCheck);
        koeficienty.addEventListener("click", otvorModalOkno);
        initKoefs();
    }

    let novyDruh = document.getElementById("newDruhForm");
    if (novyDruh != null) {
        novyDruh.addEventListener("submit", (e) => {
            if (!validateNovyDruhForm()) {
                e.preventDefault();
            }
        })
    }

}
function zmenaSelectOptions() {
    let select = document.getElementById("selectDruh");
    if (select.value !== "hlaska") {
        $.ajax ({
            type: "POST",
            url: 'http://localhost:8080/X-Tipping/getDatumy',
            data: select.options[select.selectedIndex].text,
            dataType: "json",
            contentType: "application/json",
            success: function(data) {
                document.getElementById("divKoeficienty").classList.remove("mt-4");
                document.getElementById("beggining").value = data.response[0];
                document.getElementById("end").value = data.response[1];
                document.getElementById("datumy").className = "mb-4";
                document.getElementById("cisla").className = "mb-4";
                zobrazCisla(parseInt(data.response[2]));
            },
            error: function() {
                alert("error");
            }
        });
    } else {
        document.getElementById("divKoeficienty").classList.add("mt-4");
        document.getElementById("datumy").className = "hidden";
        document.getElementById("cisla").className = "hidden";
    }
}

function zobrazCisla(pocet) {
    let cisla = document.getElementById("cisla").querySelectorAll('input[type="text"]');
    for (let i = 0; i < 5; i++) {
        if (i < pocet) {
             cisla[i].classList.remove("hidden");
        } else {
            cisla[i].classList.add("hidden");
        }
    }
}

function checkTippingAllForm() {
    let nazov = document.getElementById("name");
    let zaciatok = document.getElementById("beggining");
    let sendData = [nazov.value, zaciatok.value];
    $.ajax ({
        type: "POST",
        url: 'http://localhost:8080/X-Tipping/validate',
        data: JSON.stringify(sendData),
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            var vporiadku = true;
            if (data.response[0] != null && data.response[0] !== "") {
                setErrorFor(nazov, data.response[0]);
                vporiadku = false;
            } else {
                setSuccessFor(nazov);
            }
            if (data.response[1] != null && data.response[1] !== "") {
                setErrorFor(zaciatok, data.response[1]);
                vporiadku = false;
            } else {
                setSuccessFor(zaciatok);
            }
            if (vporiadku) {
                document.getElementById("spravcaPodujatiForm").submit();
            }
        },
        error: function() {
            alert("error");
        }
    });
}

function sendDruh() {
    let select = document.getElementById("selectDruh");
    $.ajax ({
        type: "POST",
        url: 'http://localhost:8080/X-Tipping/setDruh',
        data: select.options[select.selectedIndex].text,
        dataType: "json",
        contentType: "application/json",
        success: function(data) {

        },
        error: function() {
            alert("error");
        }
    });
}

function submitKoeficientForm() {
    let koeficients = getKoeficients();
    $.ajax ({
        type: "POST",
        url: 'http://localhost:8080/X-Tipping/saveKoeficient',
        data: JSON.stringify(koeficients),
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            if (data.responseStatus === "") {
                zavriOkno();
            }
            let koefMessages = document.getElementsByClassName("koefErrorMessage");
            for (var i = 0; i < koefMessages.length; i++) {
                koefMessages[i].textContent = data.responseStatus;
            }
        },
        error: function() {
            alert("error");
        }
    });
}

function getKoeficients() {
    let koeficients = [];
    let ods = document.getElementById("koeficientForm").querySelectorAll('input[type="text"]');
    for (let i = 0; i < ods.length / 3; i++) {
        koeficients.push({"od_":ods[3 * i].value, "do_":ods[3 * i + 1].value, "koef":ods[3 * i + 2].value});
    }
    return koeficients;
}

function addKoeficient() {
    addKoeficientToTemplate("", "", "");
}

function initKoefs() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/X-Tipping/getKoeficients',
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            var disable = 0;
            if (data.responseStatus === "disable") {
                disable = 1;
            }
            for (let i = 0; i < data.response.length; i++) {
                addKoeficientToTemplate(data.response[i].od_, data.response[i].do_, data.response[i].koef, disable);
            }
            if (data.response.length === 0) {
                document.getElementById("odoberButton").style.visibility = "hidden";
            }
            if (disable) {
                document.getElementById("odoberButton").style.visibility = "hidden";
                document.getElementById("pridajButton").style.visibility = "hidden";
                document.getElementById("zrusButton").style.visibility = "hidden";
                document.getElementById("ulozButton").style.visibility = "hidden";
                document.getElementById("okButton").classList.remove("hidden");
            }
        },
        error: function () {
            alert("error");
        }
    });
}

function addKoeficientToTemplate(odV, doV, koefV, disable) {
    koeficients = getKoeficients();
    let content = document.getElementById("koeficientForm");
    var newDiv = document.createElement("div");
    newDiv.className = "flex block my-4";
    newDiv.id = "content" + koeficients.length;
    let nazvy = ["od", "do", "koef."];
    let rozmery = [9, 9, 14];
    let hodnoty = [odV, doV, koefV];
    for (var i = 0; i < 3; i++) {
        var input = document.createElement("input");
        input.type = "text";
        input.id = nazvy[i] + (koeficients.length);
        input.className = "flex-initial w-" + rozmery[i] + " px-3 py-1.5 text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out mr-5 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none";
        input.value = hodnoty[i];
        if (disable) {
            input.setAttribute("disabled", "disabled");
        }

        var label = document.createElement("label");
        label.htmlFor = nazvy[i] + (koeficients.length);
        label.innerText = nazvy[i].toUpperCase();
        label.className = "form-label text-gray-800 inline-block my-auto mr-2";
        newDiv.appendChild(label);
        newDiv.appendChild(input);
    }
    content.appendChild(newDiv);
    if (koeficients.length === 0) {
        document.getElementById("odoberButton").style.visibility = "visible";
    }
}

function removeKoeficient() {
    let koeficients = getKoeficients();
    let content = document.getElementById("koeficientForm");
    content.removeChild(document.getElementById("content" + (koeficients.length - 1)));
    if (koeficients.length === 1) {
        document.getElementById("odoberButton").style.visibility = "hidden";
    }
}

function otvorModalOkno() {
    setTimeout(function() {
        let modal = document.getElementById("modal");
        modal.style.display = "block";
        document.getElementById("main_content").classList.add("blur");
    }, 20);
}

function zavriOknoCheck(event) {
    let modal = document.getElementById("modal");
    if (!modal.contains(event.target)) {
        if (modal.style.display === "block") {
            zavriOkno();
        }
    }
}

function zrusOkno() {
    let content = document.getElementById("koeficientForm");
    var ods = document.getElementById("koeficientForm").querySelectorAll('input[type="text"]');
    for (var i = 0; i < ods.length / 3; i++) {
        content.removeChild(document.getElementById("content" + i));
    }
    initKoefs();
    zavriOkno();
}

function zavriOkno() {
    modal.style.display = "none";
    document.getElementById("main_content").classList.remove("blur");
}

function generuj() {
    document.getElementById('prveCislo').value = Math.floor(Math.random() * 10).toString();
    document.getElementById('druheCislo').value = Math.floor(Math.random() * 10).toString();
    document.getElementById('tretieCislo').value = Math.floor(Math.random() * 10).toString()
    document.getElementById('stvrteCislo').value = Math.floor(Math.random() * 10).toString();
    document.getElementById('piateCislo').value = Math.floor(Math.random() * 10).toString();
    return false;
}

function validateLogin() {
    const email = document.getElementById("email");
    const password = document.getElementById("passwd");
    var vysledok = true;

    if (email.value === '') {
        setErrorFor(email, "Nezadali ste email!");
        vysledok = false;
    } else if (!isEmailValid(email.value)) {
        setErrorFor(email, "Neplatný email!");
        vysledok = false;
    }
    else {
        setSuccessFor(email);
    }

    if (password.value === '') {
        setErrorFor(password, "Nezadali ste heslo!");
        vysledok = false;
    } else if (!/[A-Z]/.test(password.value)) {
        setErrorFor(password, "Zadali ste nesprávne heslo!");
        vysledok = false;
    } else if (!/[0-9]/.test(password.value)) {
        setErrorFor(password, "Zadali ste nesprávne heslo!");
        vysledok = false;
    } else {
        setSuccessFor(password);
    }
    return vysledok;
}

function validateRegistration() {
    let vysledok = true;
    const meno = document.getElementById("name");
    const priezvisko = document.getElementById("lastName");
    const datumNarodenia = document.getElementById("birthDate");
    const adresa = document.getElementById("address");
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const passwordRepeat = document.getElementById("repeatPassword");

    if (meno.value === '') {
        setErrorFor(meno, "Meno nesmie byť prázdne!");
        vysledok = false;
    } else {
        setSuccessFor(meno);
    }

    if (priezvisko.value === '') {
        setErrorFor(priezvisko, "Priezvisko nesmie byť prázdne!");
        vysledok = false;
    } else {
        setSuccessFor(priezvisko);
    }

    if (adresa.value === '') {
        setErrorFor(adresa, "Adresa nesmie byť prázdna!");
        vysledok = false;
    } else {
        setSuccessFor(adresa);
    }

    if (datumNarodenia.value === '') {
        setErrorFor(datumNarodenia, "Dátum narodenia nesmie byť prázdny!");
        vysledok = false;
    } else {
        let rok = datumNarodenia.value.substring(0,4);
        let mesiac = datumNarodenia.value.substring(5,7) - 1;
        let den = datumNarodenia.value.substring(8,10);
        date = new Date(rok, mesiac, den);
        if (new Date() - date < 0) {
            setErrorFor(datumNarodenia, "Neplatný dátum narodenia!");
            vysledok = false;
        } else if (dateDiffInYears(date, new Date()) < 18) {
            setErrorFor(datumNarodenia, "Zamietnuté, nedovŕšili ste 18 rokov!");
            vysledok = false;
        } else {
            setSuccessFor(datumNarodenia);
        }
    }


    if (email.value === '') {
        setErrorFor(email, "Email nesmie byť prázdny!");
        vysledok = false;
    } else if (!isEmailValid(email.value)) {
        setErrorFor(email, "Email je neplatný!");
        vysledok = false;
    } else {
        setSuccessFor(email);
    }

    if (password.value === '') {
        setErrorFor(password, "Heslo nesmie byť prázdne!");
        vysledok = false;
    } else if (!/[A-Z]/.test(password.value)) {
        setErrorFor(password, "Heslo musí obsahovať aspoň jedno veľké písmeno!");
        vysledok = false;
    } else if (!/[0-9]/.test(password.value)) {
        setErrorFor(password, "Heslo musí obsahovať aspoň jedno číslo!");
        vysledok = false;
    } else if (password.value.length < 8) {
        setErrorFor(password, "Heslo musí mať aspoň 8 znakov!");
        vysledok = false;
    } else {
        setSuccessFor(password);
    }

    if (passwordRepeat.value === '') {
        setErrorFor(passwordRepeat, "Heslo nesmie byť prázdne!");
        vysledok = false;
    } else if (password.value !== passwordRepeat.value) {
        setErrorFor(passwordRepeat, "Heslá sa nezhodujú!");
        vysledok = false;
    } else {
        setSuccessFor(passwordRepeat);
    }
    return vysledok;
}

function setErrorFor(input, message) {
    const formControl = input.parentElement;
    const small = formControl.querySelector('small');
    small.innerText = message;
    formControl.className = 'formControl error';
}

function setSuccessFor(input) {
    const formControl = input.parentElement;
    const small = formControl.querySelector('small');
    small.innerText = '';
    formControl.className = 'formControl success';
}

function isEmailValid(email) {
    return String(email)
        .toLowerCase()
        .match(
            /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        );
}

function dateDiffInYears(dateold, datenew) {
    var ynew = datenew.getFullYear();
    var mnew = datenew.getMonth();
    var dnew = datenew.getDate();
    var yold = dateold.getFullYear();
    var mold = dateold.getMonth();
    var dold = dateold.getDate();
    var diff = ynew - yold;
    if (mold > mnew) diff--;
    else {
        if (mold == mnew) {
            if (dold > dnew) diff--;
        }
    }
    return diff;
}

function validateSpravcaPodujatiForm() {
    vysledok = true;
    const nazov = document.getElementById("name");
    const begin = document.getElementById("beggining");
    const end = document.getElementById("end");
    const druh = document.getElementById("selectDruh");
    const prveCislo = document.getElementById("prveCislo");
    const druheCislo = document.getElementById("druheCislo");
    const tretieCislo = document.getElementById("tretieCislo");
    const stvrteCislo = document.getElementById("stvrteCislo");
    const piateCislo = document.getElementById("piateCislo");

    if (nazov != null) {
        if (nazov.value === '') {
            setErrorFor(nazov, "Názov je povinné pole!");
            vysledok = false;
        } else {
            setSuccessFor(nazov);
        }
    }

    if (begin.value === "") {
        setErrorFor(begin, "Začiatok je povinné pole!");
        vysledok = false;
    } else {
        setSuccessFor(begin);
    }

    if (druh.value === "hlaska") {
        setErrorFor(druh, "Druh nie je špecifikovaný");
        vysledok = false;
    } else {
        setSuccessFor(druh);
    }

    if (end.value === "") {
        setErrorFor(end, "Koniec je povinné pole!");
        vysledok = false;
    } else {
        setSuccessFor(end);
    }

    if (!vysledok) {

    }

    if (end.value !== "" && begin.value !== "") {
        let beginDate = Date.parse(begin.value);
        let endDate = Date.parse(end.value);
        if (endDate - beginDate < 0) {
            setErrorFor(end, "Dátum konca musí byť väčší ako dátum začiatku!");
            vysledok = false;
        } else {
            setSuccessFor(end);
        }
    }

    if (document.getElementsByClassName("koefErrorMessage")[0].innerText !== "") {
        vysledok = false;
    }

    if (!jeCislo(prveCislo.value) || !jeCislo(druheCislo.value) || !jeCislo(tretieCislo.value) ||
        !jeCislo(stvrteCislo.value) || !jeCislo(piateCislo.value)) {
        setErrorFor(prveCislo, "Generované môžu byť len číslice 0-9!");
        vysledok = false;
    } else {
        setSuccessFor(prveCislo);
    }
    return vysledok;
}

function jeCislo(cislo) {
    return /[0-9]/.test(cislo);
}

function validateNovyDruhForm() {
    var vysledok = true;
    let nazov = document.getElementById("name");
    let zaciatok = document.getElementById("zaciatok");
    let koniec = document.getElementById("koniec");
    let pocetCislic = document.getElementById("pocetCislic");
    let minPocetTipov = document.getElementById("minPocetTipov");

    if (nazov.value === "") {
        setErrorFor(nazov, "Názov nesmie byť prázdny");
        vysledok = false;
    } else {
        setSuccessFor(nazov);
    }

    if (zaciatok.value === "Vyberte jednu z možností") {
        setErrorFor(zaciatok, "Prosím vyberte možnosť pre začiatok");
        vysledok = false;
    }

    if (koniec.value === "Vyberte jednu z možností") {
        setErrorFor(koniec, "Prosím vyberte možnosť pre začiatok");
        vysledok = false;
    } else {
        setSuccessFor(koniec);
    }

    if (pocetCislic.value === "" || parseInt(pocetCislic.value, 10) <= 0) {
        setErrorFor(pocetCislic, "Počet nesmie byť prázdny");
        vysledok = false;
    } else {
        if (parseInt(pocetCislic.value, 10) > 5) {
            setErrorFor(pocetCislic, "Počet musí byť menší ako 6");
            vysledok = false;
        } else {
            setSuccessFor(pocetCislic);
        }
    }

    if (minPocetTipov.value === "" || parseInt(minPocetTipov.value, 10) === 0) {
        setErrorFor(minPocetTipov, "Zadajte min počet tipov");
        vysledok = false;
    } else {
        setSuccessFor(minPocetTipov);
    }
    return vysledok;
}
