// Funzione per validare la registrazione
function validateRegistrationForm(event) {
    // Recupera i valori dei campi
    let nome = document.getElementById("nome").value;
    let cognome = document.getElementById("cognome").value;
    let dataNascita = document.getElementById("data_nascita").value;
    let email = document.getElementById("email").value;
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let valid = true;

    // Controllo Nome
    if (nome === "") {
        alert("Il campo Nome è obbligatorio.");
        valid = false;
    }

    // Controllo Cognome
    if (cognome === "") {
        alert("Il campo Cognome è obbligatorio.");
        valid = false;
    }

    // Controllo Data di Nascita (assicurarsi che non sia una data futura)
    if (dataNascita === "") {
        alert("Il campo Data di Nascita è obbligatorio.");
        valid = false;
    } else {
        const today = new Date();
        const birthDate = new Date(dataNascita);
        if (birthDate >= today) {
            alert("La Data di Nascita non può essere una data futura.");
            valid = false;
        }
    }

    // Controllo Email (formato email)
    let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (email === "" || !emailPattern.test(email)) {
        alert("Inserisci un'Email valida.");
        valid = false;
    }

    // Controllo Username (non vuoto)
    if (username === "") {
        alert("Il campo Username è obbligatorio.");
        valid = false;
    }

    // Controllo Password (minimo 6 caratteri)
    if (password === "") {
        alert("Il campo Password è obbligatorio.");
        valid = false;
    } else if (password.length < 6) {
        alert("La password deve essere di almeno 6 caratteri.");
        valid = false;
    }

    // Se uno dei campi non è valido, impediamo l'invio del form
    if (!valid) {
        event.preventDefault();
    }
}
