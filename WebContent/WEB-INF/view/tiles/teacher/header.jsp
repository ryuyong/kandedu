<%@ page language="java"%><%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="row hearderval" >
	<div class="col-md-12" style="text-align:right; font-weight: bold ;color: #ffffff">
		<strong>${sessionScope.teacher.teacher_nm}</strong>님
		<a class="btn btn-default" href="<c:url value="/alogout.do" />"><span class="kill_bl">로그아웃</span></a>
	</div>
</div>