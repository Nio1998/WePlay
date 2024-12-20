<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.utente.UtenteBean" %>
<!DOCTYPE html>
<html lang="it">
<%
    // Verifica se l'utente è loggato
    Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
    isLoggedIn = (isLoggedIn != null && isLoggedIn);
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WePlay - Homepage</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/index.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<link rel="stylesheet" href="CSS/alert.css">
	<script src="JS/alert.js" defer></script>
</head>
<body>
    <header class="header">
        <jsp:include page="/pages/navbar.jsp" />
    </header>
    
    <% if (request.getAttribute("errore") != null || request.getAttribute("successo") != null) { %>
        <div class="messaggi <% if (request.getAttribute("errore") != null) { %>errore<% } else if (request.getAttribute("successo") != null) { %>successo<% } %>" id="messaggio">
            <span>
                <% if (request.getAttribute("errore") != null) { %>
                    <%= request.getAttribute("errore") %>
                <% } else if (request.getAttribute("successo") != null) { %>
                    <%= request.getAttribute("successo") %>
                <% } %>
            </span>
            <button class="close-btn" onclick="chiudiMessaggio()">×</button>
        </div>
    <% } %>

    <!-- Contenitore principale -->
    <main class="main-content">
        <section class="intro-section">
            <div class="content-wrapper">
                <div class="intro-text">
                    <h1>Unisciti a noi per partecipare e creare eventi sportivi che costruiscono nuove amicizie e valorizzano lo sport!</h1>
                    <p>WePlay è una piattaforma web dedicata a promuovere la partecipazione inclusiva agli eventi sportivi di gruppo. 
                       L'obiettivo è abbattere le barriere di accesso allo sport, creando un ambiente accogliente e aperto a persone di ogni livello di esperienza e con bisogni diversi.</p>
                    <a href="#" id="accountLink2">
                    	<button>Iscriviti a WePlay</button>
                    </a>
                    <% if (isLoggedIn) { %>
                		<script>
            				document.getElementById("accountLink2").setAttribute("href", "${pageContext.request.contextPath}/ProfiloServlet");
                		</script>
            		<% } else { %>
            			<script>
            				document.getElementById("accountLink2").setAttribute("href", "${pageContext.request.contextPath}/pages/login.jsp");
                		</script>
            		<% } %>
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
        <jsp:include page="/pages/footer.jsp" />
    </footer>
</body>
</html>
