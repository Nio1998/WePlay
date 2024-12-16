<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.utente.UtenteBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestione Utenti</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/ListaUtenti.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<script type="text/javascript">
    // Mostra un alert con il messaggio passato
    function showAlert(message) {
        alert(message);
    }
</script>
</head>
<body>
    <div class="container">
        <a href="${pageContext.request.contextPath}/pages/adminDashboard.jsp">
            <button class="dashboard-button">Torna alla Dashboard</button>
        </a>
        <h1>Lista Utenti</h1>

        <%-- Verifica se c'è un messaggio di successo e mostra l'alert --%>
        <%
        String successMessage = (String) request.getAttribute("successMessage");
        if (successMessage != null) {
        %>
        <script type="text/javascript">
            showAlert("<%= successMessage %>");
        </script>
        <%
        }
        %>

        <%-- Verifica se c'è un messaggio di fallimento e mostra l'alert --%>
        <%
        String failureMessage = (String) request.getAttribute("failureMessage");
        if (failureMessage != null) {
        %>
        <script type="text/javascript">
            showAlert("<%= failureMessage %>");
        </script>
        <%
        }
        %>

        <%-- Verifica se c'è un messaggio di timeout e mostra l'alert --%>
        <%
        String timeoutMessage = (String) request.getAttribute("timeoutMessage");
        if (timeoutMessage != null) {
        %>
        <script type="text/javascript">
            showAlert("<%= timeoutMessage %>");
        </script>
        <%
        }
        %>

        <table>
            <thead>
                <tr>
                    <th></th>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Nome</th>
                    <th>Cognome</th>
                    <th>Data di Nascita</th>
                    <th>Numero Timeout</th>
                    <th>In Timeout</th>
                    <th>Valutazioni Positive</th>
                    <th>Valutazioni Neutre</th>
                    <th>Valutazioni Negative</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<UtenteBean> utenti = (List<UtenteBean>) request.getAttribute("utenti");
                if (utenti != null && !utenti.isEmpty()) {
                    int id = 1;
                    for (UtenteBean utente : utenti) {
                %>
                <tr>
                    <td>
                        <form action="${pageContext.request.contextPath}/ProfiloServlet" method="post" style="display: inline;">
                            <input type="hidden" name="username" value="<%= utente.getUsername() %>">
                            <button type="submit" class="btn btn-info">Visualizza Profilo</button>
                        </form>
                    </td>
                    <td><%= id++ %></td>
                    <td><%= utente.getUsername() %></td>
                    <td><%= utente.getNome() %></td>
                    <td><%= utente.getCognome() %></td>
                    <td><%= utente.getDataDiNascita() %></td>
                    <td><%= utente.getNumTimeout() %></td>
                    <td><%= utente.isTimeout() ? "Sì" : "No" %></td>
                    <td><%= utente.getNumValutazioniPositive() %></td>
                    <td><%= utente.getNumValutazioniNeutre() %></td>
                    <td><%= utente.getNumValutazioniNegative() %></td>
                    <td>
                        <div class="action-buttons">
                            <form action="${pageContext.request.contextPath}/ApplicaTimeout" method="post" style="display: inline;">
                                <input type="hidden" name="username" value="<%= utente.getUsername() %>">
                                <button type="submit" class="modifica">Timeout</button>
                            </form>

                            <form action="${pageContext.request.contextPath}/ApplicaBan" method="post" style="display: inline;">
                                <input type="hidden" name="username" value="<%= utente.getUsername() %>">
                                <button type="submit" class="elimina">Ban</button>
                            </form>
                        </div>
                    </td>
                </tr>
                <% 
                    }
                } else {
                %>
                <tr>
                    <td colspan="12">Nessun utente trovato.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
