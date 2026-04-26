package com.employee.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;

@WebServlet("/display")
public class DisplayEmployeeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");

        try {
            int empno = Integer.parseInt(req.getParameter("empno"));

            EmployeeDAO dao = new EmployeeDAO();
            Employee e = dao.getEmployee(empno);

            if (e != null) {
                res.getWriter().println("<h3>Employee Details</h3>");
                res.getWriter().println("Emp No: " + e.getEmpno() + "<br>");
                res.getWriter().println("Name: " + e.getEmpName() + "<br>");
                res.getWriter().println("DoJ: " + e.getDoj() + "<br>");
                res.getWriter().println("Gender: " + e.getGender() + "<br>");
                res.getWriter().println("Salary: " + e.getBsalary() + "<br>");
            } else {
                res.getWriter().println("<h3>❌ Employee Not Found</h3>");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            res.getWriter().println("<h3>❌ Error: " + ex.getMessage() + "</h3>");
        }
    }
}