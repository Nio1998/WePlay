<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.evento.Evento" %>
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
    <a href="${pageContext.request.contextPath}/pages/adminDashboard.jsp">
        <button class="dashboard-button">Torna alla Dashboard</button>
    </a>
        <h1>Lista Eventi</h1>

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
            Collection<Evento> eventi = (Collection<Evento>) request.getAttribute("eventi");
            if (eventi != null && !eventi.isEmpty()) {
                for (Evento evento : eventi) {
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
                    <form action="${pageContext.request.contextPath}/ModificaEventoServlet" method="post" style="display:inline;">
                          <input type="hidden" name="eventoID" value="<%= evento.getID() %>">
                                <button type="submit" class="modifica">Modifica</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/CancellaEventoServlet" method="post" style="display:inline;">
                          <input type="hidden" name="eventoID" value="<%= evento.getID() %>">
                             	<button type="submit" class="elimina">Elimina</button>
                    </form>
              </div>
            </td>
        
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

    </div>
</body>
</html>
