package com.registration.form;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	PrintWriter out = response.getWriter();
	//	out.print("working");
		
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String userName = request.getParameter("username");
		String pword = request.getParameter("password");
		String Address = request.getParameter("address");
		String PostalCode = request.getParameter("pcode");
		String PhoneNo = request.getParameter("phoneNo");
		RequestDispatcher dispatcher =null;
		Connection con = null; 
		try {
			Class.forName("com.mysql.jdbc.Driver");	
	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp?useSSL=false","root","root");
PreparedStatement pst = con.prepareStatement("insert into users(firstName,lastName,userName,pword,Address,PostalCode,PhoneNo)values(?,?,?,?,?,?,?)");
			pst.setString(1, firstName);
			pst.setString(2, lastName);
			pst.setString(3, userName);
			pst.setString(4, pword);
			pst.setString(5, Address);
			pst.setString(6, PostalCode);
			pst.setString(7, PhoneNo);
			
			
			int rowCount =pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if(rowCount> 0) {
				request.setAttribute("status", "success");
				
			}else {
				request.setAttribute("status", "failed");
			}
			
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
	}
}