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
                <th>Nome</th>
                <th>Cognome</th>
                <th>Username</th>
                <th>Data di Nascita</th>
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
                <td><%= utente.getNome() %></td>
                <td><%= utente.getCognome() %></td>
                <td><%= utente.getUsername() %></td>
                <td><%= utente.getDataDiNascita() %></td>
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
                <td colspan="4">Nessun utente trovato.</td>
            </tr>
            <% 
                } 
            %>
        </tbody>
    </table>
</body>
</html>
