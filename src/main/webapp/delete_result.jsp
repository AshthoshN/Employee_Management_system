<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee Deleted</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <p class="page-title">&#128465;&#65039; Deletion Complete</p>

    <%
        Integer deletedId = (Integer) request.getAttribute("deletedId");
    %>

    <div class="alert alert-success">
        Employee <strong><%= deletedId %></strong> has been deleted successfully.
    </div>

    <div class="nav-row">
        <a href="index.jsp"    class="btn btn-secondary">&#8592; Home</a>
        <a href="empdelete.jsp" class="btn btn-danger">Delete Another</a>
        <a href="display"       class="btn btn-primary">View All Employees</a>
    </div>
</div>

</body>
</html>
