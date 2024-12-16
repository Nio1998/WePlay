<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.evento.EventoService, model.evento.Evento" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestione Eventi</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/ListaEventi.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<body>
    <div class="container">
        <!-- Intestazione Lista Eventi -->
        <h1>Lista Eventi</h1>

        <!-- Bottone Torna alla Dashboard -->
        <div class="dashboard-button-container">
            <a href="${pageContext.request.contextPath}/pages/adminDashboard.jsp">
                <button type="button" class="dashboard-button">Torna alla Dashboard</button>
            </a>
        </div>

        <!-- Form dei filtri -->
        <div class="filters">
            <form action="${pageContext.request.contextPath}/pages/ListaEventi.jsp" method="get">
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

        <!-- Tabella degli eventi -->
        <table>
            <thead>
                <tr>
                    <th></th>
                    <th>ID</th>
                    <th>Titolo</th>
                    <th>Data Inizio</th>
                    <th>Ora Inizio</th>
                    <th>Prezzo</th>
                    <th>Sport</th>
                    <th>Indirizzo</th>
                    <th>Partecipanti</th>
                    <th>Città</th>
                    <th>Stato</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
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

                    // Chiamata al servizio Evento per ottenere gli eventi filtrati
                    EventoService eventoService = new EventoService();
                    Collection<Evento> eventiFiltrati = eventoService.filtra_eventi(dataInizio, dataFine, oraInizio, prezzoMin, prezzoMax, 
                                                                               sport, titolo, indirizzo, massimoPartecipanti, 
                                                                               citta, stato);

                    if (eventiFiltrati != null && !eventiFiltrati.isEmpty()) {
                        for (Evento evento : eventiFiltrati) {
                %>
                <tr>
                    <td><i class="fas fa-clipboard-list"></i></td>
                    <td><%= evento.getID() %></td>
                    <td><%= evento.getTitolo() %></td>
                    <td><%= evento.getData_inizio() %></td>
                    <td><%= evento.getOra_inizio() %></td>
                    <td><%= evento.getPrezzo() %> €</td>
                    <td><%= evento.getSport() %></td>
                    <td><%= evento.getIndirizzo() %></td>
                    <td><%= evento.getMassimo_di_partecipanti() %></td>
                    <td><%= evento.getCitta() %></td>
                    <td><%= evento.getStato() %></td>
                    <td>
                        <div class="action-buttons">
                            <form action="#" method="post" style="display: inline;">
                                <input type="hidden" name="eventoID" value="<%= evento.getID() %>">
                                <button type="submit" class="modifica">Modifica</button>
                            </form>
                            <form action="#" method="post" style="display: inline;">
                                <input type="hidden" name="eventoID" value="<%= evento.getID() %>">
                                <button type="submit" class="elimina">Elimina</button>
                            </form>
                        </div>
                    </td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr>
                    <td colspan="12" class="empty-message">Nessun evento trovato.</td>
                </tr>
                <% 
                    } 
                %>
            </tbody>
        </table>

        <!-- Paginazione (se necessaria) -->
        <div class="pagination">
            <% 
                // Numero di eventi per pagina
                int eventiPerPagina = 10;
                int currentPage = 1;  // Impostazione della pagina corrente
                int totalPages = (int) Math.ceil((double) eventiFiltrati.size() / eventiPerPagina);
                
                for (int i = 1; i <= totalPages; i++) {
                    String linkClass = (i == currentPage) ? "active-page" : "";
            %>
            <a href="${pageContext.request.contextPath}/pages/ListaEventi.jsp?page=<%= i %>&dataInizio=<%= dataInizio %>&dataFine=<%= dataFine %>&oraInizio=<%= oraInizio %>&prezzoMin=<%= prezzoMin %>&prezzoMax=<%= prezzoMax %>&sport=<%= sport %>&titolo=<%= titolo %>&indirizzo=<%= indirizzo %>&massimoPartecipanti=<%= massimoPartecipanti %>&citta=<%= citta %>&stato=<%= stato %>" class="<%= linkClass %>"><%= i %></a>
            <% } %>
        </div>
    </div>
</body>
</html>
