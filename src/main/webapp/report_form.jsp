<html>
<head>
<link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="container">

<h2>Employee Reports</h2>

<form action="report" method="post">

    <select name="type" required>
        <option value="">-- Select Report Type --</option>
        <option value="name">Name starts with</option>
        <option value="experience">Years of Service</option>
        <option value="salary">Salary greater than</option>
    </select>

    <input type="text" name="value" placeholder="Enter value" required>

    <button type="submit">Generate Report</button>

</form>

</div>

</body>
</html>