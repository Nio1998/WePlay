<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.evento.Evento, model.evento.EventoDao" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Esplora Eventi</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/paginaEventi.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/card.css">
</head>
<body>
    <!-- Navbar inclusa -->
    <jsp:include page="/pages/navbar.jsp" />

    <!-- Titolo dinamico -->
    <div class="page-title">
        <h1>Esplora tutti gli eventi</h1>
    </div>

	<div class= "main-content">
    <!-- Contenitore degli eventi -->
    <div class="event-container">
        <% 
            String sport = request.getParameter("sport");
            EventoDao eventoDao = new EventoDao();
            List<Evento> eventi = eventoDao.getAll();

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
                <a href="${pageContext.request.contextPath}/pages/DettaglioEvento.jsp?id=<%= evento.getID() %>" class="details-link">Dettagli</a>

            </div>
            <% } %>
        </div>

        <!-- Navigazione tra le pagine -->
        <div class="pagination">
            <% 
                int totalPages = (int) Math.ceil((double) eventi.size() / eventiPerPagina);

                for (int i = 1; i <= totalPages; i++) { 
                    String linkClass = (i == currentPage) ? "active-page" : "";
            %>
            <a href="${pageContext.request.contextPath}/pages/esploraEventi.jsp?page=<%= i %>" class="<%= linkClass %>"><%= i %></a>
            <% } %>
        </div>
    </div>

    <!-- Footer incluso -->
    <jsp:include page="/pages/footer.jsp" />
</body>
</html>
