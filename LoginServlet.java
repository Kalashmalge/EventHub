package com.example.pbleventhub;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/loginForm")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String email = req.getParameter("email1");
        String password = req.getParameter("pass1");

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventhub", "root","kalash");
            PreparedStatement ps = con.prepareStatement("select * from registration where email=? and password=?");
            ps.setString(1,email);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                RequestDispatcher rd = req.getRequestDispatcher("/user_type.html");
                rd.include(req,resp);
            }
            else
            {
                resp.setContentType("text/html");
                out.print("<h3 style='color=red'> Email id and Password did not matched </h3>");

                RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                rd.include(req,resp);

            }
        }
        catch (Exception e)
        {
            resp.setContentType("text/html");
            out.print("<h3 style='color=red'> "+e.getMessage()+" </h3>");

            RequestDispatcher rd = req.getRequestDispatcher("/login.html");
            rd.include(req,resp);

        }



    }
}
