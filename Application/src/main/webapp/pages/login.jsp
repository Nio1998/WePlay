<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WePlay - Login</title>
    <!-- CSS specifico per il contenuto centrale -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login.css">
    <!-- Script per le icone -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<body>
    <!-- Import della navbar -->
    <jsp:include page="navbar.jsp" />

    <main class="main-content">
        <section class="login-section">
            <h1>LOGIN</h1>
            <div class="login-box">
                <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                    <div class="form-group">
                        <input type="text" name="username" id="username" placeholder="Username o Email" required>
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" id="password" placeholder="Password" required>
                    </div>
                    <div class="form-group">
                        <button type="submit">ACCEDI</button>
                    </div>
                </form>
                <p><strong>Non sei registrato? <a href="${pageContext.request.contextPath}/RegisterServlet">Registrati qui!</a></strong></p>
            </div>
        </section>
    </main>

    <!-- Import del footer -->
    <jsp:include page="footer.jsp" />
</body>
</html>
