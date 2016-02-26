package com.securitymanager.service;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.securitymanager.connection.DbPropertyValues;


public class DBController {
	Connection con ;
	PreparedStatement statement;
	ResultSet resultSet;
	String password;
	boolean insertFlag = true;
	DbPropertyValues dbProperties;
	private Logger logger = LoggerFactory.getLogger(DBController.class);
	DBEncryptionKey dbEncryptionKey;
	
	public DBController(){
		dbEncryptionKey = new DBEncryptionKey();
		password = dbEncryptionKey.getKey();
	}
	
	
	
	public boolean ifExistPath(String pathValue) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbProperties = new DbPropertyValues();
			String ip = dbProperties.getDbIP();
			String port = dbProperties.getDbPort();
			String dbUsername = dbProperties.getUserName();
			String dbPassword = dbProperties.getPassword();
			String dbName = dbProperties.getDbName();
			String dbConnection = "jdbc:mysql://"+ip+":"+port+"/"+dbName;
			con = DriverManager.getConnection(dbConnection,dbUsername,dbPassword);
			String query = "select aes_decrypt(path,?) from sd_components where aes_decrypt(path,?)=?";
			statement = con.prepareStatement(query);
			statement.setString(1, password);
			statement.setString(2, password);
			statement.setString(3, pathValue);
			resultSet=statement.executeQuery();
			if(resultSet.next()){
				return true;
			}
			
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			logger.error("SQLException", e);
		} finally{
			if(con!=null)
				try {
					con.close();
					resultSet.close();
					statement.close();
				} catch (SQLException e) {
					logger.error("SQLException", e);
				}
		}
		return false;
	}
	
	public void insertPath(String pathValue){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbProperties = new DbPropertyValues();
			String ip = dbProperties.getDbIP();
			String port = dbProperties.getDbPort();
			String dbUsername = dbProperties.getUserName();
			String dbPassword = dbProperties.getPassword();
			String dbName = dbProperties.getDbName();
			String dbConnection = "jdbc:mysql://"+ip+":"+port+"/"+dbName;
			con = DriverManager.getConnection(dbConnection,dbUsername, dbPassword);
			String query = "select aes_decrypt(path,?) from sd_components";
			statement = con.prepareStatement(query);
			statement.setString(1, password);
			resultSet = statement.executeQuery();
			insertFlag = true;
			while(resultSet.next()){
				try {
					String path = IOUtils.toString(resultSet.getBinaryStream(1), "UTF-8");
					if(path.equals(pathValue)){
						insertFlag = false;
					}
					
				} catch (IOException e) {
					logger.error("IOException", e);
				} 

			}
			
			if(insertFlag){
				statement.execute("insert into sd_components(path) values(aes_encrypt('"+pathValue+"','"+password+"'))");
			}
			
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			logger.error("SQLException", e);
		}finally{
			if(con!=null)
				try {
					con.close();
					resultSet.close();
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.error("SQLException", e);
				}
		}
	    
	}
	
	
	public void deletePath(String pathValue){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbProperties = new DbPropertyValues();
			String ip = dbProperties.getDbIP();
			String port = dbProperties.getDbPort();
			String dbUsername = dbProperties.getUserName();
			String dbPassword = dbProperties.getPassword();
			String dbName = dbProperties.getDbName();
			String dbConnection = "jdbc:mysql://"+ip+":"+port+"/"+dbName;
			con = DriverManager.getConnection(dbConnection,dbUsername, dbPassword);
			String query = "delete from sd_components where aes_decrypt(path,?)=?";
			statement = con.prepareStatement(query);
			statement.setString(1, password);
			statement.setString(2, pathValue);
			statement.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("SQLException", e);
		}finally{
			if(con!=null)
				try {
					con.close();
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.error("SQLException", e);
				}
		}
	    
	}
	
	
	public int insertUser(String fname, String lname,String userName,String email, String empid,String userpassword, String policy , String teamName){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbProperties = new DbPropertyValues();
			String ip = dbProperties.getDbIP();
			String port = dbProperties.getDbPort();
			String dbUsername = dbProperties.getUserName();
			String dbPassword = dbProperties.getPassword();
			String dbName = dbProperties.getDbName();
			String dbConnection = "jdbc:mysql://"+ip+":"+port+"/"+dbName;
			con = DriverManager.getConnection(dbConnection,dbUsername, dbPassword);
			String query = "insert into users(username,password,name,role,empId,email,lastname,teamName) values(aes_encrypt(?,?),aes_encrypt(?,?),?,?,?,?,?,?);";
			statement = con.prepareStatement(query);
			statement.setString(1, userName);
			statement.setString(2, password);
			statement.setString(3, userpassword);
			statement.setString(4, password);
			statement.setString(5, fname);
			statement.setString(6, policy);
			statement.setString(7, empid);
			statement.setString(8, email);
			statement.setString(9, lname);
			statement.setString(10, teamName);			
			statement.execute();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException", e);
			return 0;
		} catch (SQLException e) {
			logger.error("SQLException", e);
			return 0;
		}finally{
			if(con!=null)
				try {
					con.close();
					statement.close();
				} catch (SQLException e) {
					logger.error("SQLException", e);
				}
		}
		return 200;
	}

	public int deleteUser(String username) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbProperties = new DbPropertyValues();
			String ip = dbProperties.getDbIP();
			String port = dbProperties.getDbPort();
			String dbUsername = dbProperties.getUserName();
			String dbPassword = dbProperties.getPassword();
			String dbName = dbProperties.getDbName();
			String dbConnection = "jdbc:mysql://"+ip+":"+port+"/"+dbName;
			con = DriverManager.getConnection(dbConnection,dbUsername, dbPassword);
			String query = "select aes_decrypt(username,?) from users";
			statement = con.prepareStatement(query);
			statement.setString(1, password);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				if(resultSet.getString(1).equals(username)){
					statement.executeUpdate("delete from users where username=aes_encrypt('"+username+"','"+password+"')");
					break;
				}
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("ClassNotFoundException", e);
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("SQLException", e);
			return 0;
		}finally{
			if(con!=null)
				try {
					con.close();
					resultSet.close();
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.error("SQLException", e);
				}
		}
		return 200;
		
	}

	public int modifyUser(String fname, String lname,String userName,String email, String empid,String userpassword, String policy , String teamName) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbProperties = new DbPropertyValues();
			String ip = dbProperties.getDbIP();
			String port = dbProperties.getDbPort();
			String dbUsername = dbProperties.getUserName();
			String dbPassword = dbProperties.getPassword();
			String dbName = dbProperties.getDbName();
			String dbConnection = "jdbc:mysql://"+ip+":"+port+"/"+dbName;
			con = DriverManager.getConnection(dbConnection,dbUsername, dbPassword);
			String query = "update users set password=aes_encrypt(?,?),name=?,role=?,empId=?,email=?,lastname=?,teamName=? where username=aes_encrypt(?,?)";
			statement = con.prepareStatement(query);
			statement.setString(1, userpassword);
			statement.setString(2, password);
			statement.setString(3, userName);
			statement.setString(4, policy);
			statement.setString(5, empid);
			statement.setString(6, email);
			statement.setString(7, lname);
			statement.setString(8, teamName);
			statement.setString(9, userName);
			statement.setString(10, password);
			statement.executeUpdate();	
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException", e);
			return 0;
		} catch (SQLException e) {
			logger.error("SQLException", e);
			return 0;
		} finally{
			if(con!=null)
				try {
					con.close();
					statement.close();
				} catch (SQLException e) {
					logger.error("SQLException", e);
				}
		}
		return 200;
	}
	
	
	

	public List<String> list() {
		// TODO Auto-generated method stub
		List<String> users = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbProperties = new DbPropertyValues();
			String ip = dbProperties.getDbIP();
			String port = dbProperties.getDbPort();
			String dbUsername = dbProperties.getUserName();
			String dbPassword = dbProperties.getPassword();
			String dbName = dbProperties.getDbName();
			String dbConnection = "jdbc:mysql://"+ip+":"+port+"/"+dbName;
			con = DriverManager.getConnection(dbConnection,dbUsername, dbPassword);
			String query = "select aes_decrypt(username,?) from users";
			statement = con.prepareStatement(query);
			statement.setString(1, password);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				users.add(resultSet.getString(1));
			}
			
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			logger.error("SQLException", e);
		} finally{
			if(con!=null)
				try {
					con.close();
					resultSet.close();
					statement.close();
				} catch (SQLException e) {
					logger.error("SQLException", e);
				}
		}
		return users;
	}



	
	
}
