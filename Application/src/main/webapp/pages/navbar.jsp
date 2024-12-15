<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.utente.UtenteBean" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/navbar.css">



<head>
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>


<header class="header navbar-container">
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/pages/index.jsp">
            <img src="${pageContext.request.contextPath}/IMG/logo_WePlay.png" alt="WePlay Logo">
        </a>

        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=calcio">
            <ion-icon name="football-outline"></ion-icon>
        </a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=tennis">
            <ion-icon name="tennisball-outline"></ion-icon>
        </a>

        <h1>
            <a href="${pageContext.request.contextPath}/pages/esploraEventi.jsp">ESPLORA EVENTI</a>
        </h1>

        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=basket">
            <ion-icon name="basketball-outline"></ion-icon>
        </a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=bocce">
            <ion-icon name="bowling-ball-outline"></ion-icon>
        </a>

        <a href="${pageContext.request.contextPath}/pages/login.jsp">
            <ion-icon name="person-circle-outline"></ion-icon>
        </a>

    </nav>
</header>
