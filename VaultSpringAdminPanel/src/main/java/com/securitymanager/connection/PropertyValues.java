package com.securitymanager.connection;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyValues {
	
	public InputStream inputStream;
	public Properties properties;
	public String vaultIP;
	public String port;
	public String username;
	public String password;
	public String keyPassword;
	public HashMap<String, String> keyValue;
	private Logger logger = LoggerFactory.getLogger(PropertyValues.class);
	public PropertyValues() {
		// TODO Auto-generated constructor stub
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
	


	public String getVaultIP() {
		return properties.getProperty("vaultIP");
	}

	public String getPort() {
		return properties.getProperty("port");
	}
	
	public String getJssecacerPath() {
		return properties.getProperty("jssecacertPath");
	}

	public String getKeystorePath() {
		return properties.getProperty("keystorePath");
	}
	
	public String getKeyPassword(){
		return properties.getProperty("keyPassword");	
	}


	public String getFlag() {
		// TODO Auto-generated method stub
		return properties.getProperty("vaultDisable");
	}
	
	public String getBackendPath() {
		// TODO Auto-generated method stub
		return properties.getProperty("backendPath");
	}
	
	public HashMap<String, String>getKeyValue(){
		Enumeration<Object> e = properties.keys();
		keyValue = new HashMap<String, String>();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = properties.getProperty(key);
			keyValue.put(key, value);
		}
		return keyValue;
	}



	
	

}
