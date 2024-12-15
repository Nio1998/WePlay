<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="model.utente.UtenteBean, model.utente.UtenteDAO, model.evento.Evento, model.evento.EventoDao, java.sql.SQLException, java.util.*"%>

<%
    // Verifica della sessione
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    if (utente == null || utente.isAdmin() != true) {
        response.sendRedirect(request.getContextPath() + "/pages/AccessoVietato.jsp");
        return;
    }

    EventoDao eventoDao = new EventoDao();
    List<Evento> eventi = eventoDao.getAll();
    int numeroEventi = eventi.size();
    
    UtenteDAO utenteDAO = new UtenteDAO();
    List<UtenteBean> utenti = utenteDAO.getAllUtenti();
    int numeroUtenti = utenti.size();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/adminDashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>

    <div class="dashboard">
    	<a href="<%= request.getContextPath() %>/Admin_getAllUser" class="dashboard-item">
            <div class="icon user">
                <i class="fas fa-users"></i>
            </div>
            <div class="info">
                <h3>Utenti</h3>
                <p><%= numeroUtenti %></p>
            </div>
        </a>
        <a href="<%= request.getContextPath() %>/Admin_getAllEvent" class="dashboard-item">
            <div class="icon lista">
                <i class="fas fa-clipboard-list"></i>
            </div>
            <div class="info">
                <h3>Lista Eventi</h3>
                <p><%= numeroEventi %></p>
            </div>
        </a>
        <a href="<%= request.getContextPath() %>/ProfiloServlet" class="dashboard-item">
            <div class="icon user">
                <i class="fa-solid fa-user-pen"></i>
            </div>
            <div class="info">
                <h3>Vedi o modifica account</h3>
            </div>
        </a>
        <a href="<%= request.getContextPath() %>/pages/index.jsp" class="dashboard-item">
            <div class="icon ritorno">
               <i class="fa-solid fa-house"></i>
            </div>
            <div class="info">
                <h3>Torna alla home</h3>
            </div>
        </a>
        <form action="<%= request.getContextPath() %>/logout" method="post" class="dashboard-item logout-form">
            <div class="icon ritorno" onclick="document.querySelector('.logout-form').submit();">
               <i class="fa-solid fa-right-from-bracket"></i>
            </div>
            <div class="info">
                <h3>Logout</h3>
            </div>
        </form>
    </div>
</body>
</html>
