// Funzione per chiudere il messaggio di alert
function chiudiMessaggio() {
    const messaggio = document.querySelector('.messaggi');
    if (messaggio) {
        messaggio.classList.add('clicked');
        setTimeout(() => {
            messaggio.style.display = 'none'; // Nasconde il messaggio dopo l'animazione
        }, 200); // Dopo la durata dell'animazione
    }
}
