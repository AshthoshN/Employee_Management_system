package com.employee.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import java.util.*;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            String type = req.getParameter("type");
            String value = req.getParameter("value");

            EmployeeDAO dao = new EmployeeDAO();
            List<Employee> list = dao.getReport(type, value);

            // HTML START
            out.println("<html><head>");
            out.println("<link rel='stylesheet' href='css/style.css'>");
            out.println("<title>Report Results</title>");
            out.println("</head><body>");

            out.println("<div class='container'>");
            out.println("<h2>Report Results</h2>");

            if (list.isEmpty()) {
                out.println("<h3>No Records Found</h3>");
            } else {

                out.println("<table style='width:100%; border-collapse: collapse;'>");

                // Table Header
                out.println("<tr style='background:#ff9800; color:white;'>");
                out.println("<th>ID</th>");
                out.println("<th>Name</th>");
                out.println("<th>DoJ</th>");
                out.println("<th>Gender</th>");
                out.println("<th>Salary</th>");
                out.println("</tr>");

                // Table Data
                for (Employee e : list) {
                    out.println("<tr style='text-align:center;'>");
                    out.println("<td>" + e.getEmpno() + "</td>");
                    out.println("<td>" + e.getEmpName() + "</td>");
                    out.println("<td>" + e.getDoj() + "</td>");
                    out.println("<td>" + e.getGender() + "</td>");
                    out.println("<td>" + e.getBsalary() + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
            }

            out.println("<br><br>");
            out.println("<a href='index.jsp'>⬅ Back to Home</a>");
            out.println("</div>");

            out.println("</body></html>");

        } catch (Exception ex) {
            ex.printStackTrace();
            out.println("<h3>Error: " + ex.getMessage() + "</h3>");
        }
    }
}