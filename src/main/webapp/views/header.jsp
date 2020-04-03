  <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
	<a class="navbar-brand col-2" href="#"><spring:message code="lbl.shoppingcart.app.header.title" /></a>
	<ul class="navbar-nav col-6">
		<li class="nav-item active">
    		a class="nav-link" href="/home"><spring:message code="lbl.shoppingcart.appheader.home" /></a>
    	</li>
    	<li class="nav-item active">
      		<a class="nav-link" href="#"><spring:message code="lbl.shoppingcart.appheader.myorders" /></a>
    	</li>
  	</ul>
  	<span class="col-2"> <a href="/cart/MyCart" class="btn btn-outline-info"><spring:message code="lbl.shoppingcart.appheader.mycart" />(${cartSize})</a></span>
	<c:if test="${empty userName}">
		<span class="col-1 float-right"> <a href="/login" class="btn btn-outline-warning"><spring:message code="lbl.shoppingcart.appheader.login" /></a></span>
		<span class="col-2 float-right"> <a href="/user/registration" class="btn btn-outline-warning"><spring:message code="lbl.shoppingcart.appheader.signup" /></a></span>
	</c:if>
  	<c:if test="${not empty userName}">
		<span class="col-2 float-right"> <a href="/logout" class="btn btn-outline-warning"><spring:message code="lbl.shoppingcart.appheader.logout" />(${userName})</a></span>
	</c:if>
</nav>