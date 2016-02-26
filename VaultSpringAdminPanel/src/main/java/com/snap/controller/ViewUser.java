package com.snap.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.securitymanager.connection.DbPropertyValues;
import com.securitymanager.service.DBEncryptionKey;

/**
 * Servlet implementation class ViewUser
 */
@WebServlet("/ViewUser")
public class ViewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(ViewUser.class);   
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Map<String,String> allUsers = new HashMap<String,String>();
		DBEncryptionKey dbEncryptionKey = new DBEncryptionKey();
	    String password = dbEncryptionKey.getKey();
	    Connection con=null;
	    PreparedStatement statement=null;
	    ResultSet resultset=null;
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
			String query = "select aes_decrypt(username,?),role from users";
			statement = con.prepareStatement(query);
			statement.setString(1, password);
			resultset = statement.executeQuery();
			while(resultset.next()){
				allUsers.put(resultset.getString(1),resultset.getString(2));
			}
			request.setAttribute("allUsers", allUsers);

		} catch (ClassNotFoundException ex) {
			logger.error("ClassNotFoundException",ex);
		} catch (SQLException e) {
			logger.error("SQLException",e);
		}
	    finally{
	    	if(resultset!=null)
				try {
					resultset.close();
					if(statement!=null)statement.close();
			    	if(con!=null)con.close();
				} catch (SQLException e) {
					logger.error("SQLException",e);
				}
	    	
	    }
	    request.getRequestDispatcher("/viewUser.jsp").forward(request, response);
	    

	}

}
