<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.employee.model.Employee" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Employee</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <p class="page-title">&#9999;&#65039; Edit Employee</p>

    <%
        Employee emp = (Employee) request.getAttribute("employee");
        String errorMsg = (String) request.getAttribute("errorMsg");
        if (errorMsg != null) {
    %>
        <div class="alert alert-error"><%= errorMsg %></div>
    <% } %>

    <div class="alert alert-info">
        Editing Employee ID: <strong><%= emp.getEmpno() %></strong>
    </div>

    <form action="update" method="post" onsubmit="return validateForm()">
        <!-- Hidden field to carry the ID -->
        <input type="hidden" name="empno" value="<%= emp.getEmpno() %>">

        <div class="form-group">
            <label for="empname">Full Name</label>
            <input type="text" id="empname" name="empname"
                   value="<%= emp.getEmpName() %>" required>
        </div>

        <div class="form-group">
            <label for="role">Role</label>
            <select id="role" name="role" required>
                <% String[] roles = {"CEO","Manager","Lead","Developer","Intern"};
                   for (String r : roles) { %>
                <option value="<%= r %>" <%= r.equals(emp.getRole()) ? "selected" : "" %>><%= r %></option>
                <% } %>
            </select>
        </div>

        <div class="form-group">
            <label for="doj">Date of Joining</label>
            <input type="date" id="doj" name="doj"
                   value="<%= emp.getDoj() %>" required>
        </div>

        <div class="form-group">
            <label for="salary">Basic Salary (&#8377;)</label>
            <input type="number" id="salary" name="salary"
                   value="<%= emp.getBsalary() %>" min="1" step="0.01" required>
        </div>

        <div id="clientError" class="alert alert-error" style="display:none;"></div>

        <button type="submit" class="btn btn-primary btn-full">Save Changes</button>
    </form>

    <div class="nav-row">
        <a href="empupdate.jsp" class="btn btn-secondary">&#8592; Back</a>
        <a href="index.jsp"     class="btn btn-secondary">&#8962; Home</a>
    </div>
</div>

<script>
function validateForm() {
    const name   = document.getElementById('empname').value.trim();
    const salary = parseFloat(document.getElementById('salary').value);
    const errDiv = document.getElementById('clientError');

    if (!/^[a-zA-Z ]+$/.test(name)) {
        errDiv.textContent = 'Name must contain only alphabets and spaces.';
        errDiv.style.display = 'block';
        return false;
    }
    if (isNaN(salary) || salary <= 0) {
        errDiv.textContent = 'Salary must be a positive number greater than 0.';
        errDiv.style.display = 'block';
        return false;
    }
    errDiv.style.display = 'none';
    return true;
}
</script>

</body>
</html>
