<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.evento.Evento" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eventi Sottoscritti</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/eventiSottoscritti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/card.css">
</head>
<body>
    <!-- Navbar inclusa -->
    <jsp:include page="/pages/navbar.jsp" />

    <!-- Titolo dinamico -->
    <div class="page-title">
        <h1>Esplora i tuoi eventi sottoscritti!</h1>
    </div>

    <div class="main-content">
        <!-- Contenitore degli eventi -->
        <div class="event-container">
            <%
                // Recuperiamo la lista degli eventi passata dalla servlet
                Object eventiObj = request.getAttribute("eventi");
                List<Evento> eventi = null;
                

                if (eventiObj instanceof List<?>) {
                    eventi = (List<Evento>) eventiObj;
                }
                
                

                // Paginazione
                int eventiPerPagina = 15; // 3 colonne x 5 righe
                int currentPage = 1; // Imposta una pagina predefinita
                try {
                    String pageParam = request.getParameter("page");
                    if (pageParam != null) {
                        currentPage = Integer.parseInt(pageParam);
                    }
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }

                if (eventi != null && !eventi.isEmpty()) {
                    int start = (currentPage - 1) * eventiPerPagina;
                    int end = Math.min(start + eventiPerPagina, eventi.size());

                    // Cicliamo sugli eventi per la visualizzazione
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
                    <a href="${pageContext.request.contextPath}/pages/DettaglioEvento.jsp?id=<%= evento.getID() %>" class="details-link">Dettagli</a>
                </div>
            </div>
            <% 
                    }
                } else {
            %>
            <p>Nessun evento sottoscritto disponibile.</p>
            <% 
                }
            %>
        </div>

        <!-- Navigazione tra le pagine -->
        <div class="pagination">
            <% 
                if (eventi != null && !eventi.isEmpty()) {
                    int totalPages = (int) Math.ceil((double) eventi.size() / eventiPerPagina);

                    for (int i = 1; i <= totalPages; i++) {
                        String linkClass = (i == currentPage) ? "active-page" : "";
            %>
            <a href="${pageContext.request.contextPath}/pages/eventiSottoscritti.jsp?page=<%= i %>&eventi=<%= eventi %>" class="<%= linkClass %>"><%= i %></a>

            <% 
                    }
                }
            %>
        </div>
    </div>

    <!-- Footer incluso -->
    <jsp:include page="/pages/footer.jsp" />
</body>
</html>
