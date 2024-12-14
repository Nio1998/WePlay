<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.utente.UtenteBean"  %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestione Utenti</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h1>Elenco Utenti</h1>

    <table border="1">
        <thead>
            <tr>
                <th>Username</th>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Data di Nascita</th>
                <th>Numero Timeout</th>
                <th>In Timeout</th>
                <th>Valutazioni Positive</th>
                <th>Valutazioni Neutre</th>
                <th>Valutazioni Negative</th>
                <th>Data Fine Timeout</th>
                <th>Azioni</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<UtenteBean> utenti = (List<UtenteBean>) request.getAttribute("listaUtenti");
                if (utenti != null) {
                    for (UtenteBean utente : utenti) {
            %>
            <tr>
                <td><%= utente.getUsername() %></td>
                <td><%= utente.getNome() %></td>
                <td><%= utente.getCognome() %></td>
                <td><%= utente.getDataDiNascita() %></td>
                <td><%= utente.getNumTimeout() %></td>
                <td><%= utente.isTimeout() ? "Sì" : "No" %></td>
                <td><%= utente.getNumValutazioniPositive() %></td>
                <td><%= utente.getNumValutazioniNeutre() %></td>
                <td><%= utente.getNumValutazioniNegative() %></td>
                <td><%= utente.getDataOraFineTimeout() != null ? utente.getDataOraFineTimeout() : "N/A" %></td>
                <td>
                    <!-- Bottone per bannare l'utente -->
                    <form action="ApplicaBan" method="post" style="display:inline;">
                        <input type="hidden" name="username" value="<%= utente.getUsername() %>" />
                        <button type="submit">Banna</button>
                    </form>

                    <!-- Bottone per dare timeout all'utente -->
                    <form action="ApplicaTimeout" method="post" style="display:inline;">
                        <input type="hidden" name="username" value="<%= utente.getUsername() %>" />
                        <input type="hidden" name="azione" value="assegna" />
                        <button type="submit">Timeout</button>
                    </form>
                </td>
            </tr>
            <% 
                    }
                } else { 
            %>
            <tr>
                <td colspan="11">Nessun utente trovato.</td>
            </tr>
            <% 
                } 
            %>
        </tbody>
    </table>
</body>
</html>
