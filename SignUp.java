package com.mylogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SignUp extends GenericServlet {

	@Override
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {

		String name = req.getParameter("username");
		String email = req.getParameter("emailid");
		String pass = req.getParameter("password");
		String mob = req.getParameter("mobileno");
		String city = req.getParameter("cityname");
		
		Connection cn = null;
		PreparedStatement pst = null;
		
		ServletContext con=getServletContext();
		

		try {
			Class.forName(con.getInitParameter("driver"));
			cn = DriverManager.getConnection(con.getInitParameter("url"),
					con.getInitParameter("user"),con.getInitParameter("pass"));
			
			pst = cn.prepareStatement("insert into practice.registration values(?,?,?,?,?)");
			pst.setString(1, name);
			pst.setString(2, email);
			pst.setString(3, pass);
			pst.setString(4, mob);
			pst.setString(5, city);

			pst.executeUpdate();
 
		} catch (SQLException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		
		PrintWriter pr = resp.getWriter();
		pr.print("<h1 style='color:white;'>Data Inserted successfully!!! Please log in</h1>");
//				+ "<a href='home.html'>back to home page</a>");
		
		RequestDispatcher rd=req.getRequestDispatcher("home.html");
		rd.include(req, resp);
//		rd.forward(req, resp);
		
	}

}
