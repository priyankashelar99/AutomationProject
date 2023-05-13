package com.registration.form;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("username");
		String PassWord = request.getParameter("password");
		HttpSession session =request.getSession();
		RequestDispatcher dispatcher= null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/users?useSSL=false","root","root");
			PreparedStatement pst = con.prepareStatement("select *from user where userName = ? and PassWord = ?");
			pst.setString(1, userName);
			pst.setString(2, PassWord);
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				session.setAttribute("name", rs.getString("userName"));
				dispatcher =request.getRequestDispatcher("register.jsp");
			}
			else {
				request.setAttribute("status", "failed");
				dispatcher =request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	


