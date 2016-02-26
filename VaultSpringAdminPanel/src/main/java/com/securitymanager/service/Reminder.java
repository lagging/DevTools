package com.securitymanager.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.securitymanager.connection.DbPropertyValues;

public class Reminder {
	private Logger logger = LoggerFactory.getLogger(Reminder.class); 


	public void revokePass(String username, String password,String uuid){
		PreparedStatement statement1;
		Connection con1=null;
		String uuid1 = UUID.randomUUID().toString();
		String uuid2=uuid1.replaceAll("-","");
		uuid2=uuid2.replaceAll("\\d","");
		String query1="CREATE EVENT "+uuid2+" ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL 5 HOUR DO UPDATE users SET password=aes_encrypt(?,?) where aes_decrypt(username,?)=? and aes_decrypt(password,?)=?;";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			DbPropertyValues dbProperties = new DbPropertyValues();
			String ip = dbProperties.getDbIP();
			String port = dbProperties.getDbPort();
			String dbUsername = dbProperties.getUserName();
			String dbPassword = dbProperties.getPassword();
			String dbName = dbProperties.getDbName();
			String dbConnection = "jdbc:mysql://"+ip+":"+port+"/"+dbName;
			con1 = DriverManager.getConnection(dbConnection,dbUsername, dbPassword);
			statement1 = con1.prepareStatement(query1); 
			statement1.setString(1, uuid1);
			statement1.setString(2, password);
			statement1.setString(3, password);
			statement1.setString(4, username);
			statement1.setString(5, password);
			statement1.setString(6, uuid);
			statement1.execute();
		}catch (SQLException  e) {
			logger.error("SQLException",e);
		}catch (ClassNotFoundException e){
			logger.error("ClassNotFoundException",e);
		}
	}
}
