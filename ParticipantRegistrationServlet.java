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

@WebServlet("/registerEvent")
public class ParticipantRegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        String Name = req.getParameter("name");
        String AttendeeType = req.getParameter("Attendeetype");
        String CollegeIRNNumber = req.getParameter("irn");
        String Department = req.getParameter("dept");
        String AcademicYear = req.getParameter("year");
        String EventName = req.getParameter("eventname");


        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventhub", "root","kalash");
            PreparedStatement ps = con.prepareStatement("insert into ParticipantRegistered values(?,?,?,?,?,?)");
            ps.setString(1,Name);
            ps.setString(2,AttendeeType);
            ps.setString(3,CollegeIRNNumber);
            ps.setString(4,Department);
            ps.setString(5,AcademicYear);
            ps.setString(6,EventName);


            int count = ps.executeUpdate();
            resp.setContentType("text/html");
            if (count >0)
            {
                out.print("<h3 style='color=green'> Register Successfully </h3>");


                RequestDispatcher rd  = req.getRequestDispatcher("/registerSuccess.html");
                rd.include(req,resp);
            }
            else
            {
                out.print("<h3 style='color=red'> Not Register Due To Some Error </h3>");


                RequestDispatcher rd  = req.getRequestDispatcher("/participantRegistration.html");
                rd.include(req,resp);
            }


        }
        catch(Exception e)
        {
            resp.setContentType("text/html");
            out.print("<h3 style='color=red'> Exception Occured"+ e.getMessage()+"</h3>");


            RequestDispatcher rd  = req.getRequestDispatcher("/participantRegistration.html");
            rd.include(req,resp);

        }

    }




}
