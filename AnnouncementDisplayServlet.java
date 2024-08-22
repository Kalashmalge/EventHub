package com.example.pbleventhub;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/announcements")
public class AnnouncementDisplayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventhub", "root", "kalash");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM announcements");

            out.println("<html><body><table border='1' cellpadding='5'>");
//            out.println("<caption>Announcements</caption>");
//            out.println("<tr><th>Event Name</th><th>Announcement</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("EventName") + "</td>");
                out.println("<td>" + rs.getString("Announcement") + "</td>");
                out.println("</tr>");
            }

            out.println("</table></body></html>");
            con.close();
        } catch (Exception e) {
            out.println(e);
        }
    }
}
