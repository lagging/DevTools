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
<script type="text/javascript">
function validateForm() {
    var password = document.getElementById("password1").value;
    var confirmPassword = document.getElementById("password2").value;
    if (password != confirmPassword) {
        alert("Passwords do not match.");
        return false;
    }

</script>
</head>
<body>
	<div class="container">
		<h1 class="well">Enter Username</h1>
		<div class="col-lg-12 well">
			<div class="row">
				<form name="myform" method="post" action="UserResetPassword" onsubmit="return validateForm()">
					<div class="col-sm-12">

						<div class="form-group">
							<label>Username</label> <input type="text"
								placeholder="Enter Username Here.." name="username1" class="form-control">
						</div>
						<div class="form-group">
							<label>Password</label> <input type="password"
								placeholder="Enter Password Here.." name="password1" id="password1" class="form-control">
						</div>
						<div class="form-group">
							<label>Re-password</label> <input type="password"
								placeholder="Re-confirm password Here.." name="password2" id="password2" class="form-control">
						</div>
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	

	<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type='text/javascript' src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>


</body>
</html>