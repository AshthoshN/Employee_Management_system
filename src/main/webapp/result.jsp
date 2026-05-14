<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Result</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <p class="page-title">Result</p>

    <%
        String message = (String) request.getAttribute("message");
        String status  = (String) request.getAttribute("status");
    %>

    <% if ("success".equals(status)) { %>
        <div class="alert alert-success"><%= message %></div>
    <% } else { %>
        <div class="alert alert-error"><%= message %></div>
    <% } %>

    <div class="nav-row">
        <a href="index.jsp"  class="btn btn-secondary">&#8592; Home</a>
        <a href="empadd.jsp" class="btn btn-primary">Add Employee</a>
    </div>
</div>

</body>
</html>
