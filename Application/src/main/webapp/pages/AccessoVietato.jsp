<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accesso Negato</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/CSS/AccessoVietato.css">
    <script>
        var countdown = 2; // Conto alla rovescia di 5 secondi
        function startCountdown() {
            var countdownElement = document.getElementById("countdown");
            var interval = setInterval(function() {
                countdown--;
                countdownElement.textContent = countdown;
                if (countdown <= 0) {
                    clearInterval(interval);
                    window.location.href = "<%= request.getContextPath() %>/pages/index.jsp";
                }
            }, 1000);
        }
    </script>
</head>
<body onload="startCountdown()">
    <div class="container">
        <h1>NON SEI AUTORIZZATO AD ACCEDERE A QUESTA PAGINA</h1>
        <p>Verrai reindirizzato alla homepage in <span id="countdown">2</span> secondi.</p>
    </div>
</body>
</html>
