<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/navbar.css">

<head>

	<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

</head>
<header class="header">
		<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/pages/index.jsp"><img src="${pageContext.request.contextPath}/IMG/logo_WePlay.png" alt="WePlay Logo"></a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=calcio"><ion-icon name="football-outline"></ion-icon></a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=tennis"><ion-icon name="tennisball-outline"></ion-icon></a>
        <a href="esploraEventi.jsp"><h1>ESPLORA EVENTI</h1></a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=basket"><ion-icon name="basketball-outline"></ion-icon></a>
        <a href="${pageContext.request.contextPath}/pages/paginaEventi.jsp?sport=bocce"><ion-icon name="bowling-ball-outline"></ion-icon></a>
        <a href="login.jsp"><ion-icon name="person-circle-outline"></ion-icon></a>
    </nav>
</header>
