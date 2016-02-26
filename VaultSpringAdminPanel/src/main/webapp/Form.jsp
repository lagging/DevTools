<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/style1.css" type="text/css" media="all" />
 <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="bodyHeader.jsp"></jsp:include>


	<div class="container">
		<h1 class="well">Enter Key Value</h1>
		<div class="col-lg-12 well">
			<div class="row">
				<form>
				
					<div class="col-sm-12">
						<label>Component</label>
						<select class="form-control" name="path">
						    <option>--select-path</option>
						    <c:forEach items="${allPaths}" var="path">
								<option><c:out value="${path}" /></option>
							</c:forEach>
						</select>
					
						<div class="form-group">
							<label>Key</label> <input type="text"
								placeholder="Enter Key Here.." name="key" class="form-control">
						</div>
						<div class="form-group">
							<label>Value</label> <input type="text"
								placeholder="Enter Value Here.." name="value" class="form-control">
						</div>
						<button type="button" class="btn btn-lg btn-info">Insert</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type='text/javascript' src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	
</body>
</html>