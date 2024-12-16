<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.utente.UtenteBean" %>
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
        <!-- Logo -->
        <a href="${pageContext.request.contextPath}/pages/index.jsp">
            <img src="${pageContext.request.contextPath}/IMG/logo_WePlay.png" alt="WePlay Logo">
        </a>
        <!-- Icone Sport -->
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=calcio">
            <ion-icon name="football-outline"></ion-icon>
        </a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=tennis">
            <ion-icon name="tennisball-outline"></ion-icon>
        </a>
        <a href="${pageContext.request.contextPath}/pages/esploraEventi.jsp">
            <h1>ESPLORA EVENTI</h1>
        </a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=basket">
            <ion-icon name="basketball-outline"></ion-icon>
        </a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=bocce">
            <ion-icon name="bowling-ball-outline"></ion-icon>
        </a>
        
        <!-- Icona Utente -->
        <div class="dropdown">
            <a href="#" id="accountLink" class="user-icon">
                <ion-icon name="person-circle-outline"></ion-icon>
            </a>
            <% if (isLoggedIn && !isAdmin) { %>
                <!-- Menu a tendina per utente loggato -->
                <div class="dropdown-menu">
                    <a href="${pageContext.request.contextPath}/ProfiloServlet">Profilo</a>
                    <a href="${pageContext.request.contextPath}/EsploraEventiServlet?attributo=sottoscritto">Eventi sottoscritti</a>
                    <a href="${pageContext.request.contextPath}/EsploraEventiServlet?attributo=organizzatore">Eventi creati</a>
                    <a href="${pageContext.request.contextPath}/logout">Logout</a>
                </div>
            <% } else if(isLoggedIn && isAdmin){ %>
            <script>
                    document.getElementById("accountLink").setAttribute("href", "${pageContext.request.contextPath}/pages/adminDashboard.jsp");
                </script>
            <% } else{ %>
                <!-- Se non loggato, reindirizza alla pagina di login -->
                <script>
                    document.getElementById("accountLink").setAttribute("href", "${pageContext.request.contextPath}/pages/login.jsp");
                </script>
            <% } %>
        </div>
    </nav>
</header>
