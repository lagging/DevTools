package com.snap.contextlistener;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.securitymanager.connection.PropertyValues;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;


public class VaultCron implements Runnable{

	private HttpPost postRequest;
	private String apiURL;
	private HttpClient client;
	private String jssecacertpath;
	private String hostname;
	private PropertyValues properties = new PropertyValues();
	private Logger logger = LoggerFactory.getLogger(VaultCron.class);
	@Override
	public void run() {
		
		jssecacertpath = properties.getJssecacerPath();
		hostname = properties.getVaultIP();
		Properties systemProps = System.getProperties();
		systemProps.put( "javax.net.ssl.trustStore", jssecacertpath);
		System.setProperties(systemProps);
		
		apiURL = "https://"+hostname+":8200/v1/auth/userpass/login/vaulthealth";
		client = HttpClientBuilder.create().build();
		postRequest = new HttpPost(apiURL);
		StringEntity entity = new StringEntity("{ \"password\" : \"vaulthealth\" }", StandardCharsets.UTF_8);
		entity.setContentType("application/json");
		postRequest.setEntity(entity);
		HttpResponse responseStmt;
		try {
			responseStmt = client.execute(postRequest);
			StatusLine responseString=responseStmt.getStatusLine();
			logger.info("VAULT CRON "+responseString.getStatusCode());
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
		
		
		
	}

}
