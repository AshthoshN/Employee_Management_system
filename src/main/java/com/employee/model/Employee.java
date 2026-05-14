package com.employee.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import java.sql.Date;

@WebServlet("/add")
public class AddEmployeeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            String name   = req.getParameter("empname") == null ? "" : req.getParameter("empname").trim();
            String role   = req.getParameter("role");
            String dojStr = req.getParameter("doj");
            String salStr = req.getParameter("salary");

            // ── Validation ──────────────────────────────────────────────────
            if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
                req.setAttribute("message", "Name must not be empty and must contain only alphabets.");
                req.setAttribute("status", "error");
                req.getRequestDispatcher("result.jsp").forward(req, res);
                return;
            }

            double salary;
            try {
                salary = Double.parseDouble(salStr);
            } catch (NumberFormatException nfe) {
                req.setAttribute("message", "Please enter a valid salary.");
                req.setAttribute("status", "error");
                req.getRequestDispatcher("result.jsp").forward(req, res);
                return;
            }

            if (salary <= 0) {
                req.setAttribute("message", "Salary must be greater than 0.");
                req.setAttribute("status", "error");
                req.getRequestDispatcher("result.jsp").forward(req, res);
                return;
            }

            // ── Build object and save ────────────────────────────────────────
            Employee e = new Employee();
            e.setEmpName(name);
            e.setRole(role);
            e.setDoj(Date.valueOf(dojStr));
            e.setBsalary(salary);

            EmployeeDAO dao = new EmployeeDAO();
            int generatedId = dao.addEmployee(e);

            // Fetch the saved record to display it
            Employee saved = dao.getEmployee(generatedId);

            req.setAttribute("status",      "success");
            req.setAttribute("generatedId", generatedId);
            req.setAttribute("employee",    saved);

            req.getRequestDispatcher("add_result.jsp").forward(req, res);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("message", "Unexpected error: " + ex.getMessage());
            req.setAttribute("status",  "error");
            req.getRequestDispatcher("result.jsp").forward(req, res);
        }
    }
}
