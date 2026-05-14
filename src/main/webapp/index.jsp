<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee Management System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <h1 class="site-title">&#128188; EMS</h1>
    <p class="page-subtitle">Employee Management System &mdash; manage your workforce with ease</p>

    <div class="menu-grid">
        <%-- Link to /addEmployee servlet so the next ID is pre-fetched --%>
        <a href="addEmployee" class="menu-card">
            <span class="icon">&#10133;</span>
            Add Employee
        </a>
        <a href="empupdate.jsp" class="menu-card">
            <span class="icon">&#9999;&#65039;</span>
            Update Employee
        </a>
        <a href="empdelete.jsp" class="menu-card">
            <span class="icon">&#128465;&#65039;</span>
            Delete Employee
        </a>
        <a href="display" class="menu-card">
            <span class="icon">&#128101;</span>
            Display All
        </a>
        <a href="report_form.jsp" class="menu-card" style="grid-column: span 2;">
            <span class="icon">&#128202;</span>
            Reports
        </a>
    </div>
</div>

</body>
</html></html>