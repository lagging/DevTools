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

<script>
function validateForm1() {
    var firstname = document.forms["modifyuser"]["name1"].value;
    var password = document.getElementById("txtpassword").value;
    var confirmPassword = document.getElementById("txtconfirmpassword").value;
    var username = document.getElementById("username1").value;
    var email = document.getElementById("email").value;
    
    if(username != email) {
    	 alert("email should be same as username");
         return false;
    }
    if (password != confirmPassword) {
        alert("Passwords do not match.");
        return false;
    }
    
    if (firstname == null || firstname == "") {
        alert("Fill all the fields");
        return false;
    }
    var lastname = document.forms["modifyuser"]["name2"].value;
    if (lastname == null || lastname == "") {
        alert("Fill all the fields");
        return false;
    }
    var email = document.forms["modifyuser"]["email"].value;
    if (email == null || email == "") {
        alert("Fill all the fields");
        return false;
    }
    var empid = document.forms["modifyuser"]["empid"].value;
    if (empid == null || empid == "") {
        alert("Fill all the fields");
        return false;
    }
    var teamname = document.forms["modifyuser"]["teamname1"].value;
    if (teamname == null || teamname == "") {
        alert("Fill all the fields");
        return false;
    }
    var username = document.forms["modifyuser"]["username1"].value;
    if (username == null || username == "") {
        alert("Fill all the fields");
        return false;
    }
    var password = document.forms["modifyuser"]["password1"].value;
    if (password == null || password == "") {
        alert("Fill all the fields");
        return false;
    }
    var repassword = document.forms["modifyuser"]["password2"].value;
    if (repassword == null || repassword == "") {
        alert("Fill all the fields");
        return false;
    }
    
    
    /*else return true; */
}


</script>

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
		<h1 class="well">Modify User Details</h1>
		<div class="col-lg-12 well">
			<div class="row">
				<form name="modifyuser" method="post" action="UserModify" onsubmit="return validateForm1()">
				
					<div class="col-sm-12">
						<div class="form-group">
							<label>FirstName</label> <input type="text"
								placeholder="Enter First Name.." name="name1" class="form-control">
						</div>
						<div class="form-group">
							<label>LastName</label> <input type="text"
								placeholder="Enter Last Name.." name="name2" class="form-control">
						</div>
						<div class="form-group">
							<label>Email</label> <input type="text"
								placeholder="Enter Email.." name="email" id="email" class="form-control">
						</div>
						<div class="form-group">
							<label>EmployeeId</label> <input type="text"
								placeholder="Enter EmployeeId.." name="empid"  class="form-control">
						</div>
						<div class="form-group">
							<label>Username</label> <input type="text"
								placeholder="Enter Username.." name="username1" id="username1"  class="form-control">
						</div>
						<div class="form-group">
							<label>Teamname</label> <input type="text"
								placeholder="Enter Username.." name="teamname1"  class="form-control">
						</div>
						<div class="form-group">
							<label>Password</label> <input type="password"
								placeholder="Enter password.." name="password1" id="txtpassword" class="form-control">
						</div>
						<div class="form-group">
							<label>Re-password</label> <input type="password"
								placeholder="Enter re-password.." name="password2" id="txtconfirmpassword" class="form-control">
						</div>
						<label>Policy</label> 
						<div class="radio">
						  <label><input type="radio" name="policy1" value="masterAdmin">MasterAdmin</label>
						</div>
						<div class="radio">
						  <label><input type="radio" name="policy1" value="readKeyOnly" checked>ReadKeyOnly</label>
						</div>
						<div class="radio">
						  <label><input type="radio" name="policy1" value="readKeyValue">ReadKeyValue</label>
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