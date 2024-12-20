<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WePlay - Registrazione</title>
    <!-- CSS specifico per il contenuto centrale -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/register.css">
    <link rel="stylesheet" href="CSS/alert.css">
    <script src="JS/alert.js" defer></script>
    <!-- Inclusione del file JS per la validazione -->
    <script src="${pageContext.request.contextPath}/JS/validateRegister.js" defer></script>
</head>
<body>
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
    <!-- Import della navbar -->
    <jsp:include page="navbar.jsp" />

    <main class="main-content">
        <section class="register-section">
            <h1>REGISTRAZIONE</h1>
            <div class="register-box">
                <form action="${pageContext.request.contextPath}/RegisterServlet" method="post" onsubmit="validateRegistrationForm(event)">
                    <div class="form-group">
                        <input type="text" name="nome" id="nome" placeholder="Nome" required>
                    </div>
                    <div class="form-group">
                        <input type="text" name="cognome" id="cognome" placeholder="Cognome" required>
                    </div>
                    <div class="form-group">
                        <input type="date" name="data_nascita" id="data_nascita" required>
                    </div>
                    <div class="form-group">
                        <input type="email" name="email" id="email" placeholder="Email" required>
                    </div>
                    <div class="form-group">
                        <input type="text" name="username" id="username" placeholder="Username" required>
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" id="password" placeholder="Password" required>
                    </div>
                    <div class="form-group">
                        <button type="submit">REGISTRATI</button>
                    </div>
                </form>
                <p><strong>Hai già un account? <a href="login.jsp">Accedi qui!</a></strong></p>
            </div>
        </section>
    </main>

    <!-- Import del footer -->
    <jsp:include page="footer.jsp" />
</body>
</html>