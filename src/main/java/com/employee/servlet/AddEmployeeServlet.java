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

        res.setContentType("text/html");

        try {
            Employee e = new Employee();

            e.setEmpno(Integer.parseInt(req.getParameter("empno")));
            e.setEmpName(req.getParameter("empname"));
            e.setDoj(Date.valueOf(req.getParameter("doj")));
            e.setGender(req.getParameter("gender"));
            e.setBsalary(Double.parseDouble(req.getParameter("salary")));

            EmployeeDAO dao = new EmployeeDAO();
            dao.addEmployee(e);

            res.getWriter().println("<h3>✅ Employee Added Successfully</h3>");

        } catch (Exception ex) {
            ex.printStackTrace();
            res.getWriter().println("<h3>❌ Error: " + ex.getMessage() + "</h3>");
        }
    }
}