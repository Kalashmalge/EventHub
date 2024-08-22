package com.example.pbleventhub;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/eventCreation")
public class EvenCreationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PrintWriter out = resp.getWriter();

        String eventName = req.getParameter("eventName1");
        String eventDate = req.getParameter("eventDate1");
        String eventTime = req.getParameter("eventTime1");
        String eventVenue = req.getParameter("eventVenue1");
        String eventDescription = req.getParameter("eventDescription1");
        String eventType = req.getParameter("eventType1");
        String ticketOptions = req.getParameter("ticketOptions1");

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventhub", "root","kalash");


            PreparedStatement ps = con.prepareStatement("insert into eventcreation values(?,?,?,?,?,?,?)");
            ps.setString(1,eventName);
            ps.setString(2,eventDate);
            ps.setString(3,eventTime);
            ps.setString(4,eventVenue);
            ps.setString(5,eventDescription);
            ps.setString(6,eventType);
            ps.setString(7,ticketOptions);

            int count = ps.executeUpdate();
            resp.setContentType("text/html");

            if(count>0)
            {
                out.print("<h3 style='color=green'> Event Created Successfully </h3>");

                RequestDispatcher rd  = req.getRequestDispatcher("/organizer_dashboard.html");
                rd.include(req,resp);

            }
            else
            {
                out.print("<h3 style='color=red'> Event Not Created Due To Some Error </h3>");


                RequestDispatcher rd  = req.getRequestDispatcher("/event_creation.html");
                rd.include(req,resp);
            }

        }
        catch(Exception e)
        {
            resp.setContentType("text/html");
            out.print("<h3 style='color=red'> Exception Occured"+ e.getMessage()+"</h3>");


            RequestDispatcher rd  = req.getRequestDispatcher("/event_creation.html");
            rd.include(req,resp);
        }


    }



}
