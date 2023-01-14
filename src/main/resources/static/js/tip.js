document.ready = function () {

}

window.onload = function () {
    document.getElementById("vklad").addEventListener("change", setTwoNumberDecimal);
    document.getElementById("submitTip").addEventListener("click", (e) => validate(e));
    init();
    let vklad = document.getElementById("vklad");
    if (vklad.value === "") {
        document.getElementById("vklad").value = "0.00";
    }
    setTwoNumberDecimal();
}

function init() {
    $.ajax ({
        type: "GET",
        url: 'http://localhost:8080/X-Tipping/pocetCislic',
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            zobrazCislice(parseInt(data.response));
        },
        error: function() {
            alert("error");
        }
    });
}

function zobrazCislice(pocet) {
    let cisla = document.getElementById("numbers").querySelectorAll('input[type="text"]');
    for (let i = 0; i < 5; i++) {
        if (i < pocet) {
            cisla[i].classList.remove("hidden");
        } else {
            cisla[i].classList.add("hidden");
        }
    }
}

function validate(event) {
    event.preventDefault();
    var zvalidovane = true;
    let cislo1 = document.getElementById("prveCislo");
    let cislo2 = document.getElementById("druheCislo");
    let cislo3 = document.getElementById("tretieCislo");
    let cislo4 = document.getElementById("stvrteCislo");
    let cislo5 = document.getElementById("piateCislo");
    let vklad = document.getElementById("vklad");

    if (!jeCislo(cislo1.value) || !jeCislo(cislo2.value) || !jeCislo(cislo3.value) ||
        !jeCislo(cislo4.value) || !jeCislo(cislo5.value)) {
        setErrorForCislo(cislo1, "Tipovať sa môžu len číslice 0-9!");
        zvalidovane = false;
    } else {
        setSuccessForCislo(cislo1);
    }

    if (vklad.value === "" || parseFloat(vklad.value) < 10) {
        setErrorFor(vklad, "Minimálny vklad je 10 eur!");
        zvalidovane = false;
    } else {
        setSuccessFor(vklad);
    }

    if (zvalidovane) {
        document.getElementById("tipForm").submit();
    }
}

function jeCislo(cislo) {
    return /[0-9]/.test(cislo);
}

function setErrorFor(input, message) {
    const formControl = input.parentElement;
    const small = formControl.querySelector('small');
    small.innerText = message;
    formControl.classList.add('error');
}

function setSuccessFor(input) {
    const formControl = input.parentElement;
    const small = formControl.querySelector('small');
    small.innerText = '';
    formControl.classList.add('success');
}

function setTwoNumberDecimal(event) {
    this.value = parseFloat(this.value).toFixed(2);
}

function setErrorForCislo(input, message) {
    const formControl = input.parentElement.parentElement;
    const small = formControl.querySelector('small');
    small.innerText = message;
    formControl.classList.add('error');
}

function setSuccessForCislo(input) {
    const formControl = input.parentElement.parentElement;
    const small = formControl.querySelector('small');
    small.innerText = '';
    formControl.classList.add('success');
}