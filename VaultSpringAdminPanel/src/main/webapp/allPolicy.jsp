<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/style1.css" type="text/css" media="all" />
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
</head>
<body>
	
	<jsp:include page="bodyHeader.jsp"></jsp:include>
	<%
		if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "") ) {
		
	%>
	You are not logged in
	<br />
	<a href="index.jsp">Please Login</a>
	<%
		} else if((session.getAttribute("userpolicy").equals("readKeyOnly")) || (session.getAttribute("userpolicy").equals("readKeyValue"))){
	%>
	You are not allowed
	<br />
	<%			
			} else{
	%>
	Welcome
	<div class="centered">
		<table class="keyvaluetable"
			style="background-repeat: no-repeat; width: 450px; margin: 0;"
			cellpadding="0" cellspacing="0" border=1px>
			<tr>
				<th>Policy</th>
			</tr>

			<c:forEach items="${allPolicies}" var="policy">
				<tr>
					<td><c:out value="${policy}" /></td>
				</tr>
			</c:forEach>



		</table>
	</div>



	<%
    }
%>
	<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type='text/javascript' src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</body>
</html>