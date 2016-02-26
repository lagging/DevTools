<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
		%>
		You are not logged in <br /> 
		<a href="index.jsp">Please Login</a>
		<%
			} else if((session.getAttribute("userpolicy").equals("readKeyOnly")) || (session.getAttribute("userpolicy").equals("readKeyValue"))){
		%>
		You are not allowed
		<br />
		<%
			} else {
		%>
		Welcome
		<%=session.getAttribute("userid")%>

		<div class="container">
		<h1 class="well">Delete User</h1>
		<div class="col-lg-12 well">
			<div class="row">
				<form method="post" action="UserDelete">
				
					<div class="col-sm-12">
					
						<div class="form-group">
							<label>UserName</label> <input type="text"
								placeholder="Enter Username.." name="username1" class="form-control">
						</div>
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form>
			</div>
		</div>
	</div>
		
		<%
    }
%>
	<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type='text/javascript' src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</body>
</html>