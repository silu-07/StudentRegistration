package com.mylogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LoginValidation extends GenericServlet {

	@Override
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {

		String user = req.getParameter("us");
		String pass = req.getParameter("ps");
		
		ServletContext con=getServletContext();
	
		
		PrintWriter pr = resp.getWriter();
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;

		try {
			Class.forName(con.getInitParameter("driver"));
			cn = DriverManager.getConnection(con.getInitParameter("url"),
					con.getInitParameter("user"),con.getInitParameter("pass"));
			
			pst = cn.prepareStatement("select * from practice.registration where name=?");
			pst.setString(1, user);

			rs = pst.executeQuery();
			if(pass==null) {
				 pr.println("<h1 style='color:white;'>No password entered");
				 
				
					RequestDispatcher rd=req.getRequestDispatcher("home.html");
					rd.include(req, resp);
			}
			
			if (rs.next()) {
				String dbpass = rs.getString("password");
					if (dbpass.equals(pass)) 
						{
							pr.println("<h1>Welcome..." + "</h1>");
						} 
					else 
						{
						  pr.println("<h1 style='color:white;'>Incorrect Password  -  Please Try Again");
						  
								
								RequestDispatcher rd=req.getRequestDispatcher("home.html");
								rd.include(req, resp);
						}
			} 
			else 
			{
			
				  pr.println("<h1 style='color:white;'>User not present - " +
				  " <a>Please sign up below</a>");
				 
				
				RequestDispatcher rd=req.getRequestDispatcher("Registration.html");
				rd.include(req, resp);
			}

		} catch (SQLException | ClassNotFoundException e) {

			e.printStackTrace();
		}

		pr.print("<a href='home.html'>back to home page</a>");

	}

}
