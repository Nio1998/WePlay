let originalEmail = "";
let originalPassword = "********";

window.onload = function() {
    const passwordField = document.getElementById("password");

    //Mostra asterischi in base alla lunghezza della password
    if (passwordField) {
        const passwordLength = passwordField.value.length;
        const asterisks = "*".repeat(passwordLength); // Crea una stringa con tanti asterischi quanto la lunghezza della password
        passwordField.value = asterisks;
    }
};


function attivaModifica() {
    const emailField = document.getElementById("email");
    const passwordField = document.getElementById("password");

    // Memorizza i valori originali prima di abilitare la modifica
    originalEmail = emailField.value;
    originalPassword = passwordField.value;

    // Rimuovi la classe "input-disabilitato" e aggiungi "input-modificabile" per entrambi i campi
    emailField.classList.remove("input-disabilitato");
    emailField.classList.add("input-modificabile");
    emailField.removeAttribute("readonly");

    passwordField.classList.remove("input-disabilitato");
    passwordField.classList.add("input-modificabile");
    passwordField.removeAttribute("readonly");

    // Aggiungi o aggiorna i campi hidden nel form per email e password
    const form = document.getElementById("modificaDatiForm");

    // Per l'email
    let emailInput = document.getElementById("emailInput");
    if (!emailInput) {
        emailInput = document.createElement("input");
        emailInput.type = "hidden";
        emailInput.id = "emailInput";
        emailInput.name = "email";
        form.appendChild(emailInput);
    }
    emailInput.value = originalEmail; // Imposta il valore originale

    // Per la password
    let passwordInput = document.getElementById("passwordInput");
    if (!passwordInput) {
        passwordInput = document.createElement("input");
        passwordInput.type = "hidden";
        passwordInput.id = "passwordInput";
        passwordInput.name = "password";
        form.appendChild(passwordInput);
    }
    passwordInput.value = originalPassword; // Imposta il valore originale

    // Modifica la visibilità dei pulsanti
    document.getElementById("modificaDati").style.display = "none";
    document.getElementById("modificaButtons").style.display = "flex"; // Bottoni in flex per centrarli
}


function annullaModifica(emailOriginale) {
    const emailField = document.getElementById("email");
    const passwordField = document.getElementById("password");

    emailField.classList.remove("input-modificabile");
    emailField.classList.add("input-disabilitato");
    emailField.setAttribute("readonly", "readonly");
    emailField.value = emailOriginale;

    passwordField.classList.remove("input-modificabile");
    passwordField.classList.add("input-disabilitato");
    passwordField.setAttribute("readonly", "readonly");
    passwordField.value = "******";

    document.getElementById("modificaDati").style.display = "block";
    document.getElementById("modificaButtons").style.display = "none";
}



function salvaDati() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // Controllo dell'email
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (!emailRegex.test(email)) {
        alert("L'email inserita non è valida. Assicurati di inserire un'email corretta.");
        return;  // Blocca il salvataggio
    }

    // Controllo della password solo se è stata modificata
    const passwordChanged = password !== originalPassword;

    if (passwordChanged) {
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d@$!%*?&]{8,}$/;
        if (!passwordRegex.test(password)) {
            alert("La password deve essere lunga almeno 8 caratteri, contenere almeno una lettera maiuscola, una lettera minuscola e un numero.");
            return;  // Blocca il salvataggio
        }
    }

    // Aggiorna i campi hidden nel form
    const form = document.getElementById("modificaDatiForm");

    let emailInput = document.getElementById("emailInput");
    if (!emailInput) {
        emailInput = document.createElement("input");
        emailInput.type = "hidden";
        emailInput.id = "emailInput";
        emailInput.name = "email";
        form.appendChild(emailInput);
    }
    emailInput.value = email; // Imposta il nuovo valore aggiornato

    let emailChangedFlag = document.getElementById("emailChangedFlag");
    if (!emailChangedFlag) {
        emailChangedFlag = document.createElement("input");
        emailChangedFlag.type = "hidden";
        emailChangedFlag.id = "emailChangedFlag";
        emailChangedFlag.name = "emailChanged";
        form.appendChild(emailChangedFlag);
    }
    emailChangedFlag.value = (email !== originalEmail) ? "true" : "false";

    let passwordInput = document.getElementById("passwordInput");
    if (!passwordInput) {
        passwordInput = document.createElement("input");
        passwordInput.type = "hidden";
        passwordInput.id = "passwordInput";
        passwordInput.name = "password";
        form.appendChild(passwordInput);
    }
    passwordInput.value = password;

    let passwordChangedFlag = document.getElementById("passwordChangedFlag");
    if (!passwordChangedFlag) {
        passwordChangedFlag = document.createElement("input");
        passwordChangedFlag.type = "hidden";
        passwordChangedFlag.id = "passwordChangedFlag";
        passwordChangedFlag.name = "passwordChanged";
        form.appendChild(passwordChangedFlag);
    }
    passwordChangedFlag.value = passwordChanged ? "true" : "false";
    
    console.log("Email Changed Flag:", email !== originalEmail);
    console.log("Password Changed Flag:", password !== originalPassword);

    // Invio del form
    form.submit();
}


function chiudiMessaggio() {
    const messaggio = document.getElementById("messaggio");
    if (messaggio) {
        messaggio.style.display = "none"; // Nasconde il messaggio
    }
}


