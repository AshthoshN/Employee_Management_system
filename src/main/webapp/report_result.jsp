<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.employee.model.Employee, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Report Results</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container wide">
    <p class="page-title">&#128202; Report Results</p>

    <%
        List<Employee> employees = (List<Employee>) request.getAttribute("employees");
        String filterDesc        = (String) request.getAttribute("filterDesc");
    %>

    <% if (filterDesc != null && !filterDesc.isEmpty()) { %>
        <div class="alert alert-info">Filter: <%= filterDesc %></div>
    <% } %>

    <% if (employees == null || employees.isEmpty()) { %>
        <div class="alert alert-error">No employees found matching the selected filter.</div>
    <% } else { %>
        <p class="page-subtitle">Found <strong><%= employees.size() %></strong> record(s)</p>
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Role</th>
                        <th>Date of Joining</th>
                        <th>Experience (yrs)</th>
                        <th>Basic Salary (&#8377;)</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        java.time.LocalDate today = java.time.LocalDate.now();
                        for (Employee e : employees) {
                            int exp = today.getYear() - e.getDoj().toLocalDate().getYear();
                    %>
                    <tr>
                        <td><%= e.getEmpno() %></td>
                        <td><%= e.getEmpName() %></td>
                        <td><%= e.getRole() %></td>
                        <td><%= e.getDoj() %></td>
                        <td><%= exp %></td>
                        <td>&#8377;<%= String.format("%.2f", e.getBsalary()) %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    <% } %>

    <div class="nav-row">
        <a href="report_form.jsp" class="btn btn-secondary">&#8592; Back to Reports</a>
        <a href="index.jsp"       class="btn btn-secondary">&#8962; Home</a>
    </div>
</div>

</body>
</html>
