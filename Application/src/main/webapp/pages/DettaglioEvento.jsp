<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*, model.evento.Evento, model.evento.EventoDao, model.prenotazione.PrenotazioneDAO, model.prenotazione.PrenotazioneBean" %>
<%@ page import="model.Utente" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!--=============== CSS ===============-->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/evento/DettagliEvento.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/styles.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/card/card.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <title>Dettagli Evento</title>
</head>
<body>

<jsp:include page="../navbar/navbar.jsp" />
<div class="event-details">
    <%
        try {
            int eventoId = Integer.parseInt(request.getParameter("id"));

            Evento evento = EventoDao.findById(eventoId); // Usa un DAO statico per recuperare l'evento
            PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
            List<PrenotazioneBean> partecipanti = prenotazioneDAO.findPrenotazioniByEvento(eventoId);
            String organizzatore = prenotazioneDAO.findOrganizzatoreByEventoID(eventoId);

            if (evento != null) {
    %>
    <div class="event-info">
        <h1><%= evento.getTitolo() %></h1>
        <h2>Data: <%= evento.getData_inizio() %></h2>
        <h3>Luogo: <%= evento.getCitta() %></h3>
        <h3>Prezzo: &euro;<%= String.format("%.2f", evento.getPrezzo()) %></h3>
        <h3>Organizzatore: <%= organizzatore != null ? organizzatore : "Non specificato" %></h3>
        <h3>Partecipanti:</h3>
        <ul>
            <% if (partecipanti.isEmpty()) { %>
                <li>Nessun partecipante iscritto</li>
            <% } else { %>
                <% for (PrenotazioneBean p : partecipanti) { %>
                    <li><%= p.getUtenteUsername() %></li>
                <% } %>
            <% } %>
        </ul>
    </div>
    <% } else { %>
        <p>Evento non trovato.</p>
    <% } %>
    <%
        } catch (Exception e) {
            e.printStackTrace();
    %>
        <p>Si &egrave; verificato un errore durante il caricamento dei dettagli dell'evento.</p>
    <%
        }
    %>
</div>

<div class="titolo">
    <h1>Altri eventi correlati</h1>
</div>
<div class="containerE">
    <%
        try {
            if (evento != null) {
                List<Evento> correlati = EventoDao.findCorrelati(evento.getSport(), eventoId);
                int i = 0;
                for (Evento e : correlati) {
                    if (i >= 4) break;
    %>
    <div class="card">
        <div class="contentBx">
            <h2><%= e.getTitolo() %></h2>
            <p>Luogo: <%= e.getCitta() %></p>
            <p>Data: <%= e.getData_inizio() %></p>
            <a href="<%= request.getContextPath() %>/evento/DettagliEvento.jsp?id=<%= e.getId() %>" class="btn">Dettagli</a>
        </div>
    </div>
    <%
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
    %>
        <p>Errore durante il caricamento degli eventi correlati.</p>
    <%
        }
    %>
</div>

<jsp:include page="../footer/footer.jsp" />

<!-- Div per mostrare i messaggi flash -->
<div id="flashMessage" class="flash-message"></div>
</body>
</html>