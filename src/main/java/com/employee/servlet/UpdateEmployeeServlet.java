package com.employee.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import java.sql.Date;
import java.util.List;

@WebServlet("/update")
public class UpdateEmployeeServlet extends HttpServlet {

    /**
     * GET  → called when user submits the "look up ID" form in empupdate.jsp
     *         Fetches the employee and pre-fills the edit form.
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String empnoStr = req.getParameter("empno");
        if (empnoStr == null || empnoStr.trim().isEmpty()) {
            req.getRequestDispatcher("empupdate.jsp").forward(req, res);
            return;
        }

        try {
            int empno = Integer.parseInt(empnoStr.trim());
            EmployeeDAO dao = new EmployeeDAO();
            Employee e = dao.getEmployee(empno);

            if (e == null) {
                req.setAttribute("errorMsg", "No employee found with ID: " + empno);
                req.getRequestDispatcher("empupdate.jsp").forward(req, res);
            } else {
                req.setAttribute("employee", e);
                req.getRequestDispatcher("update_form.jsp").forward(req, res);
            }
        } catch (NumberFormatException nfe) {
            req.setAttribute("errorMsg", "Employee ID must be a valid number.");
            req.getRequestDispatcher("empupdate.jsp").forward(req, res);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("errorMsg", "Error: " + ex.getMessage());
            req.getRequestDispatcher("empupdate.jsp").forward(req, res);
        }
    }

    /**
     * POST → called when user submits the pre-filled edit form (update_form.jsp).
     *         Performs the update, then shows all employees with the updated row highlighted.
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            int empno = Integer.parseInt(req.getParameter("empno"));

            String name   = req.getParameter("empname") == null ? "" : req.getParameter("empname").trim();
            String role   = req.getParameter("role");
            String dojStr = req.getParameter("doj");
            String salStr = req.getParameter("salary");

            // Validation
            if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
                req.setAttribute("errorMsg", "Name must not be empty and must contain only alphabets.");
                EmployeeDAO dao2 = new EmployeeDAO();
                req.setAttribute("employee", dao2.getEmployee(empno));
                req.getRequestDispatcher("update_form.jsp").forward(req, res);
                return;
            }

            double salary;
            try {
                salary = Double.parseDouble(salStr);
            } catch (NumberFormatException nfe) {
                req.setAttribute("errorMsg", "Please enter a valid salary.");
                EmployeeDAO dao2 = new EmployeeDAO();
                req.setAttribute("employee", dao2.getEmployee(empno));
                req.getRequestDispatcher("update_form.jsp").forward(req, res);
                return;
            }

            if (salary <= 0) {
                req.setAttribute("errorMsg", "Salary must be greater than 0.");
                EmployeeDAO dao2 = new EmployeeDAO();
                req.setAttribute("employee", dao2.getEmployee(empno));
                req.getRequestDispatcher("update_form.jsp").forward(req, res);
                return;
            }

            Employee e = new Employee();
            e.setEmpno(empno);
            e.setEmpName(name);
            e.setRole(role);
            e.setDoj(Date.valueOf(dojStr));
            e.setBsalary(salary);

            EmployeeDAO dao = new EmployeeDAO();
            dao.updateEmployee(e);

            // Fetch all and pass updated ID for highlight
            List<Employee> all = dao.getAllEmployees();
            req.setAttribute("employees",   all);
            req.setAttribute("updatedId",   empno);

            req.getRequestDispatcher("update_result.jsp").forward(req, res);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("message", "Error: " + ex.getMessage());
            req.setAttribute("status",  "error");
            req.getRequestDispatcher("result.jsp").forward(req, res);
        }
    }
}
