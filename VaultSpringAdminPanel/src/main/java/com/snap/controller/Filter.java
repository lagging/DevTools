package com.snap.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.securitymanager.connection.DbPropertyValues;
import com.securitymanager.service.DBEncryptionKey;

/**
 * Servlet implementation class Filter
 */
public class Filter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(Filter.class);    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Filter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
			List<String> allPaths = new ArrayList<String>();
			Connection connection=null;
			PreparedStatement statement=null;
			ResultSet resultset =null;
			DBEncryptionKey dbEncryptionKey = new DBEncryptionKey();
		    String password = dbEncryptionKey.getKey();
			DbPropertyValues dbProperties = new DbPropertyValues();
			String ip = dbProperties.getDbIP();
			String port = dbProperties.getDbPort();
			String dbUsername = dbProperties.getUserName();
			String dbPassword = dbProperties.getPassword();
			String dbName = dbProperties.getDbName();
			String dbConnection = "jdbc:mysql://"+ip+":"+port+"/"+dbName;
			try {
				connection = DriverManager
						.getConnection(dbConnection,dbUsername, dbPassword);
				String query = "select aes_decrypt(path,?) from sd_components";
				statement = connection.prepareStatement(query);
				statement.setString(1, password);
				resultset = statement.executeQuery();
				while(resultset.next()){
					allPaths.add(resultset.getString(1));
				}
				request.setAttribute("allPaths", allPaths);
				if(request.getRequestURI().substring(request.getContextPath().length()).equals("/FilterAddKeyValue")){		
					request.getRequestDispatcher("/add.jsp").forward(request, response);
				}
				
				if(request.getRequestURI().substring(request.getContextPath().length()).equals("/FilterDeleteKeyValue")){		
					request.getRequestDispatcher("/delete.jsp").forward(request, response);
				}
				
				if(request.getRequestURI().substring(request.getContextPath().length()).equals("/FilterViewKeyValue")){		
					request.getRequestDispatcher("/view.jsp").forward(request, response);
				}
				
				if(request.getRequestURI().substring(request.getContextPath().length()).equals("/FilterComponent")){		
					request.getRequestDispatcher("/deleteComponent.jsp").forward(request, response);
				}
				
			}catch(SQLException ex){
				logger.error("SQLException", ex);
			}finally{
				if(connection!=null)
					try {
						connection.close();
						resultset.close();
						statement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						logger.error("SQLException", e);
					}
				
			}
				
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
