<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.evento.Evento, model.evento.EventoService, java.time.LocalDate, java.time.LocalTime" %>
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

    <!-- Form dei filtri -->
    <div class="filters">
        <form action="${pageContext.request.contextPath}/pages/esploraEventi.jsp" method="get">
            <!-- Filtro generico per sport -->
            <div class="filter-inputs">
                <label for="sport">Sport:</label>
                <input type="text" name="sport" id="sport" value="${param.sport}" placeholder="Inserisci uno sport">
            </div>

            <!-- Filtro per titolo -->
            <div class="filter-inputs">
                <label for="titolo">Titolo:</label>
                <input type="text" name="titolo" id="titolo" value="${param.titolo}" placeholder="Inserisci un titolo">
            </div>

            <!-- Filtro per indirizzo -->
            <div class="filter-inputs">
                <label for="indirizzo">Indirizzo:</label>
                <input type="text" name="indirizzo" id="indirizzo" value="${param.indirizzo}" placeholder="Inserisci un indirizzo">
            </div>

            <!-- Filtro per massimo partecipanti -->
            <div class="filter-inputs">
                <label for="massimoPartecipanti">Massimo Partecipanti:</label>
                <input type="number" name="massimoPartecipanti" id="massimoPartecipanti" value="${param.massimoPartecipanti}">
            </div>

            <!-- Filtro per data inizio -->
            <div class="filter-inputs">
                <label for="dataInizio">Data Inizio:</label>
                <input type="date" name="dataInizio" id="dataInizio" value="${param.dataInizio}">
            </div>

            <!-- Filtro per data fine -->
            <div class="filter-inputs">
                <label for="dataFine">Data Fine:</label>
                <input type="date" name="dataFine" id="dataFine" value="${param.dataFine}">
            </div>

            <!-- Filtro per ora inizio -->
            <div class="filter-inputs">
                <label for="oraInizio">Ora Inizio:</label>
                <input type="time" name="oraInizio" id="oraInizio" value="${param.oraInizio}">
            </div>

            <!-- Filtro per prezzo minimo -->
            <div class="filter-inputs">
                <label for="prezzoMin">Prezzo Minimo:</label>
                <input type="number" name="prezzoMin" id="prezzoMin" step="0.01" value="${param.prezzoMin}">
            </div>

            <!-- Filtro per prezzo massimo -->
            <div class="filter-inputs">
                <label for="prezzoMax">Prezzo Massimo:</label>
                <input type="number" name="prezzoMax" id="prezzoMax" step="0.01" value="${param.prezzoMax}">
            </div>

            <!-- Filtro per città -->
            <div class="filter-inputs">
                <label for="citta">Città:</label>
                <input type="text" name="citta" id="citta" value="${param.citta}" placeholder="Inserisci una città">
            </div>

            <!-- Filtro per stato -->
            <div class="filter-inputs">
                <label for="stato">Stato:</label>
                <input type="text" name="stato" id="stato" value="${param.stato}" placeholder="Inserisci uno stato">
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
                Collection<Evento> eventiFiltrati = eventoService.filtra_eventi(dataInizio, dataFine, oraInizio, prezzoMin, prezzoMax, 
                                                                           sport, titolo, indirizzo, massimoPartecipanti, 
                                                                           citta, stato);

                // Numero di eventi per pagina
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

                for (Evento evento : eventiPaginati) {
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
            <% } %>
        </div>

        <!-- Navigazione tra le pagine -->
        <div class="pagination">
            <% 
                for (int i = 1; i <= totalPages; i++) { 
                    String linkClass = (i == currentPage) ? "active-page" : "";
            %>
            <a href="${pageContext.request.contextPath}/pages/esploraEventi.jsp?page=<%= i %>&dataInizio=<%= dataInizio %>&dataFine=<%= dataFine %>&oraInizio=<%= oraInizio %>&prezzoMin=<%= prezzoMin %>&prezzoMax=<%= prezzoMax %>&sport=<%= sport %>&titolo=<%= titolo %>&indirizzo=<%= indirizzo %>&massimoPartecipanti=<%= massimoPartecipanti %>&citta=<%= citta %>&stato=<%= stato %>" class="<%= linkClass %>"><%= i %></a>
            <% } %>
        </div>
    </div>

    <!-- Footer incluso -->
    <jsp:include page="/pages/footer.jsp" />
</body>
</html>
