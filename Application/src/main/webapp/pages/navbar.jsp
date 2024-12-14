<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.utente.UtenteBean, java.sql.SQLException, model.utente.UtenteDAO" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/navbar.css">

<%
    // Verifica se l'utente è loggato
    Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
    isLoggedIn = (isLoggedIn != null && isLoggedIn);

    // Ottieni informazioni sull'utente dalla sessione
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    boolean isAdmin = (utente != null) ? utente.isAdmin() : false;
%>

<head>
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>

<header class="header">
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/pages/index.jsp">
            <img src="${pageContext.request.contextPath}/IMG/logo_WePlay.png" alt="WePlay Logo">
        </a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=calcio"><ion-icon name="football-outline"></ion-icon></a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=tennis"><ion-icon name="tennisball-outline"></ion-icon></a>
        <a href="esploraEventi.jsp"><h1>ESPLORA EVENTI</h1></a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=basket"><ion-icon name="basketball-outline"></ion-icon></a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=bocce"><ion-icon name="bowling-ball-outline"></ion-icon></a>
        <a href="#" id="accountLink"><ion-icon name="person-circle-outline"></ion-icon></a>
    </nav>
</header>

<script>
document.addEventListener("DOMContentLoaded", function() {
    // Verifica se l'utente è loggato e il ruolo
    var isLoggedIn = <%= isLoggedIn %>;
    var isAdmin = <%= isAdmin %>;
    var accountLink = document.getElementById("accountLink");

    if (!isLoggedIn) {
        // Se non è loggato, reindirizza alla pagina di login
        accountLink.href = "${pageContext.request.contextPath}/pages/login.jsp";
    } else {
        // Se è loggato, reindirizza alla dashboard corretta in base al ruolo
        accountLink.href = isAdmin 
            ? "${pageContext.request.contextPath}/pages/adminDashboard.jsp" 
            : "${pageContext.request.contextPath}/pages/userDashboard.jsp";
    }
});
</script>
