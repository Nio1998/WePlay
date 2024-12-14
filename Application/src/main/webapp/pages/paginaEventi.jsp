<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.evento.Evento, model.evento.EventoDao" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eventi Sportivi</title>
    <!-- Collegamento ai file CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/paginaEventi.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/card.css">
</head>
<body>
    <!-- Navbar inclusa -->
    <jsp:include page="/pages/navbar.jsp" />

    <!-- Titolo dinamico -->
    <div class="page-title">
        <h1>Eventi di <%= request.getParameter("sport") != null ? request.getParameter("sport") : "tutti gli sport" %></h1>
    </div>
	<div class= "main-content">
    <!-- Contenitore degli eventi -->
    <div class="event-container">
        <% 
            String sport = request.getParameter("sport");
            EventoDao eventoDao = new EventoDao();
            List<Evento> eventi;

            if (sport != null && !sport.isEmpty()) {
                eventi = eventoDao.getEventiBySport(sport);
            } else {
                eventi = eventoDao.getAll();
            }

            for (Evento evento : eventi) {
        %>
        <div class="card event-card">
            <div class="card-header">
                <h2><%= evento.getTitolo() %></h2>
            </div>
            <div class="card-body">
                <p class="event-description"><%= evento.getCitta() %></p>
                <p class="event-location">Luogo: <%= evento.getIndirizzo() %></p>
                <p class="event-date">Data: <%= evento.getData_inizio() %></p>
            </div>
            <div class="card-footer">
                <p>Posti disponibili: <%= evento.getMassimo_di_partecipanti() %></p>
                <a href="${pageContext.request.contextPath}/pages/dettagliEvento.jsp?id=<%= evento.getID() %>" class="details-link">Dettagli</a>
            </div>
        </div>
        <% } %>
    </div>
</div>
    <!-- Footer incluso -->
    <jsp:include page="/pages/footer.jsp" />
</body>
</html>
