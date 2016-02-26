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
function myFunction() {
    var pass1 = document.getElementById("pass1").value;
    var pass2 = document.getElementById("pass2").value;
    var ok = true;
    if (pass1 != pass2) {
        alert("Passwords Do not match");
        ok = false;
    }
    return ok;
}
</script>
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
	<%=session.getAttribute("userid")%>
	
	
	<div class="container">
		<h1 class="well">Certificate Generation</h1>
		<div class="col-lg-12 well">
			<div class="row">
				<form name="certform" method="post" action="Cert" onsubmit="return myFunction()" >
					<div class="col-sm-12">
						
						<div class="form-group">
							<label>CN</label> <input type="text"
								placeholder="Enter Common Name(e.g ims.snapdeal.com)" name="cn" class="form-control">
						</div>
						<div class="form-group">
							<label>CertsName</label> <input type="text"
								placeholder="certificates name(e.g ims_prod)" name="certsname" class="form-control">
						</div>
						<div class="form-group">
							<label>Password</label> <input type="password"
								placeholder="password to encrypt in PKCS12.." name="certpassword" id="pass1" class="form-control">
						</div>
						<div class="form-group">
							<label>Re-Password</label> <input type="password"
								placeholder="confirm password to encrypt " name="recertpassword" id="pass2" class="form-control">
						</div>
						<button type="submit" class="btn btn-primary">Generate</button>
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