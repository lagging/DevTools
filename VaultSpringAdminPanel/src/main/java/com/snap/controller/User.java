package com.snap.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.securitymanager.connection.DbPropertyValues;
import com.securitymanager.service.DBController;
import com.securitymanager.service.DBEncryptionKey;
import com.securitymanager.service.EmailService;
import com.securitymanager.service.Reminder;

/**
 * Servlet implementation class User
 */
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(User.class); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/UserView")){
			DBController dbcontroller = new DBController();
			List<String> users = dbcontroller.list();
			request.setAttribute("abc", users); // Will be available as ${products} in JSP
			request.getRequestDispatcher("/viewUser.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String username = request.getParameter("username1");
		DBController dbcontroller = new DBController();
		String message;
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/UserForgetPassword")){
			//String email=request.getParameter("email");
			Connection con=null;
			String uuid = UUID.randomUUID().toString();
			PreparedStatement statement;
			//ResultSet resultSet;
			DBEncryptionKey dbEncryptionKey=new DBEncryptionKey();
			final String  password=dbEncryptionKey.getKey();


			try {
				Class.forName("com.mysql.jdbc.Driver");
				DbPropertyValues dbProperties = new DbPropertyValues();
				String ip = dbProperties.getDbIP();
				String port = dbProperties.getDbPort();
				String dbUsername = dbProperties.getUserName();
				String dbPassword = dbProperties.getPassword();
				String dbName = dbProperties.getDbName();
				String dbConnection = "jdbc:mysql://"+ip+":"+port+"/"+dbName;
				con = DriverManager.getConnection(dbConnection,dbUsername, dbPassword);
				String query="UPDATE users SET password=aes_encrypt(?,?),loginCount=0 where aes_decrypt(username,?)=?;";
				statement = con.prepareStatement(query);
				statement.setString(1, uuid);
				statement.setString(2, password);
				statement.setString(3, password);
				statement.setString(4, username);
				statement.executeUpdate();
				EmailService email=new EmailService();
				email.sendMail(username, uuid);
				Reminder revoke=new Reminder();
				revoke.revokePass(username, password, uuid);
			}
			catch( ClassNotFoundException  e){
				logger.error("ClassNotFoundException",e);
			}
			catch( SQLException e){
				logger.error("SQLException",e);
			}
			finally{

				if(con!=null)
					try {
						con.close();
					} catch (SQLException e) {
						logger.error("SQLException",e);
					}


			}
			request.getRequestDispatcher("/index.jsp").forward(request, response);

		}

		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/UserResetPassword")){
			String resetPassword=request.getParameter("password1");
			Connection con=null;
			PreparedStatement statement;
			DBEncryptionKey dbEncryptionKey=new DBEncryptionKey();
			String password=dbEncryptionKey.getKey();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				DbPropertyValues dbProperties = new DbPropertyValues();
				String ip = dbProperties.getDbIP();
				String port = dbProperties.getDbPort();
				String dbUsername = dbProperties.getUserName();
				String dbPassword = dbProperties.getPassword();
				String dbName = dbProperties.getDbName();
				String dbConnection = "jdbc:mysql://"+ip+":"+port+"/"+dbName;
				con = DriverManager.getConnection(dbConnection,dbUsername, dbPassword);
				String query="UPDATE users SET password=aes_encrypt(?,?),loginCount=1 where aes_decrypt(username,?)=?;";
				statement = con.prepareStatement(query);
				statement.setString(1, resetPassword);
				statement.setString(2, password);
				statement.setString(3, password);
				statement.setString(4, username);
				statement.executeUpdate();

			}
			catch( ClassNotFoundException e){
				logger.error("ClassNotFoundException",e);
			}
			catch(  SQLException e){
				logger.error("SQLException",e);
			}
			finally{

				if(con!=null)
					try {
						con.close();
					} catch (SQLException e) {
						logger.error("SQLException",e);
					}


			}
			request.getRequestDispatcher("/index.jsp").forward(request, response);

		}
		
		
		
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/User")){
			
			String fname = request.getParameter("name1");
			String lname = request.getParameter("name2");
			String email = request.getParameter("email");
			String empid = request.getParameter("empid");
			String teamName = request.getParameter("teamname1");						
			String policy = request.getParameter("policy1");
			String password = request.getParameter("password1");
			
			
				int status=dbcontroller.insertUser(fname, lname,username,email,empid, password, policy,teamName);

				if(status==200){
					message="User inserted successfully";
				}
				else{
					message="User Exist or Details not Proper";
				}
				request.setAttribute("message", message);
				request.getRequestDispatcher("/inserted.jsp").forward(request, response);
			
		}
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/UserDelete")){
			
				int status=dbcontroller.deleteUser(username);
				if(status==200){
					message="User Deleted successfully";
				}
				else{
					message="Some error occured";
				}
				request.setAttribute("message", message);
				request.getRequestDispatcher("/inserted.jsp").forward(request, response);

		}
		
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/UserModify")){
			String fname = request.getParameter("name1");
			String lname = request.getParameter("name2");
			String email = request.getParameter("email");
			String empid = request.getParameter("empid");
			String teamName = request.getParameter("teamname1");						
			String policy = request.getParameter("policy1");
			String password = request.getParameter("password1");
			
				int status=dbcontroller.modifyUser(fname, lname,username,email,empid, password, policy,teamName);
				if(status==200){
					message="User Modified successfully";
				}
				else{
					message="Some error occured";
				}
				request.setAttribute("message", message);
				request.getRequestDispatcher("/inserted.jsp").forward(request, response);
				
			
		}
		
		
		
		

	}

}
