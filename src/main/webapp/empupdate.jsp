<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Employee</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <p class="page-title">&#9999;&#65039; Update Employee</p>
    <p class="page-subtitle">Enter the Employee ID to look up and edit their details.</p>

    <%
        String errorMsg = (String) request.getAttribute("errorMsg");
        if (errorMsg != null) {
    %>
        <div class="alert alert-error"><%= errorMsg %></div>
    <% } %>

    <form action="update" method="get">
        <div class="form-group">
            <label for="empno">Employee ID</label>
            <input type="number" id="empno" name="empno" placeholder="e.g. 101" required min="1">
        </div>
        <button type="submit" class="btn btn-primary btn-full">Look Up Employee</button>
    </form>

    <div class="nav-row">
        <a href="index.jsp" class="btn btn-secondary">&#8592; Home</a>
    </div>
</div>

</body>
</html>
