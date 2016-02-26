package com.snap.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.securitymanager.connection.DbPropertyValues;
import com.securitymanager.service.DBEncryptionKey;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(LoginController.class);   

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String destination = "";
		DBEncryptionKey dbEncryptionKey = new DBEncryptionKey();
		String userId = request.getParameter("username");    
		String userPassword = request.getParameter("password");
		String password = dbEncryptionKey.getKey();
		Connection con=null;
		PreparedStatement authenticate=null;
		ResultSet rs=null;
		DbPropertyValues dbProperties = new DbPropertyValues();
		String ip = dbProperties.getDbIP();
		String port = dbProperties.getDbPort();
		String dbUsername = dbProperties.getUserName();
		String dbPassword = dbProperties.getPassword();
		String dbName = dbProperties.getDbName();
		String dbConnection = "jdbc:mysql://"+ip+":"+port+"/"+dbName;


		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(dbConnection, dbUsername, dbPassword);
			String query = "select aes_decrypt(username,?),aes_decrypt(password,?),role,loginCount from users where username=aes_encrypt(?,?) and password=aes_encrypt(?,?);";
			authenticate = con.prepareStatement(query);
			authenticate.setString(1, password);
			authenticate.setString(2, password);
			authenticate.setString(3, userId);
			authenticate.setString(4, password);
			authenticate.setString(5, userPassword);
			authenticate.setString(6, password);

			rs = authenticate.executeQuery();
			if(rs.next()){
				if (rs.getInt(4)==1) {
					session.setAttribute("userid", rs.getString(1));
					session.setAttribute("userpolicy", rs.getString(3));
					destination = "/adminlist.jsp";
				} 
				if(rs.getInt(4)==0){
					session.setAttribute("userid", rs.getString(1));
					destination= "/resetPassword.jsp";
				}

			}

			else {
				destination = "/index.jsp";

			}}

		catch (ClassNotFoundException ex) {
			logger.error("ClassNotFoundException",ex);
		} catch (SQLException e) {
			logger.error("SQLException",e);
		}
		finally{
			if(rs!=null)
				try {
					rs.close();
					if(authenticate!=null)authenticate.close();
					if(con!=null)con.close();
				} catch (SQLException e) {
					logger.error("SQLException",e);
				}

		}

		if(destination.equals("/index.jsp")){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		else if(destination.equals("/resetPassword.jsp")){
			String nextJSP = "/resetPassword.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request,response);
		}
		else{
			request.getRequestDispatcher("/adminlist.jsp").forward(request, response);
		}


	}

}