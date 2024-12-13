// Funzione per validare il login
function validateLoginForm(event) {
    // Recupera i valori dei campi
    let usernameOrEmail = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let valid = true;

    // Controllo Username/Email
    if (usernameOrEmail === "") {
        alert("Il campo Username o Email è obbligatorio.");
        valid = false;
    }

    // Controllo Password (non vuota)
    if (password === "") {
        alert("Il campo Password è obbligatorio.");
        valid = false;
    }

    // Se uno dei campi non è valido, impediamo l'invio del form
    if (!valid) {
        event.preventDefault();
    }
}
