package com.example.pbleventhub;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/attendeeForm")
public class AttendeeRegistrationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Include the CSS file in the HTML response
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>Attendee Registration</title>");
        out.println("<link rel=\"stylesheet\" href=\"css/attendeeTable.css\">"); // Link to your CSS file
        out.println("</head>");
        out.println("<body>");

        // Your HTML content for the attendee registration form goes here
        out.println("<h1>Attendee Registration Form</h1>");
        out.println("<form action=\"attendeeForm\" method=\"POST\">");
        // Include form fields, labels, etc.
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
//        PrintWriter out =  resp.getWriter();


        String event_name = req.getParameter("event_name");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventhub", "root", "kalash");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM participantregistered WHERE EventName = ?");
            ps.setString(1, event_name);
            ResultSet rs = ps.executeQuery();
//            String cssPath = req.getContextPath() + "/attendeeTable.css";
//            System.out.println("/attendeeTable.css " + "attendeeTable.css");




            out.println("<table border='1' cellpadding='5'>");
            out.println("<caption>Attendee Registration:</caption>");
            out.println("<tr>");

            ResultSetMetaData rsmd = rs.getMetaData();
            int totalColumn = rsmd.getColumnCount();

            // Display column labels
            for (int i = 1; i <= totalColumn; i++) {
                out.println("<th>" + rsmd.getColumnName(i) + "</th>");
            }

            out.println("</tr>");

            // Display data rows
            while (rs.next()) {
                out.println("<tr>");
                for (int i = 1; i <= totalColumn; i++) {
                    out.println("<td>" + rs.getString(i) + "</td>");
                }
                out.println("</tr>");
            }

            out.println("</table></body></html>");
            con.close();
        } catch (Exception e) {
            out.println(e);
        }



    }
}
