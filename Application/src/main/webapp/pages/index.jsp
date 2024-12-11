<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WePlay - Homepage</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/index.css">
</head>
<body>
    <header class="header">
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
        <nav class="navbar">
            <a href="${pageContext.request.contextPath}/pages/index.jsp"><img src="${pageContext.request.contextPath}/IMG/logo_WePlay.png" alt="WePlay Logo"></a>
            <a href="calcio.jsp"><ion-icon name="football-outline"></ion-icon></a>
            <a href="tennis.jsp"><ion-icon name="tennisball-outline"></ion-icon></a>
            <a href="esploraEventi.jsp"><h1>ESPLORA EVENTI</h1></a>
            <a href="basket.jsp"><ion-icon name="basketball-outline"></ion-icon></a>
            <a href="bocce.jsp"><ion-icon name="bowling-ball-outline"></ion-icon></a>
            <a href="login.jsp"><ion-icon name="person-circle-outline"></ion-icon></a>
        </nav>
    </header>

    <main class="main-content">
        <section class="intro-section">
            <div class="content-wrapper">
                <div class="intro-text">
                    <h1>Unisciti a noi per partecipare e creare eventi sportivi che costruiscono nuove amicizie e valorizzano lo sport!</h1>
                    <p>WePlay è una piattaforma web dedicata a promuovere la partecipazione inclusiva agli eventi sportivi di gruppo. 
                       L'obiettivo è abbattere le barriere di accesso allo sport, creando un ambiente accogliente e aperto a persone di ogni livello di esperienza e con bisogni diversi.</p>
                    <a href="${pageContext.request.contextPath}/pages/login.jsp">
                    <button>Iscriviti a WePlay</button>
                    </a>
                </div>
                <div class="intro-image">
                    <img src="${pageContext.request.contextPath}/IMG/logo_WePlay.png" alt="WePlay Logo">
                </div>
            </div>
        </section>

        <section class="functions">
            <div class="content-wrapper">
                <div class="text-container">
                <h2>Cosa fare su WePlay</h2>
                    <p>Il sito offre strumenti intuitivi per la gestione degli eventi sportivi, tra cui un sistema di prenotazione semplice, la gestione delle iscrizioni e delle disponibilità,
                       oltre a funzionalità per facilitare l'interazione sociale tra i partecipanti e promuovere i valori di inclusione e rispetto.</p>
                </div>
            </div>
        </section>
        
        <section class="functions">
            <div class="content-wrapper">
                <div class="text-container">
                    <h2>Ci racconta WePlay...</h2>
                    <p>"Grazie a WePlay ho scoperto quanto lo sport possa unire le persone. Ho iniziato partecipando a una partita di calcetto organizzata tramite la piattaforma, senza conoscere nessuno.
                       Da quel giorno, però, ho trovato non solo un gruppo di gioco, ma degli amici veri. Ogni settimana ci ritroviamo per giocare e, nel tempo, il nostro rapporto è cresciuto anche fuori dal campo. 
                       Ora siamo una squadra dentro e fuori lo sport, e tutto è nato grazie a WePlay. Non avrei mai pensato che un'app potesse cambiare così tanto la mia vita sociale!"
                       – Marco, 29 anni</p>
                </div>
            </div>
        </section>
    </main>

    <footer class="footer">
    	<div class="footer-links">
            <a href="${pageContext.request.contextPath}/pages/index.jsp"><p>Informazioni</p></a>
            <a href="${pageContext.request.contextPath}/pages/assistenza.jsp"><p>Assistenza</p></a>
        </div>
        <div class="footer-info">
            <p>© 2024 WePlay, Tutti i diritti riservati.</p>
            <p>Via di Prova, n. 123, Frosinone (FR)</p>
        </div> 
    </footer>
</body>
</html>
