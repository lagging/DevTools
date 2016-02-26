<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
</head>
<body>
	<jsp:include page="bodyHeader.jsp"></jsp:include>
	<%
		if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
	%>
	You are not logged in
	<br />
	<a href="index.jsp">Please Login</a>
	<%
		} else {
	%>
	Welcome
	<%=session.getAttribute("userid")%>
	<br />
	<br />


	<div class="centered">
		<table class="keyvaluetable" style=" background-repeat:no-repeat; width:450px;margin:0;" cellpadding="0" cellspacing="0" border=1px>
		<tr>
		    <th>Key</th>
		    <th>Value</th>
		</tr>
		
		<c:forEach items="${keyValue}" var="entry">
				<tr>
					<td>${entry.key}</td>
				<% 
					if( (session.getAttribute("userpolicy").equals("readKeyValue")) || (session.getAttribute("userpolicy").equals("masterAdmin"))){
				%>
					<td>${entry.value}</td>
				<% 
					}
				%>
				</tr>
		</c:forEach>
		
		</table>
	</div>

	<%
		}
	%>


</body>
</html>