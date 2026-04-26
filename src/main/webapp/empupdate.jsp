<html>
<head>
<link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="container">

<h2>Update Employee</h2>

<form action="update" method="post">
    <input type="text" name="empno" placeholder="Employee Number" required>
    <input type="text" name="empname" placeholder="New Name" required>
    <input type="date" name="doj" required>
    <input type="text" name="gender" placeholder="Gender" required>
    <input type="text" name="salary" placeholder="New Salary" required>

    <button type="submit">Update Employee</button>
</form>

</div>

</body>
</html>