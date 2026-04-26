package com.employee.dao;

import java.sql.*;
import com.employee.model.Employee;
import java.util.*;

public class EmployeeDAO {

    private String url = "jdbc:mysql://localhost:3306/employee_db";
    private String user = "root";
    private String pass = "1234"; // change if needed

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }

    public void addEmployee(Employee e) throws Exception {
        Connection con = getConnection();

        String query = "INSERT INTO Employee VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, e.getEmpno());
        ps.setString(2, e.getEmpName());
        ps.setDate(3, e.getDoj());
        ps.setString(4, e.getGender());
        ps.setDouble(5, e.getBsalary());

        ps.executeUpdate();
        con.close();
    }
    public void updateEmployee(Employee e) throws Exception {
        Connection con = getConnection();

        String query = "UPDATE Employee SET EmpName=?, DoJ=?, Gender=?, Bsalary=? WHERE Empno=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, e.getEmpName());
        ps.setDate(2, e.getDoj());
        ps.setString(3, e.getGender());
        ps.setDouble(4, e.getBsalary());
        ps.setInt(5, e.getEmpno());

        int rows = ps.executeUpdate();

        if (rows == 0) {
            throw new Exception("Employee not found!");
        }

        con.close();
    }
    public void deleteEmployee(int empno) throws Exception {
        Connection con = getConnection();

        String query = "DELETE FROM Employee WHERE Empno=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, empno);

        int rows = ps.executeUpdate();

        if (rows == 0) {
            throw new Exception("Employee not found!");
        }

        con.close();
    }
    public Employee getEmployee(int empno) throws Exception {
        Connection con = getConnection();

        String query = "SELECT * FROM Employee WHERE Empno=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, empno);

        ResultSet rs = ps.executeQuery();

        Employee e = null;

        if (rs.next()) {
            e = new Employee();
            e.setEmpno(rs.getInt(1));
            e.setEmpName(rs.getString(2));
            e.setDoj(rs.getDate(3));
            e.setGender(rs.getString(4));
            e.setBsalary(rs.getDouble(5));
        }

        con.close();
        return e;
    }

    public List<Employee> getReport(String type, String value) throws Exception {

        Connection con = getConnection();
        PreparedStatement ps = null;

        if (type.equals("name")) {
            ps = con.prepareStatement("SELECT * FROM Employee WHERE EmpName LIKE ?");
            ps.setString(1, value + "%");
        } 
        else if (type.equals("experience")) {
            ps = con.prepareStatement(
                "SELECT * FROM Employee WHERE TIMESTAMPDIFF(YEAR, DoJ, CURDATE()) >= ?"
            );
            ps.setInt(1, Integer.parseInt(value));
        } 
        else if (type.equals("salary")) {
            ps = con.prepareStatement("SELECT * FROM Employee WHERE Bsalary > ?");
            ps.setDouble(1, Double.parseDouble(value));
        }

        ResultSet rs = ps.executeQuery();

        List<Employee> list = new ArrayList<>();

        while (rs.next()) {
            Employee e = new Employee();
            e.setEmpno(rs.getInt(1));
            e.setEmpName(rs.getString(2));
            e.setDoj(rs.getDate(3));
            e.setGender(rs.getString(4));
            e.setBsalary(rs.getDouble(5));

            list.add(e);
        }

        con.close();
        return list;
    }
}