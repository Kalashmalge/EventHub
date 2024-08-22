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

@WebServlet("/regForm")
public class RegistrationServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String username = request.getParameter("name1");
        String email = request.getParameter("email1");
        String password = request.getParameter("pass1");


        try
        {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventhub", "root","kalash");
            PreparedStatement ps = con.prepareStatement("insert into registration values(?,?,?)");
            ps.setString(1,username);
            ps.setString(2,email);
            ps.setString(3,password);


            int count = ps.executeUpdate();
            response.setContentType("text/html");
            if (count >0)
            {
                out.print("<h3 style='color=green'> User Register Successfully </h3>");


                RequestDispatcher rd  = request.getRequestDispatcher("/user_type.html");
                rd.include(request,response);
            }
            else
            {
                out.print("<h3 style='color=red'> User Not Register Due To Some Error </h3>");


                RequestDispatcher rd  = request.getRequestDispatcher("/registration.html");
                rd.include(request,response);
            }
        }
        catch(Exception e)
        {

            response.setContentType("text/html");
            out.print("<h3 style='color=red'> Exception Occured"+ e.getMessage()+"</h3>");


            RequestDispatcher rd  = request.getRequestDispatcher("/registration.html");
            rd.include(request,response);

        }



    }


}
