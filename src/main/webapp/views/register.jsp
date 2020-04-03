<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
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

        <form:form method="POST" modelAttribute="user" class="form-signin">
            <h2 class="form-signin-heading">Create your account</h2>
            <spring:bind path="name">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="name" class="form-control" placeholder="Name"
                                autofocus="true"></form:input>
                    <form:errors class="text-danger" path="name"></form:errors>
                </div>
            </spring:bind>
            <spring:bind path="gID">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                
                    <form:input type="text" path="gID" class="form-control" placeholder="gID"
                                autofocus="true"></form:input>
                    <form:errors class="text-danger" path="gID"></form:errors>
                </div>
            </spring:bind>
            <spring:bind path="email">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="email" class="form-control" placeholder="Email"
                                autofocus="true"></form:input>
                    <form:errors class="text-danger" path="email"></form:errors>
                </div>
            </spring:bind>
            <%-- <spring:bind path="isActive">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:checkbox path="isActive" class="form-control" placeholder="isActive"
                                autofocus="true" label="isActive"></form:checkbox>
                    <form:errors class="text-danger" path="isActive"></form:errors>
                </div>
            </spring:bind> --%>
            <spring:bind path="isSuperAdmin">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:checkbox path="isSuperAdmin" class="form-control" placeholder="isSuperAdmin"
                                autofocus="true" label="isSuperAdmin"></form:checkbox>
                    <form:errors class="text-danger" path="isSuperAdmin"></form:errors>
                </div>
            </spring:bind>
            <spring:bind path="isAdmin">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:checkbox path="isAdmin" class="form-control" placeholder="isAdmin"
                                autofocus="true" label="isAdmin"></form:checkbox>
                    <form:errors class="text-danger" path="isAdmin"></form:errors>
                </div>
            </spring:bind>
            <spring:bind path="isCandidate">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:checkbox path="isCandidate" class="form-control" placeholder="isCandidate"
                                autofocus="true" label="isCandidate" ></form:checkbox>
                    <form:errors class="text-danger" path="isActive"></form:errors>
                </div>
            </spring:bind>
            <spring:bind path="userName">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="userName" class="form-control" placeholder="User Name"
                                autofocus="true" readonly="true"></form:input>
                    <form:errors class="text-danger" path="userName"></form:errors>
                </div>
            </spring:bind>

  
            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form:form>

    </div>
<%@ include file="footer.jsp"%>

  </body>
  <script type="text/javascript">

 	$("#gID").on('keyup',function(){
 		var text = $("#gID").val();
 	 	$("#userName").val(text);
 	 	});
  
  </script>
</html>