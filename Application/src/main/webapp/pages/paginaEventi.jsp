<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.evento.Evento, model.evento.EventoDao" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eventi Sportivi</title>
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

    <div class="main-content">
        <!-- Contenitore degli eventi -->
        <div class="event-container">
            <%
                String sport = request.getParameter("sport");
                EventoDao eventoDao = new EventoDao();
                List<Evento> eventi = sport != null && !sport.isEmpty()
                    ? eventoDao.getEventiBySport(sport)
                    : eventoDao.getAll();

                // Paginazione
                int eventiPerPagina = 15; // 3 colonne x 5 righe
                int currentPage;
                try {
                    currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }

                int start = (currentPage - 1) * eventiPerPagina;
                int end = Math.min(start + eventiPerPagina, eventi.size());

                for (int i = start; i < end; i++) {
                    Evento evento = eventi.get(i);
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

        <!-- Navigazione tra le pagine -->
        <div class="pagination">
            <% 
                int totalPages = (int) Math.ceil((double) eventi.size() / eventiPerPagina);

                for (int i = 1; i <= totalPages; i++) { 
                    String linkClass = (i == currentPage) ? "active-page" : "";
            %>
            <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=<%= sport %>&page=<%= i %>" class="<%= linkClass %>"><%= i %></a>
            <% } %>
        </div>
    </div>

    <!-- Footer incluso -->
    <jsp:include page="/pages/footer.jsp" />
</body>
</html>
