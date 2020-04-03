<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<link href="shoppingcart.jpg" rel="icon"/>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
<title><spring:message code="lbl.shoppingcart.app.title" /></title>

</head>
<body>
	<%@ include file="header.jsp"%>

	<div class="container">
		<div class="row">
			<div class="jumbotron">
				<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
					<h1 class="display-4">
						<spring:message code="lbl.shoppingcart.app.title" />
					</h1><br /><br />
					<c:if test="${param.error ne null}">
			<div style="color: red"><spring:message code="lbl.shoppingcart.login.invalidcredentials" /></div>
		</c:if>
		<form action="/user/setpassword" method="post">
			<div class="form-group">
				<label for="username"><spring:message code="lbl.shoppingcart.login.username" /></label> <input type="text"
					class="form-control" id="username" name="username" value="${username}" readonly>
			</div>
			<div class="form-group">
				<label for="pwd"><spring:message code="lbl.shoppingcart.login.password" /></label> <input type="password"
					class="form-control" id="password" name="password">
			</div>
			
			<div class="form-group">
				<label for="pwd"><spring:message code="lbl.shoppingcart.login.confirmpassword" /></label> <input type="password"
					class="form-control" id="confirm_password" name="password">
					<span id='message'></span>
			</div>

			<button type="submit" class="btn btn-success" id="submit" disabled><spring:message code="lbl.shoppingcart.login.submit" /></button>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
				</div>
			</div>
		</div>

	</div>
	<%@ include file="footer.jsp"%>
</body>
<script>
$('#password, #confirm_password').on('keyup', function () {
	  if ($('#password').val() == $('#confirm_password').val()) {
	    $('#message').html('Matching').css('color', 'green');
	    $("#submit").removeAttr("disabled");
	  } else{ 
	    $('#message').html('Not Matching').css('color', 'red');
	  	$("#submit").attr("disabled","");
	  }
	});
</script>
</html>
