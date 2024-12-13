<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/navbar.css">

<head>

	<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

</head>
<header class="header">
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/pages/index.jsp"><img src="${pageContext.request.contextPath}/IMG/logo_WePlay.png" alt="WePlay Logo"></a>
        <a href="calcio.jsp"><ion-icon name="football-outline"></ion-icon></a>
        <a href="tennis.jsp"><ion-icon name="tennisball-outline"></ion-icon></a>
        <a href="esploraEventi.jsp"><h1>ESPLORA EVENTI</h1></a>
        <a href="basket.jsp"><ion-icon name="basketball-outline"></ion-icon></a>
        <a href="bocce.jsp"><ion-icon name="bowling-ball-outline"></ion-icon></a>
        <a href="login.jsp"><ion-icon name="person-circle-outline"></ion-icon></a>
    </nav>
</header>
