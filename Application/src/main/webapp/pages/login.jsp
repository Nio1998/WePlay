<%@ page contentType="text/html; charset=UTF-8" %>
<%
    Boolean loginError = (Boolean) session.getAttribute("login-error");
    if (loginError == null) {
        loginError = false;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WePlay - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login.css">
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
    <jsp:include page="navbar.jsp" />
    <main class="main-content">
        <section class="login-section">
            <h1>LOGIN</h1>
            <% if (loginError) { %>
                <p class="error-message">Credenziali non valide. Riprova.</p>
            <% } %>
            <div class="login-box">
                <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                    <div class="form-group">
                        <input type="text" name="username" id="username" placeholder="Username" required>
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" id="password" placeholder="Password" required>
                    </div>
                    <div class="form-group">
                        <button type="submit">ACCEDI</button>
                    </div>
                </form>
                <p><strong>Non sei registrato? <a href="register.jsp">Registrati qui!</a></strong></p>
            </div>
        </section>
    </main>
    <jsp:include page="footer.jsp" />
</body>
</html>
