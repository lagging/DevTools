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
	You are not logged in
	<br />
	<a href="index.jsp">Please Login</a>
	<%
		} else if ((session.getAttribute("userpolicy").equals("readKeyOnly"))
				|| (session.getAttribute("userpolicy").equals("readKeyValue"))) {
	%>
	You are not allowed
	<br />
	<%
		} else {
	%>
	Welcome
	<%=session.getAttribute("userid")%>

	<form method="post" action="VaultPolicyCreate">
		<center>
			PolicyName:
			<input type="text" name="policyname" value="" />
			<br/>
			<table border="1" width="30%" cellpadding="3">
				<thead>
					<tr>
						<th colspan="2">Enter JSON format policy</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
						<textarea rows="5" cols="30"
								style="margin: 0px; width: 508px; height: 213px;"
								name="policyjson"></textarea>
						</td>
					</tr>
					<tr>
						<td><input type="submit" value="create" /></td>
					</tr>

				</tbody>
			</table>
		</center>
	</form>
	<a href="http://jsonlint.com/" target="_blank"> json-validator</a><br/>
	<a href="samplePolicy.jsp" target="_blank"> sample_json</a>
	<%
		}
	%>

	<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type='text/javascript' src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>