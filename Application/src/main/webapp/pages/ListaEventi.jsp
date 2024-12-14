<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.evento.Evento"  %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestione Eventi</title>
    <link rel="stylesheet" type="text/css" href="CSS/ListaEventi.css">
</head>
<body>
    <h1>Elenco Eventi</h1>

    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Titolo</th>
                <th>Data Inizio</th>
                <th>Ora Inizio</th>
                <th>Sport</th>
                <th>Prezzo</th>
                <th>Città</th>
                <th>Numero Massimo di Partecipanti</th>
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
                <td><%= evento.getID() %></td>
                <td><%= evento.getTitolo() %></td>
                <td><%= evento.getData_inizio() %></td>
                <td><%= evento.getOra_inizio() %></td>
                <td><%= evento.getSport() %></td>
                <td><%= evento.getPrezzo() %> €</td>
                <td><%= evento.getCitta() %></td>
                <td><%= evento.getMassimo_di_partecipanti() %></td>
                <td><%= evento.getStato() %></td>
                <td>
                    <!-- Bottone per modificare l'evento -->
                    <form action="ModificaEventoServlet" method="get" style="display:inline;">
                        <input type="hidden" name="eventoId" value="<%= evento.getID() %>" />
                        <button type="submit">Modifica</button>
                    </form>

                    <!-- Bottone per eliminare l'evento -->
                    <form action="CancellaEventoServlet" method="post" style="display:inline;">
                        <input type="hidden" name="evento_id" value="<%= evento.getID() %>" />
                        <button type="submit">Elimina</button>
                    </form>
                </td>
            </tr>
            <% 
                    }
                } else { 
            %>
            <tr>
                <td colspan="10">Nessun evento trovato.</td>
            </tr>
            <% 
                } 
            %>
        </tbody>
    </table>
</body>
</html>
