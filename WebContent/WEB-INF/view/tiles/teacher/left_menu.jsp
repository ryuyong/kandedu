<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="col-md-2">
	<br/>
	<ul class="nav nav-sidebar">
		<c:if test="${menuId eq 'T00001'}">
			<li><a class="btn btn-primary" href="<c:url value="/teacher/answer_list.do" />">문제별정답리스트</a></li>
		</c:if>
		<c:if test="${menuId ne 'T00001'}">
			<li><a class="btn btn-default" href="<c:url value="/teacher/answer_list.do" />">문제별정답리스트</a></li>
		</c:if>
		<c:if test="${menuId eq 'T00002'}">
			<li><a class="btn btn-primary" href="<c:url value="/teacher/answersubmit_list.do" />">학생별제출리스트</a></li>
		</c:if>
		<c:if test="${menuId ne 'T00002'}">
			<li><a class="btn btn-default" href="<c:url value="/teacher/answersubmit_list.do" />">학생별제출리스트</a></li>
		</c:if>
	</ul>
</div>
