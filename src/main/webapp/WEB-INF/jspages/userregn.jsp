<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PETS APP</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/contents/csspages/stylepage.css">
</head>
<body>
	<h3>PET PEERS</h3>
	<jsp:include page="menu.jsp" />
	<form method="get">
		<br>
		<div class="imgcontainer">
			<img
				src="${pageContext.request.contextPath}/contents/images/puppy.jpg"
				alt="Dogimage" class="avatar">
		</div>
		<p style="color: red; text-align: center;">${nouser}</p>
		<div class="container">
			<form:form method="get" modelAttribute="user">
				<label>Enter Username: </label>
				<td><form:input path="username" placeholder="Enter Username"
						required="required" /></td>
				<form:errors path="username" cssClass="error" />
				<br>
				<label>Enter Password: </label>
				<form:password path="userPassword" placeholder="Enter Password"
					required="required" />
				<form:errors path="userPassword" cssClass="error" />
				<br>
				<label>Confirm Password:</label>
				<form:password path="confirmPassword" placeholder="Confirm Password"
					required="required" />
				<p style="color: red; text-align: center;">${nomatch}</p>
				<button type="submit" formaction="./add">Submit</button>
			</form:form>
		</div>
	</form>

</body>
</html>