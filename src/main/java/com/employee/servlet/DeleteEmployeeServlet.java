package com.employee.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.employee.dao.EmployeeDAO;

@WebServlet("/delete")
public class DeleteEmployeeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");

        try {
            int empno = Integer.parseInt(req.getParameter("empno"));

            EmployeeDAO dao = new EmployeeDAO();
            dao.deleteEmployee(empno);

            res.getWriter().println("<h3>✅ Employee Deleted Successfully</h3>");

        } catch (Exception ex) {
            ex.printStackTrace();
            res.getWriter().println("<h3>❌ Error: " + ex.getMessage() + "</h3>");
        }
    }
}