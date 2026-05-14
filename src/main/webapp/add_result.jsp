<%@ page import="com.employee.model.Employee" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee Added</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <p class="page-title">&#9989; Employee Added</p>

    <%
        Integer generatedId = (Integer) request.getAttribute("generatedId");
        Employee emp        = (Employee) request.getAttribute("employee");
    %>

    <div class="alert alert-success">
        Employee saved successfully! &nbsp;
        <strong>Generated Employee ID: <%= generatedId %></strong>
    </div>

    <% if (emp != null) { %>
    <div class="detail-card">
        <div class="detail-row">
            <span class="detail-label">Employee ID</span>
            <span class="detail-value"><strong><%= emp.getEmpno() %></strong></span>
        </div>
        <div class="detail-row">
            <span class="detail-label">Name</span>
            <span class="detail-value"><%= emp.getEmpName() %></span>
        </div>
        <div class="detail-row">
            <span class="detail-label">Role</span>
            <span class="detail-value"><%= emp.getRole() %></span>
        </div>
        <div class="detail-row">
            <span class="detail-label">Date of Joining</span>
            <span class="detail-value"><%= emp.getDoj() %></span>
        </div>
        <div class="detail-row">
            <span class="detail-label">Basic Salary</span>
            <span class="detail-value">&#8377;<%= String.format("%.2f", emp.getBsalary()) %></span>
        </div>
    </div>
    <% } %>

    <div class="nav-row">
        <a href="index.jsp"  class="btn btn-secondary">&#8592; Home</a>
        <a href="empadd.jsp" class="btn btn-primary">Add Another</a>
    </div>
</div>

</body>
</html>
