package com.employee.dao;

import java.sql.*;
import java.util.*;
import com.employee.model.Employee;

public class EmployeeDAO {

    private String url  = "jdbc:mysql://localhost:3306/employee_db";
    private String user = "root";
    private String pass = "1234"; // change if needed

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }

    // ── Helper: map a ResultSet row → Employee ──────────────────────────────
    private Employee map(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setEmpno(rs.getInt("Empno"));
        e.setEmpName(rs.getString("EmpName"));
        e.setRole(rs.getString("Role"));
        e.setDoj(rs.getDate("DoJ"));
        e.setBsalary(rs.getDouble("Bsalary"));
        return e;
    }

    // ── NEXT AVAILABLE ID (gap-filling) ─────────────────────────────────────
    // Finds the lowest positive integer NOT already used as an Empno.
    // Examples:
    //   Existing: {2,3,4}   → returns 1   (gap at start)
    //   Existing: {1,3,4}   → returns 2   (gap in middle)
    //   Existing: {1,2,3}   → returns 4   (no gap, next after max)
    //   Existing: {}        → returns 1   (empty table)
    public int getNextEmployeeId() throws Exception {
        String sql =
            "SELECT MIN(t1.Empno + 1) AS next_id " +
            "FROM Employee t1 " +
            "WHERE NOT EXISTS (" +
            "    SELECT 1 FROM Employee t2 WHERE t2.Empno = t1.Empno + 1" +
            ")";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next() && rs.getObject("next_id") != null) {
                int candidate = rs.getInt("next_id");
                // candidate is (some_existing_id + 1); check if gap starts at 1
                // i.e. if Empno=1 doesn't exist, the answer is 1 not candidate
                return getLowestGap(con, candidate);
            }
            // Table is empty
            return 1;
        }
    }

    // Checks whether 1 is missing; if so returns 1, otherwise returns candidate.
    private int getLowestGap(Connection con, int candidate) throws Exception {
        String sql = "SELECT COUNT(*) FROM Employee WHERE Empno = 1";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) {
                return 1; // slot 1 is free
            }
        }
        return candidate;
    }

    // ── ADD EMPLOYEE (returns actual generated ID) ──────────────────────────
    // Since we are filling gaps, we INSERT with an explicit Empno.
    // We fetch the gap ID first, then insert with that ID.
    public int addEmployee(Employee e) throws Exception {
        // Step 1: get the gap ID inside the same connection for consistency
        int targetId;
        try (Connection con = getConnection()) {
            targetId = getNextEmployeeIdFromConnection(con);

            // Step 2: insert with that explicit ID
            String sql = "INSERT INTO Employee (Empno, EmpName, Role, DoJ, Bsalary) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, targetId);
                ps.setString(2, e.getEmpName());
                ps.setString(3, e.getRole());
                ps.setDate(4, e.getDoj());
                ps.setDouble(5, e.getBsalary());
                ps.executeUpdate();
            }
        }
        return targetId;
    }

    // Internal helper — gap-fill logic reused within an existing connection
    private int getNextEmployeeIdFromConnection(Connection con) throws Exception {
        // Check if slot 1 is free first
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT COUNT(*) FROM Employee WHERE Empno = 1");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) return 1;
        }

        // Find lowest gap: smallest (n+1) where (n+1) doesn't exist
        String sql =
            "SELECT MIN(t1.Empno + 1) AS next_id " +
            "FROM Employee t1 " +
            "WHERE NOT EXISTS (" +
            "    SELECT 1 FROM Employee t2 WHERE t2.Empno = t1.Empno + 1" +
            ")";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getObject("next_id") != null) {
                return rs.getInt("next_id");
            }
        }
        return 1; // fallback for empty table
    }

    // ── GET SINGLE EMPLOYEE ─────────────────────────────────────────────────
    public Employee getEmployee(int empno) throws Exception {
        String sql = "SELECT * FROM Employee WHERE Empno = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, empno);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    // ── GET ALL EMPLOYEES ───────────────────────────────────────────────────
    public List<Employee> getAllEmployees() throws Exception {
        String sql = "SELECT * FROM Employee ORDER BY Empno";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<Employee> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    // ── UPDATE ──────────────────────────────────────────────────────────────
    public void updateEmployee(Employee e) throws Exception {
        String sql = "UPDATE Employee SET EmpName=?, Role=?, DoJ=?, Bsalary=? WHERE Empno=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getEmpName());
            ps.setString(2, e.getRole());
            ps.setDate(3, e.getDoj());
            ps.setDouble(4, e.getBsalary());
            ps.setInt(5, e.getEmpno());
            ps.executeUpdate();
        }
    }

    // ── DELETE ──────────────────────────────────────────────────────────────
    public void deleteEmployee(int empno) throws Exception {
        String sql = "DELETE FROM Employee WHERE Empno=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, empno);
            ps.executeUpdate();
        }
    }

    // ── REPORT: name starts with letter ────────────────────────────────────
    public List<Employee> getReportByName(String letter) throws Exception {
        String sql = "SELECT * FROM Employee WHERE EmpName LIKE ? ORDER BY EmpName";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, letter + "%");
            ResultSet rs = ps.executeQuery();
            List<Employee> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    // ── REPORT: experience range (years) ───────────────────────────────────
    public List<Employee> getReportByExperience(int fromYears, int toYears) throws Exception {
        String sql = "SELECT * FROM Employee " +
                     "WHERE TIMESTAMPDIFF(YEAR, DoJ, CURDATE()) BETWEEN ? AND ? " +
                     "ORDER BY DoJ";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, fromYears);
            ps.setInt(2, toYears);
            ResultSet rs = ps.executeQuery();
            List<Employee> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    // ── REPORT: salary range ────────────────────────────────────────────────
    public List<Employee> getReportBySalary(double minSalary, double maxSalary) throws Exception {
        String sql;
        PreparedStatement ps;
        Connection con = getConnection();

        if (minSalary >= 0 && maxSalary >= 0) {
            sql = "SELECT * FROM Employee WHERE Bsalary BETWEEN ? AND ? ORDER BY Bsalary";
            ps = con.prepareStatement(sql);
            ps.setDouble(1, minSalary);
            ps.setDouble(2, maxSalary);
        } else if (minSalary >= 0) {
            sql = "SELECT * FROM Employee WHERE Bsalary >= ? ORDER BY Bsalary";
            ps = con.prepareStatement(sql);
            ps.setDouble(1, minSalary);
        } else {
            sql = "SELECT * FROM Employee WHERE Bsalary <= ? ORDER BY Bsalary";
            ps = con.prepareStatement(sql);
            ps.setDouble(1, maxSalary);
        }

        ResultSet rs = ps.executeQuery();
        List<Employee> list = new ArrayList<>();
        while (rs.next()) list.add(map(rs));
        con.close();
        return list;
    }
}
