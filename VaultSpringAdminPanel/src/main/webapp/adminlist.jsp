<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >

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
	<div class="container" align="center">
	  <h2>Secret in Your Hand</h2>                  
	  <img src="images/vault.jpeg" class="img-responsive" alt="Cinque Terre" width="700" height="236"> 
	</div>
	

	<%
    }
%>
	<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type='text/javascript' src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</body>
</html>