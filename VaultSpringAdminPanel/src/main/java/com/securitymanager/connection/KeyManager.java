package com.securitymanager.connection;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Properties;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



public class KeyManager {
	private String clientToken="";
	private String vaultIP;
	private String port;
	private String jssecacertPath;
	private String keystorePath;
	private String keyPassword;
	private PropertyValues propertyValues;
	private String apiURL;
	private String baseURL="https://";
	private String version="/v1/";
	private String path;
	private int statusCode;
	private String vaultTokenHeader="X-Vault-Token";
	private Logger logger = LoggerFactory.getLogger(KeyManager.class);
	private HttpClient client;
	private HttpGet getRequest;
	private HttpPost postRequest;
	private HttpPut putRequest;
	private HttpDelete deleteRequest;
	private HttpResponse responseStmt;
	private StatusLine responseString;
	private BufferedReader bufferedReader;
	private StringBuilder response;
	private String lineReader;
	private JSONObject responseObject ;
	private JSONObject jsonObject;
	private JSONObject policyResponse;
	private URL url;
	private HttpsURLConnection con;
	private KeyManagerFactory keyManagerFactory;
	private KeyStore keyStore;
	private InputStream keyInput;
	private SSLContext context ;
	private  InputStream inputStream;
	private String flag;


	public  KeyManager(){
		propertyValues=new PropertyValues();
		this.vaultIP=propertyValues.getVaultIP();
		this.port=propertyValues.getPort();
		this.jssecacertPath=propertyValues.getJssecacerPath();
		this.keystorePath=propertyValues.getKeystorePath();
		this.keyPassword=propertyValues.getKeyPassword();
		propertyValues.getBackendPath();
		this.flag=propertyValues.getFlag();
	}


	public  KeyManager(String vaultIP,String port,String jssecacertPath,String keystorePath,String keyPassword,String flag,String backendPath){
		propertyValues=new PropertyValues();
		this.vaultIP=vaultIP;
		this.port=port;
		this.jssecacertPath=jssecacertPath;
		this.keystorePath=keystorePath;
		this.keyPassword=keyPassword;
		this.flag=flag;
	}




	public void authenticate()throws KeyManagerConnectionException, IOException{
		if(flag.equals("0")){
			Properties systemProps = System.getProperties();
			systemProps.put( "javax.net.ssl.trustStore", jssecacertPath);
			System.setProperties(systemProps);
			path = "auth/cert/login";
			apiURL =  baseURL  + vaultIP + ":" + port + version +  path ;
			// Open a secure connection.  
			try {
				url = new URL(apiURL );
			} catch (MalformedURLException e1) {
				logger.error("URL error", e1);
			}

			try {
				con = (HttpsURLConnection) url.openConnection();
				con.setRequestProperty( "Connection", "close" );
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setUseCaches(false);
				con.setRequestMethod( "POST" );
				con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
			} catch (IOException e1) {
				logger.error("IOException", e1);
			}
			
			// Set up the connection properties  
			File pKeyFile = new File(keystorePath);

			try {
				keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
				keyStore = KeyStore.getInstance("PKCS12");
				keyInput = new FileInputStream(pKeyFile);
				keyStore.load(keyInput, keyPassword.toCharArray());
				keyInput.close();
				keyManagerFactory.init(keyStore, keyPassword.toCharArray());
				context = SSLContext.getInstance("TLS");
				context.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
			} catch (NoSuchAlgorithmException e) {
				logger.error("NoSuchAlgorithException", e);
			} catch (KeyStoreException e) {
				logger.error("KeyStoreException", e);
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				logger.error("CerificateException", e);
			} catch (UnrecoverableKeyException e) {
				logger.error("UnrecoverableKeyException", e);
			} catch (KeyManagementException e) {
				logger.error("KeyManagementException", e);
			}

			SSLSocketFactory sockFact = context.getSocketFactory();
			con.setSSLSocketFactory( sockFact );

			// Check for errors
			try {
				statusCode = con.getResponseCode();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error("IOException", e1);
			}

			if (statusCode == HttpURLConnection.HTTP_OK) {
				logger.info("[VAULT AUTHENTICATED]");
				try {
					inputStream = con.getInputStream();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					logger.error("IOException", e1);
				}
				// Process the response

				String line = null;
				response = null;
				response = new StringBuilder();
				bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );
				while( ( line = bufferedReader.readLine() ) != null )
				{
					response.append(line);
				}

				try {
					inputStream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					logger.error("IOException", e1);
				}
				try {
					responseObject = new JSONObject(response.toString());
					clientToken=responseObject.getJSONObject("auth").getString("client_token");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					logger.error("JSON Exception error", e);
				}

			} else {
				throw new KeyManagerConnectionException(statusCode);
			}

		}

	}


	public HashMap<String,String> getCredentials(String path)throws IOException, KeyManagerConnectionException{

		HashMap<String, String> keyValue = new HashMap<String, String>();
		apiURL = baseURL + vaultIP + ":" + port + version + path ;
		client = HttpClientBuilder.create().build();
		getRequest = new HttpGet(apiURL);
		getRequest.addHeader(vaultTokenHeader, clientToken);
		responseStmt = client.execute(getRequest);
		responseString=responseStmt.getStatusLine();
		statusCode=responseString.getStatusCode();
		if(statusCode == 200){
			logger.info("[PATH VALID]");
			bufferedReader = new BufferedReader(new InputStreamReader(responseStmt.getEntity().getContent()));
			lineReader = null;
			response = new StringBuilder();
			while ((lineReader = bufferedReader.readLine()) != null) {
				response.append(lineReader);
			}

			try {
				JSONObject responseJson = new JSONObject(response.toString());
				JSONObject dataJson = responseJson.getJSONObject("data");
				Iterator<?> iterator = dataJson.keys();
				while(iterator.hasNext()){
					String key = (String)iterator.next();
					String value = dataJson.getString(key);
					keyValue.put(key,value);
				}
				return keyValue;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				logger.error("JSON Exception error", e);
			}

		}
		else{
			throw new KeyManagerConnectionException(statusCode);
		}

		return keyValue;
	}


	public int insertKeyValue(String path2, String key, String value) {
		// TODO Auto-generated method stub
		apiURL = baseURL + vaultIP + ":" + port + version +  path2;
		client = HttpClientBuilder.create().build();
		postRequest = new HttpPost(apiURL);
		postRequest.addHeader(vaultTokenHeader, clientToken);
		jsonObject = new JSONObject();
		HashMap<String, String> keyValue = new HashMap<String, String>();
		try {
			keyValue = getCredentials(path2);
		} catch (IOException e1) {
			logger.error("IOException", e1);
			return 0;
		} catch (KeyManagerConnectionException e1) {
			logger.error("KeyManagerConnectionException", e1);
			return 0;
		}

		for(String existingKey : keyValue.keySet()){
			jsonObject.put(existingKey, keyValue.get(existingKey));
		}
		jsonObject.put(key, value);
		StringEntity entity = new StringEntity(jsonObject.toString(), StandardCharsets.UTF_8);
		entity.setContentType("application/json");
		postRequest.setEntity(entity);
		try {
			responseStmt = client.execute(postRequest);
			responseString=responseStmt.getStatusLine();
			statusCode=responseString.getStatusCode();

		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException", e);
			return 0;
		} catch (IOException e) {
			logger.error("IOException", e);
			return 0;
		}


		return 200;
	}

	public int deleteKeyValue(String path2, String key, String value) {
		// TODO Auto-generated method stub
		apiURL = baseURL + vaultIP + ":" + port + version +  path2;
		client = HttpClientBuilder.create().build();
		postRequest = new HttpPost(apiURL);
		postRequest.addHeader(vaultTokenHeader, clientToken);
		jsonObject = new JSONObject();
		HashMap<String, String> keyValue = new HashMap<String, String>();
		try {
			keyValue = getCredentials(path2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("IOException", e1);
			return 0;
		} catch (KeyManagerConnectionException e1) {
			// TODO Auto-generated catch block
			logger.error("KeyManagerConnectionException", e1);
			return 0;
		}

		for(String existingKey : keyValue.keySet()){
			jsonObject.put(existingKey, keyValue.get(existingKey));
		}
		jsonObject.remove(key);
		StringEntity entity = new StringEntity(jsonObject.toString(), StandardCharsets.UTF_8);
		entity.setContentType("application/json");
		postRequest.setEntity(entity);
		try {
			responseStmt = client.execute(postRequest);
			responseString=responseStmt.getStatusLine();
			statusCode=responseString.getStatusCode();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			logger.error("ClientProtocolException", e);
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IOException", e);
			return 0;
		}


		return 200;
	}


	public int deleteUser(String name) {
		// TODO Auto-generated method stub
		path = "auth/cert/certs/";
		apiURL = baseURL + vaultIP + ":" + port + version +  path + name;
		client = HttpClientBuilder.create().build();
		deleteRequest = new HttpDelete(apiURL);
		deleteRequest.addHeader(vaultTokenHeader, clientToken);
		try {
			responseStmt = client.execute(deleteRequest);
			responseString=responseStmt.getStatusLine();
			statusCode=responseString.getStatusCode();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			logger.error("ClientProtocolException", e);
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IOException", e);
			return 0;
		}
		return 200;
	}

	public JSONObject viewPolicy(String policyname) {
		// TODO Auto-generated method stub
		path = "sys/policy/";
		apiURL = baseURL + vaultIP + ":" + port + version +  path + policyname;
		client = HttpClientBuilder.create().build();
		getRequest = new HttpGet(apiURL);
		getRequest.addHeader(vaultTokenHeader, clientToken);
		try {
			responseStmt = client.execute(getRequest);
			responseString=responseStmt.getStatusLine();
			statusCode=responseString.getStatusCode();
			if(statusCode == 200){
				bufferedReader = new BufferedReader(new InputStreamReader(responseStmt.getEntity().getContent()));
				lineReader = null;
				response = new StringBuilder();
				while ((lineReader = bufferedReader.readLine()) != null) {
					response.append(lineReader);
				}
				policyResponse = new JSONObject(response.toString());
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			logger.error("ClientProtocolException", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IOException", e);
		}
		return policyResponse;
	}

	public int deletePolicy(String policyname) {
		// TODO Auto-generated method stub
		path = "sys/policy/";
		apiURL = baseURL + vaultIP + ":" + port + version +  path + policyname;
		client = HttpClientBuilder.create().build();
		deleteRequest = new HttpDelete(apiURL);
		deleteRequest.addHeader(vaultTokenHeader, clientToken);
		try {
			responseStmt = client.execute(deleteRequest);
			responseString=responseStmt.getStatusLine();
			statusCode=responseString.getStatusCode();			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			logger.error("ClientProtocolException", e);
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IOException", e);
			return 0;
		}
		return 200;
	}


	public int createPolicy(String policyjson, String policyname) {
		// TODO Auto-generated method stub
		path = "sys/policy/";
		apiURL = baseURL + vaultIP + ":" + port + version +  path + policyname;
		client = HttpClientBuilder.create().build();
		putRequest = new HttpPut(apiURL);
		putRequest.addHeader(vaultTokenHeader, clientToken);
		StringEntity entity = new StringEntity(policyjson, StandardCharsets.UTF_8);
		entity.setContentType("application/json");
		putRequest.setEntity(entity);

		try {
			responseStmt = client.execute(putRequest);
			responseString=responseStmt.getStatusLine();
			statusCode=responseString.getStatusCode();
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException", e);
			return 0;
		} catch (IOException e) {
			logger.error("IOException", e);
			return 0;
		}
		return 200;
	}


	public List<String> allPolicy() {

		path = "sys/policy";
		apiURL = baseURL + vaultIP + ":" + port + version +  path ;
		client = HttpClientBuilder.create().build();
		getRequest = new HttpGet(apiURL);
		getRequest.addHeader(vaultTokenHeader, clientToken);
		List<String> allPolicies = new ArrayList<String>();

		try {
			responseStmt = client.execute(getRequest);
			responseString=responseStmt.getStatusLine();
			statusCode=responseString.getStatusCode();
			if(statusCode == 200){
				bufferedReader = new BufferedReader(new InputStreamReader(responseStmt.getEntity().getContent()));
				lineReader = null;
				response = new StringBuilder();
				while ((lineReader = bufferedReader.readLine()) != null) {
					response.append(lineReader);
				}
				try {
					JSONObject responseJson = new JSONObject(response.toString());
					JSONArray policies = responseJson.getJSONArray("policies");
					for(int id=0; id<policies.length();id++){
						String policy = policies.getString(id).toString();
						allPolicies.add(policy);
					}
					
				} catch (JSONException e) {
					logger.error("JSON Exception error", e);
				}

			}
		}catch (ClientProtocolException e1) {
			logger.error("ClientProtocolException", e1);
		} catch (IOException e1) {
			logger.error("IOException", e1);
		}

		return allPolicies;
	}


	public int firstKeyValue(String pathValue, String key, String Value) {
		apiURL = baseURL + vaultIP + ":" + port + version +  pathValue;
		client = HttpClientBuilder.create().build();
		postRequest = new HttpPost(apiURL);
		postRequest.addHeader(vaultTokenHeader, clientToken);
		jsonObject = new JSONObject();
		jsonObject.put(key, Value);
		StringEntity entity = new StringEntity(jsonObject.toString(), StandardCharsets.UTF_8);
		entity.setContentType("application/json");
		postRequest.setEntity(entity);
		try {
			responseStmt = client.execute(postRequest);
			responseString=responseStmt.getStatusLine();
			statusCode=responseString.getStatusCode();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			logger.error("ClientProtocolException", e);
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IOException", e);
			return 0;
		}


		return 200;
	}


	public int deleteComponent(String component) {
		apiURL = baseURL + vaultIP + ":" + port + version +  component;
		client = HttpClientBuilder.create().build();
		deleteRequest = new HttpDelete(apiURL);
		deleteRequest.addHeader(vaultTokenHeader, clientToken);
		try {
			responseStmt = client.execute(deleteRequest);
			responseString=responseStmt.getStatusLine();
			statusCode=responseString.getStatusCode();			
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException", e);
			return 0;
		} catch (IOException e) {
			logger.error("IOException", e);
			return 0;
		}
		return 200;
	}

}


