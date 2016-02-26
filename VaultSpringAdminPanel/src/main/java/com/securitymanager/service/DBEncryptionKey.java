package com.securitymanager.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.securitymanager.connection.DbPropertyValues;

public class DBEncryptionKey {
	
	DbPropertyValues dbPropertyValue;
	BufferedReader bufferedReader;
	String key;
	private Logger logger = LoggerFactory.getLogger(DBEncryptionKey.class);
	
	public String getKey() {
		dbPropertyValue = new DbPropertyValues();
		String path = dbPropertyValue.getEncryptionKeyPath();
		try {
			bufferedReader = new BufferedReader(new FileReader(path));
			key=bufferedReader.readLine();
			if(key != null){
				return key;
			}
			
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException",e);
		} catch(IOException e){
			logger.error("IOException",e);
		}
		
		
		return key;
	}

}
