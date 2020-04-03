<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<style>
div .float-left, div .float-right{
	margin-left : 20px;
}
</style>
</head>
<body>
	<%@ include file="header.jsp"%>

	<div class="container">
		<div class="row">
			<h1 class="col-8">
				<spring:message code="lbl.shoppingcart.app.welcome" /> ${userName}! 
			</h1>
			
		</div>
		<div class="row">
			<table class="table table-hover" >
				<c:forEach var="product" items="${products}">
					<c:url var="addLink" value="/cart/addToCart">
						<c:param name="productId" value="${product.product_id}" />
					</c:url>
					<tr>
						<td>
								<div>							
									<div class="float-left"><span class="text-muted"><spring:message code="lbl.shoppingcart.product.name" /></span> <br/>${product.prodName}</div>
									<div class="float-left"><span class="text-muted"><spring:message code="lbl.shoppingcart.product.producttype" /></span> <br/>${product['class'].simpleName}</div>
									<c:choose>
									<c:when test="${product['class'].simpleName == 'Apparel'}" >
										<div class="float-left">
											<span class="text-muted"><spring:message code="lbl.shoppingcart.apparel.brand" /></span> <br/> ${product.brand}
										</div>
										<div class="float-left">
											<span class="text-muted"><spring:message code="lbl.shoppingcart.apparel.design" /></span> <br/> ${product.design}
										</div>
										<div class="float-left">
											<span class="text-muted"><spring:message code="lbl.shoppingcart.apparel.type" /></span> <br /> ${product.type}
										</div>
									</c:when>
									<c:otherwise>
										<div class="float-left  floatoffset">
											<span class="text-muted"><spring:message code="lbl.shoppingcart.book.author" /></span><br />${product.author}
										</div>
										<div class="float-left floatoffset">
											<span class="text-muted"><spring:message code="lbl.shoppingcart.book.genre" /></span> <br /> ${product.genre}
										</div>
										<div class="float-left floatoffset">
											<span class="text-muted"><spring:message code="lbl.shoppingcart.book.publications" /></span> <br/> ${product.publications}
										</div>
									</c:otherwise>
								</c:choose>
								<div class="float-right"><a class="btn btn-outline-primary" href="${addLink}"><spring:message code="lbl.shoppingcart.product.addtocart" /></a></div>
								<div class="float-right"><span class="text-muted"><spring:message code="lbl.shoppingcart.product.price" /></span> <br/>${product.price}</div>
									
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
		<%@ include file="footer.jsp"%>
</body>
</html>
