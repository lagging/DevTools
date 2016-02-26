package com.securitymanager.connection;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbPropertyValues {

	public InputStream inputStream;
	public Properties properties;
	public String dbIP;
	public String dbPort;
	public String dbName;
	public String userName;
	public String password;
	public HashMap<String, String> keyValue;
	private Logger logger = LoggerFactory.getLogger(DbPropertyValues.class);
	public DbPropertyValues() {
		try {
			properties = new Properties();
			String propertyFileName = "vaultConfig.properties";  //mandatory name 'config.properties'
			inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);
			if (inputStream != null) {
				properties.load(inputStream);
				
			} else {
				throw new FileNotFoundException("Property file '" + propertyFileName + "' not found in the classpath");
			}

		} catch (Exception e) {
			logger.error("Property values error", e);
		} 

	}
	
	

	public String getDbIP() {
		return properties.getProperty("mysql.ip");
	}



	public String getDbPort() {
		return properties.getProperty("mysql.port");
	}



	public String getDbName() {
		return properties.getProperty("mysql.db");
	}

	

	public String getUserName() {
		return properties.getProperty("mysql.username");
	}



	public String getPassword() {
		return properties.getProperty("mysql.password");
	}
	
	
	public String getEncryptionKeyPath() {
		return properties.getProperty("mysql.encryptionKeyPath");
	}
	
	public String getCertFolderPath() {
		return properties.getProperty("certFolderPath");
	}
	
	
	
}
