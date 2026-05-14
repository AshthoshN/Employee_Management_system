<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.employee.model.Employee, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Successful</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container wide">
    <p class="page-title">&#9989; Employee Updated</p>

    <%
        Integer updatedId       = (Integer) request.getAttribute("updatedId");
        List<Employee> employees = (List<Employee>) request.getAttribute("employees");
    %>

    <div class="alert alert-success">
        Employee ID <strong><%= updatedId %></strong> has been updated successfully.
        The highlighted row shows the updated record.
    </div>

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
                <% if (employees == null || employees.isEmpty()) { %>
                <tr><td colspan="6" style="text-align:center;color:#888;">No employees found.</td></tr>
                <% } else {
                    java.time.LocalDate today = java.time.LocalDate.now();
                    for (Employee e : employees) {
                        int exp = today.getYear() - e.getDoj().toLocalDate().getYear();
                        boolean isUpdated = (updatedId != null && e.getEmpno() == updatedId);
                %>
                <tr class="<%= isUpdated ? "highlighted" : "" %>">
                    <td><%= e.getEmpno() %></td>
                    <td><%= e.getEmpName() %></td>
                    <td><%= e.getRole() %></td>
                    <td><%= e.getDoj() %></td>
                    <td><%= exp %></td>
                    <td>&#8377;<%= String.format("%.2f", e.getBsalary()) %></td>
                </tr>
                <% } } %>
            </tbody>
        </table>
    </div>

    <div class="nav-row">
        <a href="index.jsp"    class="btn btn-secondary">&#8592; Home</a>
        <a href="empupdate.jsp" class="btn btn-primary">Update Another</a>
    </div>
</div>

</body>
</html>
