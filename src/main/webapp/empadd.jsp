<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Employee</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        /* Readonly / preview field styling */
        .id-preview-field {
            width: 100%;
            padding: 10px 12px;
            border: 1.5px dashed #3f51b5;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 700;
            color: #1a237e;
            background: #e8eaf6;
            cursor: not-allowed;
            letter-spacing: 0.03em;
        }

        .id-preview-field.error {
            border-color: #e53935;
            color: #b71c1c;
            background: #ffebee;
        }

        .id-badge {
            display: inline-block;
            background: #3f51b5;
            color: #fff;
            font-size: 0.72rem;
            font-weight: 700;
            padding: 2px 8px;
            border-radius: 10px;
            margin-left: 8px;
            vertical-align: middle;
            letter-spacing: 0.05em;
            text-transform: uppercase;
        }
    </style>
</head>
<body>

<div class="container">
    <p class="page-title">&#10133; Add Employee</p>

    <%
        Integer nextEmpId = (Integer) request.getAttribute("nextEmpId");
        String  idError   = (String)  request.getAttribute("idError");
        boolean hasIdError = (idError != null);
    %>

    <%-- Show a warning if the page was opened directly (bypassing the servlet) --%>
    <% if (nextEmpId == null) { %>
        <div class="alert alert-error">
            Please open this page via the home menu so the Employee ID can be pre-loaded.
            <a href="addEmployee" style="font-weight:600;">Click here to reload.</a>
        </div>
    <% } %>

    <%-- Auto-generated ID preview field --%>
    <div class="form-group">
        <label for="previewId">
            Employee ID (Auto Generated)
            <span class="id-badge">read only</span>
        </label>

        <% if (hasIdError) { %>
            <input type="text"
                   id="previewId"
                   class="id-preview-field error"
                   value="Could not fetch ID — check DB connection"
                   readonly>
            <small style="color:#b71c1c;"><%= idError %></small>
        <% } else if (nextEmpId != null) { %>
            <input type="text"
                   id="previewId"
                   class="id-preview-field"
                   value="<%= nextEmpId %>"
                   readonly>
        <% } else { %>
            <input type="text"
                   id="previewId"
                   class="id-preview-field error"
                   value="—"
                   readonly>
        <% } %>
    </div>

    <hr class="divider">

    <%-- Main add form — note: previewId field is NOT inside this form --%>
    <form action="add" method="post" id="addForm" onsubmit="return validateForm()">

        <div class="form-group">
            <label for="empname">Full Name</label>
            <input type="text" id="empname" name="empname"
                   placeholder="e.g. Arjun Kumar" required>
        </div>

        <div class="form-group">
            <label for="role">Role</label>
            <select id="role" name="role" required>
                <option value="" disabled selected>-- Select Role --</option>
                <option value="CEO">CEO</option>
                <option value="Manager">Manager</option>
                <option value="Lead">Lead</option>
                <option value="Developer">Developer</option>
                <option value="Intern">Intern</option>
            </select>
        </div>

        <div class="form-group">
            <label for="doj">Date of Joining</label>
            <input type="date" id="doj" name="doj" required>
        </div>

        <div class="form-group">
            <label for="salary">Basic Salary (&#8377;)</label>
            <input type="number" id="salary" name="salary"
                   placeholder="e.g. 50000" min="1" step="0.01" required>
        </div>

        <div id="clientError" class="alert alert-error" style="display:none;"></div>

        <button type="submit" class="btn btn-primary btn-full">Save Employee</button>
    </form>

    <div class="nav-row">
        <a href="index.jsp" class="btn btn-secondary">&#8592; Home</a>
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
