// Funzione di validazione per la creazione dell'evento
function validateCreaEventoForm(event) {
    let titolo = document.getElementById("titolo").value;
    let sport = document.getElementById("sport").value;
    let indirizzo = document.getElementById("indirizzo").value;
    let citta = document.getElementById("citta").value;
    let data = document.getElementById("data").value;
    let ora = document.getElementById("ora").value;
    let partecipanti = document.getElementById("partecipanti").value;
    let prezzo = document.getElementById("prezzo").value;

    let valid = true;

    // Controllo Titolo
    if (titolo === "") {
        alert("Il campo Titolo è obbligatorio.");
        valid = false;
    }

    // Controllo Sport
    if (sport === "") {
        alert("Il campo Sport è obbligatorio.");
        valid = false;
    }

    // Controllo Indirizzo
    if (indirizzo === "") {
        alert("Il campo Indirizzo è obbligatorio.");
        valid = false;
    }

    // Controllo Città
    if (citta === "") {
        alert("Il campo Città è obbligatorio.");
        valid = false;
    }

	// Controllo Data (minimo il giorno dopo)
	let today = new Date(); // Data di oggi
	today.setHours(0, 0, 0, 0); // Azzeramento ore/minuti/secondi
	let eventDate = new Date(data); // Data dell'evento

	if (eventDate <= today) {
	    alert("La data dell'evento deve essere almeno domani.");
	    valid = false;
	}


    // Controllo Partecipanti (minimo 2)
    if (partecipanti < 2) {
        alert("Il numero di partecipanti deve essere almeno 2.");
        valid = false;
    }

    // Controllo Prezzo (minimo 0)
    if (prezzo < 0) {
        alert("Il prezzo deve essere maggiore o uguale a 0.");
        valid = false;
    }

    if (!valid) {
        event.preventDefault();
    }
}

// Funzione per selezionare lo sport
function selectSport(sport) {
    document.getElementById("sport").value = sport;
    document.getElementById("sportOptions").style.display = "none";
}

// Mostra le opzioni sport quando l'utente clicca sul campo
document.getElementById("sport").addEventListener("click", function () {
    let options = document.getElementById("sportOptions");
    options.style.display = options.style.display === "none" ? "block" : "none";
});

window.onload = function() {
    var sportInput = document.getElementById('sport');
    var sportOptions = document.getElementById('sportOptions');
    
    sportOptions.style.width = sportInput.offsetWidth + 'px'; // Imposta la larghezza della tendina in base alla larghezza dell'input
};
