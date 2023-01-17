window.onload = function () {
    let registerForm = document.getElementById("registerForm");
    if (registerForm != null) {
        registerForm.addEventListener("submit", (e) => {
            if (!validateRegistration()) {
                e.preventDefault();
            }
        });
    }
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
        if (mold === mnew) {
            if (dold > dnew) diff--;
        }
    }
    return diff;
}

function isEmailValid(email) {
    return String(email)
        .toLowerCase()
        .match(
            /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        );
}