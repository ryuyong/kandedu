<%@ page language="java"%><%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
<script>
$(document).ready(function(){
});
</script>

<div class="col-md-12 hearderval" style="text-align:right; font-weight: bold ;color: #ffffff">
	<div class="row">
		<div class="col-md-12">
			<strong>${sessionScope.stud.stud_nm}</strong>님
			<a class="btn btn-default" href="javascript:gologout();" role="button">로그아웃</a>	    
		</div>
	</div>
</div>
