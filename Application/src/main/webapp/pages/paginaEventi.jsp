<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.evento.Evento, model.evento.EventoService, java.time.LocalDate, java.time.LocalTime" %>
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
        <h1>Esplora tutti gli eventi</h1>
    </div>

<div class="filters">
    <form action="${pageContext.request.contextPath}/pages/esploraEventi.jsp" method="get" class="filter-form">
        <!-- Contenitore dei filtri sulla stessa riga -->
        <div class="filter-box">
            <label for="sport">Sport:</label>
            <input type="text" name="sport" id="sport" value="${param.sport}" placeholder="Inserisci uno sport">
        </div>

        <div class="filter-box">
            <label for="dataInizio">Data:</label>
            <input type="date" name="dataInizio" id="dataInizio" value="${param.dataInizio}">
        </div>

        <div class="filter-box">
            <label for="citta">Città:</label>
            <input type="text" name="citta" id="citta" value="${param.citta}" placeholder="Inserisci una città">
        </div>

        <!-- Bottone di conferma -->
        <div class="filter-submit">
            <button type="submit" name="applyFilter" value="true">Applica Filtro</button>
        </div>
    </form>
</div>

    <div class="main-content">
        <!-- Contenitore degli eventi -->
        <div class="event-container">
            <% 
                String dataInizio = request.getParameter("dataInizio");
                String dataFine = request.getParameter("dataFine");
                String oraInizio = request.getParameter("oraInizio");
                String prezzoMin = request.getParameter("prezzoMin");
                String prezzoMax = request.getParameter("prezzoMax");
                String sport = request.getParameter("sport");
                String titolo = request.getParameter("titolo");
                String indirizzo = request.getParameter("indirizzo");
                String massimoPartecipanti = request.getParameter("massimoPartecipanti");
                String citta = request.getParameter("citta");
                String stato = request.getParameter("stato");
                
                // Chiamata al DAO per applicare il filtro
                EventoService eventoService = new EventoService();
                Collection<Evento> eventiFiltrati = eventoService.filtra_eventi(dataInizio, null, null, null, null, 
                                                                           sport, null, null, null, 
                                                                           citta, "non iniziato");
	
             	// Verifica se ci sono eventi
                if (eventiFiltrati.isEmpty()) {
                    out.println("<p>No events found matching the filters.</p>");
                }
             
                //Numero di eventi per pagina
                int eventiPerPagina = 10;

                // Pagina attuale (recuperata dal parametro della richiesta)
                String pageParam = request.getParameter("page");
                int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;

                // Calcolo totale delle pagine
                int totalPages = (int) Math.ceil((double) eventiFiltrati.size() / eventiPerPagina);

                // Determinare l'indice di partenza e fine per gli eventi della pagina corrente
                List<Evento> eventiPaginati = new ArrayList<>(eventiFiltrati);
                int startIndex = (currentPage - 1) * eventiPerPagina;
                int endIndex = Math.min(startIndex + eventiPerPagina, eventiPaginati.size());

                // Estrarre solo gli eventi della pagina corrente
                eventiPaginati = eventiPaginati.subList(startIndex, endIndex);

                for (Evento evento : eventiFiltrati) {
            %>
            <div class="card event-card">
                <div class="card-header">
                    <h2><%= evento.getTitolo() %></h2>
                </div>
                <div class="card-body">
                	<p class="sport"><%= evento.getSport() %></p>
                    <p class="event-description"><%= evento.getCitta() %></p>
                    <p class="event-location">Luogo: <%= evento.getIndirizzo() %></p>
                    <p class="event-date">Data: <%= evento.getData_inizio() %></p>
                </div>
                <div class="card-footer">
                    <p>Posti disponibili: <%= evento.getMassimo_di_partecipanti() %></p>
                    
                    <!-- Form che invia la richiesta alla servlet -->
		            <form action="${pageContext.request.contextPath}/DettagliEvento" method="POST">
		                <input type="hidden" name="eventoId" value="<%= evento.getID() %>">
		                <input type="hidden" name="attributo" value="esploraEventi">
		                <button type="submit" class="details-link">Dettagli</button>
		            </form>
		            
		            
                </div>
            </div>
            <% } %>
        </div>

        <!-- Navigazione tra le pagine --> 
        <div class="pagination">
            <% 
                for (int i = 1; i <= totalPages; i++) { 
                    String linkClass = (i == currentPage) ? "active-page" : "";
            %>
            <a href="${pageContext.request.contextPath}/pages/esploraEventi.jsp?page=<%= i %><%= dataInizio != null ? "&dataInizio="+dataInizio : "" %><%= dataFine != null ? "&dataFine="+dataFine : "" %><%= oraInizio != null ? "&oraInizio="+oraInizio : "" %><%= prezzoMin != null ? "&prezzoMin="+prezzoMin : "" %><%= prezzoMax != null ? "&prezzoMax="+ prezzoMax : "" %><%= sport != null ? "&sport="+sport : "" %><%= titolo != null ? "&titolo="+titolo : "" %><%= indirizzo != null ? "&indirizzo="+indirizzo : "" %><%= massimoPartecipanti != null ? "&massimoPartecipanti="+massimoPartecipanti : "" %><%= citta != null ? "&citta="+citta : "" %><%= stato != null ? "&stato="+stato : "" %>" class="<%= linkClass %>"><%= i %></a>
            <% } %>
        </div>
    </div>

   <!-- Footer incluso -->
    <jsp:include page="/pages/footer.jsp" />
</body>
</html>
