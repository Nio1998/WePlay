<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.utente.UtenteBean"%>
<%@ page import="model.Segnalazione.Segnalazione"%>
<%@ page import="java.util.List"%>

<%
    // Recupera i dati dalla request
    UtenteBean utenteVisualizzato = (UtenteBean) request.getAttribute("utente"); // Utente di cui si sta visualizzando il profilo
    UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("utente"); // Utente che sta visualizzando la pagina
    Integer reputazione = (Integer) request.getAttribute("reputazione");
    List<Segnalazione> segnalazioni = (List<Segnalazione>) request.getAttribute("segnalazioni");

    // Controlla se l'utente loggato è presente nella sessione
    if (utenteLoggato == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profilo</title>
    <link rel="stylesheet" href="CSS/stileProfilo.css">
    <script src="JS/scriptProfilo.js" defer></script>
</head>
<body>
    <jsp:include page="navbar.jsp" />

    <div class="profilo-section">
        <!-- Messaggi di errore/successo -->
        <% if (request.getAttribute("errore") != null) { %>
            <div class="messaggi errore" id="messaggio">
                <span><%= request.getAttribute("errore") %></span>
                <button class="close-btn" onclick="chiudiMessaggio()">×</button>
            </div>

        <% } else if (request.getAttribute("successo") != null) { %>
            <div class="messaggi successo" id="messaggio">
                <span><%= request.getAttribute("successo") %></span>
                <button class="close-btn" onclick="chiudiMessaggio()">×</button>

            </div>
        <% } %>

        <!-- Saluto solo se l'utente visualizzato è lo stesso dell'utente loggato e non è admin -->
        <% if (!utenteLoggato.isAdmin() && utenteLoggato.getUsername().equals(utenteVisualizzato.getUsername())) { %>
            <h1>Ciao, <%= utenteVisualizzato.getUsername() %>!</h1>
        <% } %>
        
        <div class="profilo-box">
            <h2>Dati Profilo</h2>
            <div class="campo-profilo">
                <span>Username:</span> <span><%= utenteVisualizzato.getUsername() %></span>
            </div>
            <div class="campo-profilo">
                <span>Nome:</span> <span><%= utenteVisualizzato.getNome() %></span>
            </div>
            <div class="campo-profilo">
                <span>Cognome:</span> <span><%= utenteVisualizzato.getCognome() %></span>
            </div>
            <div class="campo-profilo">
                <span>Email:</span> <span><%= utenteVisualizzato.getEmail() %></span>
            </div>
            <div class="campo-profilo">
                <span>Data di nascita:</span> <span><%= utenteVisualizzato.getDataDiNascita() %></span>
            </div>
            <% if (reputazione != null) { %>
                <div class="campo-profilo">
                    <span>Reputazione:</span>
                    <% if (reputazione == -1) { %>
                        <img src="IMG/emojiNegativa.svg" alt="Reputazione Negativa">
                    <% } else if (reputazione == 0) { %>
                        <img src="IMG/emojiNeutra.svg" alt="Reputazione Neutra">
                    <% } else { %>
                        <img src="IMG/emojiPositiva.svg" alt="Reputazione Positiva">
                    <% } %>
                </div>
            <% } %>
        </div>

        <!-- Segnalazioni (solo per admin) -->
        <% if (segnalazioni != null && utenteLoggato.isAdmin()) { %>
            <h2 class="titolo-segnalazioni">Segnalazioni</h2>
            <% if (!segnalazioni.isEmpty()) { %>
                <table class="tabella-segnalazioni">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Motivo</th>
                            <th>Utente Segnalante</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Segnalazione s : segnalazioni) { %>
                            <tr>
                                <td><%= s.getId() %></td>
                                <td><%= s.getMotivazione() %></td>
                                <td><%= s.getUtenteSegnalante() %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <p style="text-align: center; color: #00796B;">Non ci sono segnalazioni per questo utente.</p>
            <% } %>
        <% } %>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>
