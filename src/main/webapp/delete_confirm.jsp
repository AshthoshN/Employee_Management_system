<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.employee.model.Employee" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirm Delete</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <p class="page-title">&#9888;&#65039; Confirm Deletion</p>

    <%
        Employee emp = (Employee) request.getAttribute("employee");
    %>

    <div class="alert alert-error">
        You are about to permanently delete the following employee. This action cannot be undone.
    </div>

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

    <!-- Yes → POST to /delete -->
    <form action="delete" method="post" style="margin-top: 22px;">
        <input type="hidden" name="empno" value="<%= emp.getEmpno() %>">
        <div class="nav-row">
            <button type="submit" class="btn btn-danger">&#10003; Yes, Delete</button>
            <a href="empdelete.jsp" class="btn btn-secondary">&#10005; No, Cancel</a>
            <a href="index.jsp"     class="btn btn-secondary">&#8962; Home</a>
        </div>
    </form>
</div>

</body>
</html>
