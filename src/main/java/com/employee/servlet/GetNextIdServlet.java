package com.employee.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.employee.dao.EmployeeDAO;

@WebServlet("/addEmployee")
public class GetNextIdServlet extends HttpServlet {

    /**
     * GET → called when the user navigates to the Add Employee page.
     * Fetches the next AUTO_INCREMENT value from the DB and forwards
     * to empadd.jsp so the ID is visible before the form is submitted.
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            EmployeeDAO dao = new EmployeeDAO();
            int nextId = dao.getNextEmployeeId();
            req.setAttribute("nextEmpId", nextId);
        } catch (Exception ex) {
            ex.printStackTrace();
            // On any DB error, pass -1 so JSP can show a graceful message
            req.setAttribute("nextEmpId", -1);
            req.setAttribute("idError", "Could not fetch next Employee ID: " + ex.getMessage());
        }

        req.getRequestDispatcher("empadd.jsp").forward(req, res);
    }
}
