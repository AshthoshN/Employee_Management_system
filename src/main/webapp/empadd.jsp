<html>
<head>
<link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="container">

<h2>Add Employee</h2>

<form action="add" method="post">
    <input type="text" name="empno" placeholder="Employee Number" required>
    <input type="text" name="empname" placeholder="Employee Name" required>
    <input type="date" name="doj" required>
    <input type="text" name="gender" placeholder="Gender" required>
    <input type="text" name="salary" placeholder="Salary" required>

    <button type="submit">Add Employee</button>
</form>

</div>

</body>
</html>