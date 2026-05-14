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

        try {
            String type = req.getParameter("type");
            EmployeeDAO dao = new EmployeeDAO();
            List<Employee> list = new ArrayList<>();
            String filterDesc = "";

            if ("name".equals(type)) {
                String letter = req.getParameter("nameLetter");
                if (letter == null || letter.trim().isEmpty()) {
                    req.setAttribute("errorMsg", "Please enter a starting letter for the name filter.");
                    req.getRequestDispatcher("report_form.jsp").forward(req, res);
                    return;
                }
                list = dao.getReportByName(letter.trim().toUpperCase());
                filterDesc = "Names starting with &ldquo;" + letter.trim().toUpperCase() + "&rdquo;";

            } else if ("experience".equals(type)) {
                String fromStr = req.getParameter("expFrom");
                String toStr   = req.getParameter("expTo");
                int from, to;
                try {
                    from = Integer.parseInt(fromStr.trim());
                    to   = Integer.parseInt(toStr.trim());
                } catch (NumberFormatException nfe) {
                    req.setAttribute("errorMsg", "Please enter valid integer years for experience range.");
                    req.getRequestDispatcher("report_form.jsp").forward(req, res);
                    return;
                }
                if (from > to) {
                    req.setAttribute("errorMsg", "'From' years cannot be greater than 'To' years.");
                    req.getRequestDispatcher("report_form.jsp").forward(req, res);
                    return;
                }
                list = dao.getReportByExperience(from, to);
                filterDesc = "Experience between " + from + " and " + to + " years";

            } else if ("salary".equals(type)) {
                String minStr = req.getParameter("salMin");
                String maxStr = req.getParameter("salMax");
                double min = -1, max = -1;

                if (minStr != null && !minStr.trim().isEmpty()) {
                    try { min = Double.parseDouble(minStr.trim()); }
                    catch (NumberFormatException e) {
                        req.setAttribute("errorMsg", "Min salary must be a valid number.");
                        req.getRequestDispatcher("report_form.jsp").forward(req, res);
                        return;
                    }
                }
                if (maxStr != null && !maxStr.trim().isEmpty()) {
                    try { max = Double.parseDouble(maxStr.trim()); }
                    catch (NumberFormatException e) {
                        req.setAttribute("errorMsg", "Max salary must be a valid number.");
                        req.getRequestDispatcher("report_form.jsp").forward(req, res);
                        return;
                    }
                }
                if (min < 0 && max < 0) {
                    req.setAttribute("errorMsg", "Please enter at least a min or max salary.");
                    req.getRequestDispatcher("report_form.jsp").forward(req, res);
                    return;
                }
                list = dao.getReportBySalary(min, max);
                if (min >= 0 && max >= 0)      filterDesc = "Salary between ₹" + min + " and ₹" + max;
                else if (min >= 0)              filterDesc = "Salary &ge; ₹" + min;
                else                            filterDesc = "Salary &le; ₹" + max;

            } else {
                req.setAttribute("errorMsg", "Unknown report type selected.");
                req.getRequestDispatcher("report_form.jsp").forward(req, res);
                return;
            }

            req.setAttribute("employees",  list);
            req.setAttribute("filterDesc", filterDesc);
            req.getRequestDispatcher("report_result.jsp").forward(req, res);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("message", "Error generating report: " + ex.getMessage());
            req.setAttribute("status",  "error");
            req.getRequestDispatcher("result.jsp").forward(req, res);
        }
    }
}
