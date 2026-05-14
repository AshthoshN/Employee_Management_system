package com.employee.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import java.util.List;

@WebServlet("/display")
public class DisplayEmployeeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        handle(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        handle(req, res);
    }

    private void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            EmployeeDAO dao = new EmployeeDAO();
            List<Employee> list = dao.getAllEmployees();
            req.setAttribute("employees", list);
            req.getRequestDispatcher("empdisplay.jsp").forward(req, res);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("message", "Error fetching employees: " + ex.getMessage());
            req.setAttribute("status",  "error");
            req.getRequestDispatcher("result.jsp").forward(req, res);
        }
    }
}
