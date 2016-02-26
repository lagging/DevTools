package com.snap.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.securitymanager.service.DBController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.securitymanager.connection.KeyManager;
import com.securitymanager.connection.KeyManagerConnectionException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class Admin
 */

public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(Admin.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
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
		
		DBController dbController;
		String message = null;
		KeyManager keyManager = new KeyManager();
		JSONObject policyResponse = new JSONObject();
		Boolean insert = true;
		
		try {
			keyManager.authenticate();
		} catch (KeyManagerConnectionException e) {
			logger.error("KeyManagerConnectionException", e);
			
		}
		
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/AdminAdd")){
			String path = request.getParameter("path");
			if(path.equals("--select-path")){
				message = "SELECT PROPER PATH";
			}
			else{
				String key = request.getParameter("key");
				String value = request.getParameter("value");
				if((key == "") || (value == "")){
					message = "ENTER BOTH KEY VALUE PAIRS";
				}
				else{
					Map<String, String> keyValue;
					insert = true;
					try {
						keyValue = keyManager.getCredentials(path);

						for (Map.Entry<String, String> entry : keyValue.entrySet())
						{
							if(key.equals(entry.getKey())){
								int reply = JOptionPane.showConfirmDialog(null, "This key already exists. Do you want to modify?", "Confirm", JOptionPane.YES_NO_OPTION);
								if (reply == JOptionPane.NO_OPTION){
									 insert = false;
								 }
							}

						}

					} catch (KeyManagerConnectionException e) {
						logger.error("KeyManagerConnectionException", e);
					}
					if(insert){
						int status = keyManager.insertKeyValue(path,key,value);
						if(status == 200){
							message = "KEY VALUE PAIR INSERTED SUCCESSFULLY";
						}
						else{
							message = "SOME ERROR OCCURED CHECK LOGS";
						}	
						request.setAttribute("message", message);
						request.getRequestDispatcher("/inserted.jsp").forward(request, response);	
					}
					else{
						request.getRequestDispatcher("/FilterAddKeyValue").forward(request, response);
					}
					
				}
			}
			
		}
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/AdminDelete")){
			String path = request.getParameter("path");
			if(path.equals("--select-path")){
				message = "SELECT PROPER PATH";
			}
			else{
				String key = request.getParameter("key");
				String value = request.getParameter("value");
				if((key == "") || (value == "")){
					message = "ENTER BOTH KEY VALUE PAIRS";
				}
				else{
					int status = keyManager.deleteKeyValue(path,key,value);
					if(status == 200){
						message = "KEY VALUE PAIR DELETED SUCCESSFULLY";
					}
					else{
						message = "SOME ERROR OCCURED CHECK LOGS";
					}
				}
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/inserted.jsp").forward(request, response);
		}
		
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/AdminView")){
			String path = request.getParameter("path");
			if(path.equals("--select-path")){
				message = "SELECT PROPER PATH";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/inserted.jsp").forward(request, response);
			}
			else{
				HashMap<String, String> keyValue;
				try {
					keyValue = keyManager.getCredentials(path);
					request.setAttribute("keyValue", keyValue);
					request.getRequestDispatcher("/viewed.jsp").forward(request, response);
				} catch (KeyManagerConnectionException e) {
					// TODO Auto-generated catch block
					logger.error("KeyManagerConnectionException", e);
				}
			}
			
		}
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/AdminAddPath")){
			
				String pathValue=request.getParameter("path");
				if(pathValue == ""){
					message = "INSERT PROPER PATH";
				}
				else{
					dbController = new DBController();
					if(dbController.ifExistPath(pathValue)){
						message = "PATH ALREADY EXIST";
					}
					else{
						if((keyManager.firstKeyValue(pathValue,"default","default") == 200)){
							dbController.insertPath(pathValue);
							message = "PATH ADDED SUCCESSFULLY";
						}
						else{
							message = "ERROR OCCURED WHILE ADDING PATH ";
						}
					}
					
					
				}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/inserted.jsp").forward(request, response);
		}
		
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/VaultDeleteUser")){
			String name = request.getParameter("name");
			if(name==""){
				message="PROVIDE NAME PLEASE";
			}
			else{
				int status = keyManager.deleteUser(name);
				if(status==200){
					message="USER DELETED SUCCESSFULLY";
				}else{
					message="ERROR!!! IN DELETING USER";
				}
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/inserted.jsp").forward(request, response);
		}
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/VaultPolicyView")){
			String policyname = request.getParameter("policyname");
			if(policyname==""){
				message="PROVIDE NAME PLEASE";
			}
			else{
				policyResponse = keyManager.viewPolicy(policyname);
			}
			request.setAttribute("message", policyResponse);
			request.getRequestDispatcher("/inserted.jsp").forward(request, response);
		}
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/VaultPolicyDelete")){
			String policyname = request.getParameter("policyname");
			if(policyname==""){
				message="PROVIDE NAME PLEASE";
			}
			else{
				int status = keyManager.deletePolicy(policyname);
				if(status==200){
					message="POLICY DELETED SUCCESSFULLY";
				}else{
					message="ERROR!!! IN DELETING POLICY";
				}
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/inserted.jsp").forward(request, response);
		}
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/VaultPolicyCreate")){
			String policyjson = request.getParameter("policyjson");
			String policyname = request.getParameter("policyname");
			if(policyjson =="" || policyname ==""){
				message="PROVIDE JSON FORMAT POLICY/NAME";
			}
			else{
				int status = keyManager.createPolicy(policyjson,policyname);
				if(status==200){
					message="POLICY CREATED SUCCESSFULLY";
				}else{
					message="ERROR!!! IN CREATING POLICY";
				}
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/inserted.jsp").forward(request, response);
		}
		
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/AllPolicy")){
			List<String> allPolicies = new ArrayList<String>();
			allPolicies = keyManager.allPolicy();
			request.setAttribute("allPolicies", allPolicies);
			request.getRequestDispatcher("/allPolicy.jsp").forward(request, response);
			
		}
			
		
		
	}

}
