<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*, java.time.ZoneId,java.util.Date, java.time.LocalDateTime, model.evento.Evento, model.evento.EventoDao, model.prenotazione.PrenotazioneDAO, model.prenotazione.PrenotazioneBean" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/evento/DettagliEvento.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <title>Dettagli Evento</title>
</head>
<body>

<jsp:include page="../pages/navbar.jsp" />
<div class="event-details">
    <% 
        Evento evento = null;
        int eventoId = -1;
        String currentUser = (String) session.getAttribute("username");
        try {
            eventoId = Integer.parseInt(request.getParameter("id"));
            EventoDao eventoDao = new EventoDao();
            evento = eventoDao.get(eventoId);

            PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
            List<PrenotazioneBean> partecipanti = prenotazioneDAO.findPrenotazioniByEvento(eventoId);
            String organizzatore = prenotazioneDAO.findOrganizzatoreByEventoID(eventoId);
            boolean isPartecipante = false;
            for (PrenotazioneBean p : partecipanti) {
                if (p.getUtenteUsername().equals(currentUser)) {
                    isPartecipante = true;
                    break;
                }
            }

            boolean isOrganizzatore = organizzatore.equals(currentUser);
            boolean isTerminato = "terminato".equals(evento.getStato());

            long currentTimeMillis = System.currentTimeMillis();
            boolean isWithin24HoursAfterEnd = false;
            boolean isLessThan24HoursBeforeStart = false;

            if (evento.getData_inizio() != null) {
                // Converte LocalDate in ZonedDateTime con il fuso orario di sistema
                long eventStartMillis = evento.getData_inizio()
                                               .atStartOfDay(ZoneId.systemDefault())
                                               .toInstant()
                                               .toEpochMilli();

                isLessThan24HoursBeforeStart = eventStartMillis - currentTimeMillis <= 24 * 60 * 60 * 1000;
            }

            if (evento.getData_inizio() != null) {
                // Converte LocalDate in ZonedDateTime con il fuso orario di sistema
                long eventEndMillis = evento.getData_inizio()
                                             .atStartOfDay(ZoneId.systemDefault())
                                             .toInstant()
                                             .toEpochMilli();

                isWithin24HoursAfterEnd = currentTimeMillis - eventEndMillis <= 24 * 60 * 60 * 1000;
            }


            if (evento != null) {
    %>
    <div class="event-info">
        <div class="event-details-grid">
            <div><strong>Titolo:</strong> <%= evento.getTitolo() %></div>
            <div><strong>Luogo:</strong> <%= evento.getCitta() %>, <%= evento.getIndirizzo() %></div>
            <div><strong>Prezzo:</strong> &euro;<%= String.format("%.2f", evento.getPrezzo()) %></div>
            <div><strong>Sport:</strong> <%= evento.getSport() %></div>
            <div><strong>Partecipanti:</strong> <%= partecipanti.size() %> / <%= evento.getMassimo_di_partecipanti() %></div>
            <div><strong>Orario:</strong> <%= evento.getData_inizio() %></div>
        </div>
        <div class="sport-icon-container">
            <% 
                String sport = evento.getSport();
                String iconPath = "";
                switch (sport.toLowerCase()) {
                    case "pallavolo":
                        iconPath = "images/volleyball.png";
                        break;
                    case "calcio":
                        iconPath = "images/football.png";
                        break;
                    case "basket":
                        iconPath = "images/basketball.png";
                        break;
                    case "tennis":
                    	iconPath = "images/tennis.png";
                        break;
                    case "ping pong":
                        iconPath = "images/pingpong.png";
                        break;
                    case "badminton":
                        iconPath = "images/badminton.png";
                        break;
                    case "golf":
                        iconPath = "images/golf.png";
                        break;
                    case "beach volley":
                        iconPath = "images/beachvolly.png";
                        break;
                    case "padel":
                        iconPath = "images/padel.png";
                        break;
                     case "bocce":
                        iconPath = "images/bocce.png";
                        break;
                    default:
                        iconPath = "images/default-sport.png";
                        break;
                }
            %>
            <img src="<%= request.getContextPath() %>/<%= iconPath %>" alt="<%= sport %>" class="sport-icon">
        </div>

        <div class="event-actions">
            <% if (!isTerminato) { %>
                <% if (isOrganizzatore) { %>
                    <button class="edit-button">Modifica Evento</button>
                <% } else if (isPartecipante) { %>
                    <% if (isLessThan24HoursBeforeStart) { %>
                        <div class="warning-message">Se procedi alla cancellazione subirai una penalizzazione!</div>
                    <% } %>
                    <button class="cancel-button">Cancella Prenotazione</button>
                <% } else { %>
                    <button class="join-button">Partecipa</button>
                <% } %>
            <% } else { %>
                <% if (isOrganizzatore) { %>
                    <button class="finish-button">Termina Evento</button>
                <% } %>
            <% } %>
        </div>

        <h2>Partecipanti</h2>
        <ul class="participants-list">
            <% if (partecipanti.isEmpty()) { %>
                <li>Nessun partecipante iscritto</li>
            <% } else { %>
                <% for (PrenotazioneBean p : partecipanti) { %>
                    <li class="participant-item">
                        <span><%= p.getUtenteUsername() %> <% if (p.getUtenteUsername().equals(organizzatore)) { %><i class="fas fa-crown" title="Organizzatore"></i><% } %></span>
                        <div class="participant-actions">
                            <button class="rate-button <%= isTerminato && isWithin24HoursAfterEnd ? "enabled" : "disabled" %>">😊</button>
                            <button class="rate-button <%= isTerminato && isWithin24HoursAfterEnd ? "enabled" : "disabled" %>">😐</button>
                            <button class="rate-button <%= isTerminato && isWithin24HoursAfterEnd ? "enabled" : "disabled" %>">☹️</button>
                            <button class="report-button <%= isTerminato && isWithin24HoursAfterEnd ? "red" : "disabled" %>">Segnala</button>
                        </div>
                    </li>
                <% } %>
            <% } %>
        </ul>
    </div>
    <% 
            } else { 
    %>
        <p>Evento non trovato.</p>
    <% 
            }
        } catch (Exception e) {
            e.printStackTrace();
    %>
        <p>Si &egrave; verificato un errore durante il caricamento dei dettagli dell'evento.</p>
    <% 
        }
    %>
</div>

<jsp:include page="../pages/footer.jsp" />

<div id="flashMessage" class="flash-message"></div>
</body>
</html>
