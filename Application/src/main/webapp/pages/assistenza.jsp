<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modulo di Assistenza - WePlay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/assistenza.css">
</head>
<body>
    <!-- Navbar inclusa -->
    <jsp:include page="/pages/navbar.jsp" />

    <!-- Titolo dinamico -->
    <div class="page-title">
        <h1>Modulo di Assistenza</h1>
    </div>

    <!-- Contenitore del modulo -->
    <div class="crea-evento-box">
        <form action="${pageContext.request.contextPath}/GestioneAssistenza" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required placeholder="Inserisci la tua email">
            </div>
            <div class="form-group">
                <label for="oggetto">Oggetto</label>
                <input type="text" id="oggetto" name="oggetto" required placeholder="Inserisci l'oggetto">
            </div>
            <div class="form-group">
                <label for="messaggio">Spiegaci cosa non va</label>
                <textarea id="messaggio" name="messaggio" required placeholder="Scrivi qui il tuo messaggio"></textarea>
            </div>
            <div class="buttons">
                <button type="submit">Invia</button>
                <button type="reset">Cancella</button>
            </div>
        </form>
    </div>

    <!-- Footer incluso -->
    <jsp:include page="/pages/footer.jsp" />
</body>
</html>
