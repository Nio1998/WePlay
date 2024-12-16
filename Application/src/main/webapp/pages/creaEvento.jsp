<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.*, javax.servlet.*, java.io.*, model.*" %>
<%@ page import="model.utente.UtenteBean" %>
<%
    // Recupera l'utente dalla session o dalla request
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");

    if (utente == null) {
    	System.out.println("error reLogin");
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>WePlay - Crea Nuovo Evento</title>
<!-- CSS specifico per il contenuto centrale -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/creaEvento.css">
<!-- Script per la validazione -->
<script
	src="${pageContext.request.contextPath}/JS/validateCreaEvento.js" defer></script>
</head>
<body>
	<!-- Import della navbar -->
	<jsp:include page="navbar.jsp" />


    <main class="main-content">
        <section class="crea-evento-section">
            <h1>CREA NUOVO EVENTO</h1>
            <div class="crea-evento-box">
                <form action="${pageContext.request.contextPath}/CreaEventoServlet" method="post" id="creaEventoForm" onsubmit="return validateCreaEventoForm(event)">
					<div class="form-group">
						<input type="text" name="titolo" id="titolo" placeholder="Titolo Evento" required>
					</div>
					<div class="form-group">
						<input type="text" name="sport" id="sport" placeholder="Sport"
							required readonly>
						<div id="sportOptions" class="dropdown-options"
							style="display: none;">
							<div class="option" onclick="selectSport('Calcio')">Calcio</div>
							<div class="option" onclick="selectSport('Basket')">Basket</div>
							<div class="option" onclick="selectSport('Pallavolo')">Pallavolo</div>
							<div class="option" onclick="selectSport('Tennis')">Tennis</div>
							<div class="option" onclick="selectSport('Ping Pong')">Ping
								Pong</div>
							<div class="option" onclick="selectSport('Badminton')">Badminton</div>
							<div class="option" onclick="selectSport('Golf')">Golf</div>
							<div class="option" onclick="selectSport('Beach Volley')">Beach
								Volley</div>
							<div class="option" onclick="selectSport('Padel')">Padel</div>
							<div class="option" onclick="selectSport('Bocce')">Bocce</div>
						</div>
					</div>
					<div class="form-group">
						<input type="text" name="indirizzo" id="indirizzo"
							placeholder="Indirizzo" required>
					</div>
					<div class="form-group">
						<input type="text" name="citta" id="citta" placeholder="Città"
							required>
					</div>
					<div class="form-group date-time-group">
						<input type="date" name="data" id="data" required> <input
							type="time" name="ora" id="ora" required>
					</div>
					<div class="form-group">
						<input type="number" name="partecipanti" id="partecipanti"
							placeholder="Partecipanti Massimi" required min="2">
					</div>
					<div class="form-group">
						<input type="number" name="prezzo" id="prezzo"
							placeholder="Prezzo" required min="0" step="0.01">
					</div>
					<div class="form-group buttons">
						<button type="reset" class="annulla-btn">ANNULLA</button>
						<button type="submit" class="crea-btn">CREA</button>
					</div>
				</form>
			</div>
		</section>
	</main>

	<!-- Import del footer -->
	<jsp:include page="footer.jsp" />
</body>
</html>
