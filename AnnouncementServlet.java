package com.example.pbleventhub;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/announcement")
public class AnnouncementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();


        String EventName = req.getParameter("eventName");
        String yourAnnouncement = req.getParameter("announcementMessage");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventhub", "root", "kalash");



            PreparedStatement ps = con.prepareStatement("insert into announcements values(?,?)");

            ps.setString(1, EventName);
            ps.setString(2, yourAnnouncement);

            int count = ps.executeUpdate();
            resp.setContentType("text/html");
            if (count >0)
            {
                out.print("<h3 style='color=green'> Announcement Done  </h3>");


                RequestDispatcher rd  = req.getRequestDispatcher("/organizer_dashboard.html");
                rd.include(req,resp);
            }
            else
            {
                out.print("<h3 style='color=red'> Announcement Failed </h3>");


                RequestDispatcher rd  = req.getRequestDispatcher("/announcement.html");
                rd.include(req,resp);
            }

        }
        catch (Exception e)
        {
            resp.setContentType("text/html");
            out.print("<h3 style='color=red'> Exception Occured"+ e.getMessage()+"</h3>");


            RequestDispatcher rd  = req.getRequestDispatcher("/announcement.html");
            rd.include(req,resp);

        }

    }
}
