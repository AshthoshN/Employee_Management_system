package com.employee.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;

@WebServlet("/delete")
public class DeleteEmployeeServlet extends HttpServlet {

    /**
     * GET → look up the employee by ID, show their data and ask Yes / No.
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String empnoStr = req.getParameter("empno");
        if (empnoStr == null || empnoStr.trim().isEmpty()) {
            req.getRequestDispatcher("empdelete.jsp").forward(req, res);
            return;
        }

        try {
            int empno = Integer.parseInt(empnoStr.trim());
            EmployeeDAO dao = new EmployeeDAO();
            Employee e = dao.getEmployee(empno);

            if (e == null) {
                req.setAttribute("errorMsg", "No employee found with ID: " + empno);
                req.getRequestDispatcher("empdelete.jsp").forward(req, res);
            } else {
                req.setAttribute("employee", e);
                req.getRequestDispatcher("delete_confirm.jsp").forward(req, res);
            }
        } catch (NumberFormatException nfe) {
            req.setAttribute("errorMsg", "Employee ID must be a valid number.");
            req.getRequestDispatcher("empdelete.jsp").forward(req, res);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("errorMsg", "Error: " + ex.getMessage());
            req.getRequestDispatcher("empdelete.jsp").forward(req, res);
        }
    }

    /**
     * POST → user confirmed deletion.
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            int empno = Integer.parseInt(req.getParameter("empno"));
            EmployeeDAO dao = new EmployeeDAO();
            dao.deleteEmployee(empno);

            req.setAttribute("deletedId", empno);
            req.getRequestDispatcher("delete_result.jsp").forward(req, res);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("message", "Error deleting employee: " + ex.getMessage());
            req.setAttribute("status",  "error");
            req.getRequestDispatcher("result.jsp").forward(req, res);
        }
    }
}
