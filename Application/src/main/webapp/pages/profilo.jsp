<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.*, javax.servlet.*, java.io.*, model.*" %>
<%@ page import="model.utente.UtenteBean" %>
<%
    // Recupera l'utente dalla session o dalla request
    UtenteBean utente = (UtenteBean) request.getAttribute("utente");

    if (utente == null) {
    	System.out.println("error Profilo");
        response.sendRedirect("login.jsp");
        return;
    }

    // Reputazione per scegliere la faccina
    int reputazione = (Integer) request.getAttribute("reputazione");
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
    
    			
			<% if (request.getAttribute("errore") != null || request.getAttribute("successo") != null) { %>
		    <div class="messaggi <% if (request.getAttribute("errore") != null) { %>errore<% } else if (request.getAttribute("successo") != null) { %>successo<% } %>" id="messaggio">
		        <span>
		            <% if (request.getAttribute("errore") != null) { %>
		                <%= request.getAttribute("errore") %>
		            <% } else if (request.getAttribute("successo") != null) { %>
		                <%= request.getAttribute("successo") %>
		            <% } %>
		        </span>
		        <button class="close-btn" onclick="chiudiMessaggio()">×</button>
		    </div>
		<% } %>
		
        <h1>Ciao, <%= utente.getUsername() %>!</h1>
        <div class="profilo-box">
            <h2>I tuoi dati</h2>
            <hr>
            <div class="campo-profilo">
                <span>Username:</span>
                <span><%= utente.getUsername() %></span>
            </div>
            <div class="campo-profilo">
                <span>Password:</span>
                <input id="password" name="password" type="password" value="********" readonly class="input-disabilitato" />
            </div>
            <div class="campo-profilo">
                <span>Nome:</span>
                <span><%= utente.getNome() %></span>
            </div>
            <div class="campo-profilo">
                <span>Cognome:</span>
                <span><%= utente.getCognome() %></span>
            </div>
            <div class="campo-profilo">
                <span>Email:</span>
                <input id="email" name="email" type="text" value="<%= utente.getEmail() %>" readonly class="input-disabilitato" />
            </div>
            <div class="campo-profilo">
                <span>Data di nascita:</span>
                <span><%= utente.getDataDiNascita() %></span>
            </div>
            <div class="campo-profilo">
                <span>Reputazione:</span>
                <span>
                    <% if (reputazione == -1) { %>
                        <img src="IMG/emojiNegativa.png" alt="Reputazione Negativa">
                    <% } else if (reputazione == 0) { %>
                        <img src="IMG/emojiNeutra.png" alt="Reputazione Neutra">
                    <% } else { %>
                        <img src="IMG/emojiPositiva.png" alt="Reputazione Positiva">
                    <% } %>
                </span>
            </div>

            <!-- Form che invia alla servlet -->
            <form action="ModificaDatiServlet" method="POST" id="modificaDatiForm">
			    <div class="azioni">
			        <button type="button" id="modificaDati" onclick="attivaModifica()">Modifica Dati</button>
			        <div id="modificaButtons" style="display: none;">
			            <button type="button" id="annulla" onclick="annullaModifica('<%= utente.getEmail() %>')">Annulla</button>
			            <button type="button" id="salva" onclick="salvaDati()">Salva</button>
			        </div>
			    </div>
			</form>


        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>