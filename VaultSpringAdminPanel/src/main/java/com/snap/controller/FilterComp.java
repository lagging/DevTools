package com.snap.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.securitymanager.connection.KeyManager;
import com.securitymanager.connection.KeyManagerConnectionException;
import com.securitymanager.service.DBController;

/**
 * Servlet implementation class FilterComponent
 */
public class FilterComp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(FilterComp.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilterComp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		KeyManager keyManager = new KeyManager();
		DBController dbController;
		String message;
		try {
			keyManager.authenticate();
		} catch (KeyManagerConnectionException e) {
			// TODO Auto-generated catch block
			logger.error("KeyManagerConnectionException",e);
		}
		
		if(request.getRequestURI().substring(request.getContextPath().length()).equals("/DeleteComponent")){
			
			if(keyManager.deleteComponent(request.getParameter("component")) == 200){
				dbController = new DBController();
				dbController.deletePath(request.getParameter("component"));
				message = "Component Deleted Successfully";
			}
			else{
				message = "Error Occured While Deleting Component";
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/inserted.jsp").forward(request, response);
			
		}
	}

}
