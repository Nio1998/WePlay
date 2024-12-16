<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*, model.evento.Evento, model.prenotazione.PrenotazioneDAO, model.prenotazione.PrenotazioneBean, model.utente.UtenteBean" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/DettaglioEvento.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<link rel="stylesheet" href="CSS/alert.css">
	<script src="JS/alert.js" defer></script>
	
    <title>Dettagli Evento</title>
</head>
<body>

 <!-- Import della navbar -->
    <jsp:include page="navbar.jsp" />
    
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

<div class="main-content">  
    <div class="event-details">
        <% 
            Evento evento = (Evento) request.getAttribute("evento");
            List<UtenteBean> partecipanti = (List<UtenteBean>) request.getAttribute("partecipanti");
            boolean isPartecipante = (boolean) request.getAttribute("isPartecipante");
            boolean isOrganizzatore = (boolean) request.getAttribute("isOrganizzatore");
            boolean isTerminato = (boolean) request.getAttribute("isTerminato");
            boolean isLessThan24HoursBeforeStart = (boolean) request.getAttribute("isLessThan24HoursBeforeStart");
            boolean isWithin24HoursAfterEnd = (boolean) request.getAttribute("isWithin24HoursAfterEnd");
            String attributo = (String) request.getAttribute("attributo");
            System.out.println("es1: " + attributo);
            Map<UtenteBean, Integer> utentiValutazioni = (Map<UtenteBean, Integer>) request.getAttribute("utentiValutazione");
            
	        // Verifica e gestione della lista partecipanti
	            if (partecipanti != null && utentiValutazioni == null) {
	                utentiValutazioni = new HashMap<>();
	                for (UtenteBean partecipante : partecipanti) {
	                    utentiValutazioni.put(partecipante, -2); // -2 indica "non valutato"
	                }
	            }
	            

            if (evento != null) {
        %>
        <div class="event-info">
            <div class="event-details-grid">
                <div><strong>Titolo:</strong> <%= evento.getTitolo() %></div>
                <div><strong>Luogo:</strong> <%= evento.getCitta() %>, <%= evento.getIndirizzo() %></div>
                <div><strong>Prezzo:</strong> &euro;<%= String.format("%.2f", evento.getPrezzo()) %></div>
                <div><strong>Sport:</strong> <%= evento.getSport() %></div>
                <div><strong>Partecipanti:</strong> <%= partecipanti.size() %> / <%= evento.getMassimo_di_partecipanti() %></div>
                <div><strong>Orario:</strong> <%= evento.getData_inizio() %></div>
            </div>

            <div class="event-actions">
                <% if (!isTerminato) { %>
                    <% if ( !attributo.isEmpty() && attributo.equals("organizzatore")) { %>
                        <button class="edit-button">Modifica Evento</button>
                        <button class="edit-button">Cancella Evento</button>
                    <% } else if (isPartecipante) { %>
                        <% if (isLessThan24HoursBeforeStart) { %>
                            <div class="warning-message">Se procedi alla cancellazione subirai una penalizzazione!</div>
                        <% } %>
                        
                        <form action="${pageContext.request.contextPath}/CancellaPrenotazioneServlet" method="post">
                            <input type="hidden" name="eventoID" value="<%= evento.getID() %>">
                            <input type="hidden" name="attributo" value="esploraEventi">
                            <button class="cancel-button" type="submit">Cancella Prenotazione</button>
                        </form>
                    <% } else { %>
                        <form action="${pageContext.request.contextPath}/CreaPrenotazioneServlet" method="post">
                            <input type="hidden" name="eventoID" value="<%= evento.getID() %>">
                            <input type="hidden" name="attributo" value="esploraEventi">
                            <button class="join-button" type="submit">Partecipa</button>
                        </form>
                    <% } %>
                <% } else { %>
                    <% if (isOrganizzatore) { %>
                        <button class="finish-button">Termina Evento</button>
                    <% } %>
                <% } %>
            </div>

            <h2>Partecipanti</h2>
				<ul class="participants-list">
				    <% if (utentiValutazioni.isEmpty()) { %>
				        <li>Nessun partecipante iscritto</li>
				    <% } else { %>
				        <% for (Map.Entry<UtenteBean, Integer> entry : utentiValutazioni.entrySet()) { 
				            UtenteBean utente = entry.getKey();
				            int esito = entry.getValue(); %>
				            <li class="participant-item">
				                <span>
				                    <%= utente.getUsername() %>
				                    <% if (utente.getUsername().equals(request.getAttribute("organizzatore")) ) { %>
				                        <i class="fas fa-crown" title="Organizzatore"></i>
				                    <% } %>
				                </span>
				                <% if (!"esploraEventi".equals(attributo)) { %>
				                <div class="participant-actions">
				                    <% if (!isTerminato) { %>
				                        <!-- Evento non terminato: Emoji bloccate -->
				                        <img src="<%= request.getContextPath() %>/IMG/emojiNegativaBlocked.svg" alt="Emoji Negativa" class="emoji-icon" />
				                        <img src="<%= request.getContextPath() %>/IMG/emojiNeutraBlocked.svg" alt="Emoji Neutra" class="emoji-icon" />
				                        <img src="<%= request.getContextPath() %>/IMG/emojiPositivaBlocked.svg" alt="Emoji Positiva" class="emoji-icon" />
				                    <% } else { 
				                        // Evento terminato: Controlla esito
				                        switch (esito) {
				                        case -2: // Non valutato
				                            if (isWithin24HoursAfterEnd) { 
				                                // Mostra le immagini Clickable
				                        %>		
				                        <%
				                       	
				                        %>
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNegativaClickable.svg" alt="Emoji Negativa" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNeutraClickable.svg" alt="Emoji Neutra" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiPositivaClickable.svg" alt="Emoji Positiva" class="emoji-icon" />
				                        <%
				                            } else { 
				                                // Mostra le immagini Blocked
				                        %>
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNegativaBlocked.svg" alt="Emoji Negativa" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNeutraBlocked.svg" alt="Emoji Neutra" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiPositivaBlocked.svg" alt="Emoji Positiva" class="emoji-icon" />
				                        <%
				                            }
				                            break;
				                            case -1: // Valutazione negativa
				                   
				                    	if (isWithin24HoursAfterEnd) { 
				                                // Mostra le immagini Clickable
				                        %>		
				                        <%
				                       	
				                        %>
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNegativa.png" alt="Emoji Negativa" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNeutraClickable.svg" alt="Emoji Neutra" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiPositivaClickable.svg" alt="Emoji Positiva" class="emoji-icon" />
				                        <%
				                            } else { 
				                                // Mostra le immagini Blocked
				                        %>
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNegativa.png" alt="Emoji Negativa" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNeutraBlocked.svg" alt="Emoji Neutra" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiPositivaBlocked.svg" alt="Emoji Positiva" class="emoji-icon" />
				                        <%
				                            }
				                    
				                   
				                            break;
				                            case 0: // Valutazione neutra
				                   
				                    if (isWithin24HoursAfterEnd) { 
				                                // Mostra le immagini Clickable
				                        %>		
				                        <%
				                       	
				                        %>
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNegativaClickable.svg" alt="Emoji Negativa" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNeutra.png" alt="Emoji Neutra" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiPositivaClickable.svg" alt="Emoji Positiva" class="emoji-icon" />
				                        <%
				                            } else { 
				                                // Mostra le immagini Blocked
				                        %>
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNegativaBlocked.svg" alt="Emoji Negativa" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNeutra.png" alt="Emoji Neutra" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiPositivaBlocked.svg" alt="Emoji Positiva" class="emoji-icon" />
				                        <%
				                            }
				                        
				                    
				                            break;
				                            case 1: // Valutazione positiva
				                   
				                    if (isWithin24HoursAfterEnd) { 
				                                // Mostra le immagini Clickable
				                        %>		
				                        <%
				                       	
				                        %>
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNegativaClickable.svg" alt="Emoji Negativa" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNeutraClickable.svg" alt="Emoji Neutra" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiPositiva.png" alt="Emoji Positiva" class="emoji-icon" />
				                        <%
				                            } else { 
				                                // Mostra le immagini Blocked
				                        %>
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNegativaBlocked.svg" alt="Emoji Negativa" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiNeutraBlocked.svg" alt="Emoji Neutra" class="emoji-icon" />
				                                <img src="<%= request.getContextPath() %>/IMG/emojiPositiva.png" alt="Emoji Positiva" class="emoji-icon" />
				                        <%
				                            }
				                        
				                            break;
				                        }
				                        
				                    } %>
				                      <!-- Bottone Segnala -->
									    <button class="report-button <%= (isTerminato && isWithin24HoursAfterEnd) ? "red" : "disabled" %>">Segnala</button>
				                </div>
				                <% } %>
				            </li>
				        <% } %>
				    <% } %>
				</ul>

        </div>
        <% 
            } else { 
        %>
            <p>Evento non trovato.</p>
        <% 
            }
        %>
    </div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
