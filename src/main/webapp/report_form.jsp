<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee Reports</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .tab-row { display: flex; gap: 10px; margin-bottom: 24px; flex-wrap: wrap; }
        .tab-btn {
            padding: 8px 18px; border: 1.5px solid #d0d7e2;
            border-radius: 20px; background: #f5f7fb; cursor: pointer;
            font-size: 0.88rem; font-weight: 600; color: #555;
            transition: all .2s;
        }
        .tab-btn.active { background: #3f51b5; color: #fff; border-color: #3f51b5; }
        .report-panel { display: none; }
        .report-panel.active { display: block; }
    </style>
</head>
<body>

<div class="container">
    <p class="page-title">&#128202; Employee Reports</p>
    <p class="page-subtitle">Choose a filter type and generate a report.</p>

    <%
        String errorMsg = (String) request.getAttribute("errorMsg");
        if (errorMsg != null) {
    %>
        <div class="alert alert-error"><%= errorMsg %></div>
    <% } %>

    <div class="tab-row">
        <button class="tab-btn active" onclick="showTab('name',this)">&#128100; By Name</button>
        <button class="tab-btn"        onclick="showTab('exp',this)">&#128197; By Experience</button>
        <button class="tab-btn"        onclick="showTab('salary',this)">&#128176; By Salary</button>
    </div>

    <!-- NAME FILTER -->
    <div id="tab-name" class="report-panel active">
        <form action="report" method="post">
            <input type="hidden" name="type" value="name">
            <div class="form-group">
                <label for="nameLetter">Name starts with (letter)</label>
                <input type="text" id="nameLetter" name="nameLetter"
                       maxlength="1" placeholder="e.g. A" required>
            </div>
            <button type="submit" class="btn btn-primary btn-full">Generate Report</button>
        </form>
    </div>

    <!-- EXPERIENCE FILTER -->
    <div id="tab-exp" class="report-panel">
        <form action="report" method="post">
            <input type="hidden" name="type" value="experience">
            <div class="form-group">
                <label for="expFrom">From (years of experience)</label>
                <input type="number" id="expFrom" name="expFrom"
                       placeholder="e.g. 2" min="0" required>
            </div>
            <div class="form-group">
                <label for="expTo">To (years of experience)</label>
                <input type="number" id="expTo" name="expTo"
                       placeholder="e.g. 10" min="0" required>
            </div>
            <button type="submit" class="btn btn-primary btn-full">Generate Report</button>
        </form>
    </div>

    <!-- SALARY FILTER -->
    <div id="tab-salary" class="report-panel">
        <form action="report" method="post">
            <input type="hidden" name="type" value="salary">
            <div class="form-group">
                <label for="salMin">Minimum Salary (&#8377;) &mdash; optional</label>
                <input type="number" id="salMin" name="salMin"
                       placeholder="e.g. 30000" min="0" step="0.01">
            </div>
            <div class="form-group">
                <label for="salMax">Maximum Salary (&#8377;) &mdash; optional</label>
                <input type="number" id="salMax" name="salMax"
                       placeholder="e.g. 100000" min="0" step="0.01">
            </div>
            <p style="font-size:.82rem;color:#888;margin-bottom:12px;">
                Leave one field blank to filter with only the other bound.
            </p>
            <button type="submit" class="btn btn-primary btn-full">Generate Report</button>
        </form>
    </div>

    <div class="nav-row">
        <a href="index.jsp" class="btn btn-secondary">&#8592; Home</a>
    </div>
</div>

<script>
function showTab(name, btn) {
    document.querySelectorAll('.report-panel').forEach(p => p.classList.remove('active'));
    document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
    document.getElementById('tab-' + name).classList.add('active');
    btn.classList.add('active');
}
</script>

</body>
</html>
