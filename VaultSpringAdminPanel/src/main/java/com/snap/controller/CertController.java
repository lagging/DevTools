package com.snap.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.securitymanager.connection.DbPropertyValues;

/**
 * Servlet implementation class CertController
 */

public class CertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		DbPropertyValues configValues = new DbPropertyValues();
		String path = configValues.getCertFolderPath();
		String cn = request.getParameter("cn");
		String name = request.getParameter("certsname");
		String certpassword = request.getParameter("certpassword");
		
		String cmds = "openssl req -new -newkey rsa:2048 -nodes -out "+path+name+".csr -keyout "+path+name+".key -subj /C=IN/ST=haryana/L=gurgaon/O=snapdeal/OU=devops/CN="+cn+" && openssl x509 -req -in "+path+name+".csr -signkey "+path+name+".key -out "+path+name+".crt -days 9999 && openssl pkcs12 -export -in "+path+name+".crt -inkey "+path+name+".key -out "+path+name+".p12 -name "+certpassword+" -passin pass:"+certpassword+" -passout pass:"+certpassword;
		Process p = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", cmds});
		String message = "check your directory "+path;
		request.setAttribute("message", message);
		request.getRequestDispatcher("/inserted.jsp").forward(request, response);;
		
		
	}

}
