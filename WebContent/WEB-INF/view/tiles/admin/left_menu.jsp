<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="col-md-2">
	<br/>
	<ul class="nav nav-sidebar">
		<c:if test="${menuId eq 'A00001'}">
			<li><a class="btn btn-primary" href="<c:url value="/admin/hakwon_list.do" />">학원관리</a></li>
		</c:if>
		<c:if test="${menuId ne 'A00001'}">
			<li><a class="btn btn-default" href="<c:url value="/admin/hakwon_list.do" />">학원관리</a></li>
		</c:if>
		<c:if test="${menuId eq 'A00008'}">
			<li><a class="btn btn-primary" href="<c:url value="/admin/kwamok_list.do" />">과목관리</a></li>
		</c:if>	
		<c:if test="${menuId ne 'A00008'}">
			<li><a class="btn btn-default" href="<c:url value="/admin/kwamok_list.do" />">과목관리</a></li>
		</c:if>
		<c:if test="${menuId eq 'A00002'}">
			<li><a class="btn btn-primary" href="<c:url value="/admin/teacher_list.do" />">선생님관리</a></li>
		</c:if>
		<c:if test="${menuId ne 'A00002'}">
			<li><a class="btn btn-default" href="<c:url value="/admin/teacher_list.do" />">선생님관리</a></li>
		</c:if>
		<c:if test="${menuId eq 'A00003'}">
			<li><a class="btn btn-primary" href="<c:url value="/admin/student_list.do" />">학생관리</a></li>
		</c:if>
		<c:if test="${menuId ne 'A00003'}">
			<li><a class="btn btn-default" href="<c:url value="/admin/student_list.do" />">학생관리</a></li>
		</c:if>
		<c:if test="${menuId eq 'A00004'}">
			<li><a class="btn btn-primary" href="<c:url value="/admin/answer_list.do" />">문제별정답리스트</a></li>
		</c:if>
		<c:if test="${menuId ne 'A00004'}">
			<li><a class="btn btn-default" href="<c:url value="/admin/answer_list.do" />">문제별정답리스트</a></li>
		</c:if>
		<c:if test="${menuId eq 'A00005'}">
			<li><a class="btn btn-primary" href="<c:url value="/admin/answersubmit_list.do" />">학생별제출리스트</a></li>
		</c:if>
		<c:if test="${menuId ne 'A00005'}">
			<li><a class="btn btn-default" href="<c:url value="/admin/answersubmit_list.do" />">학생별제출리스트</a></li>
		</c:if>
		<c:if test="${menuId eq 'A00006'}">
			<li><a class="btn btn-primary" href="<c:url value="/admin/wrongnote_make.do" />">오답노트생성</a></li>
		</c:if>
		<c:if test="${menuId ne 'A00006'}">
			<li><a class="btn btn-default" href="<c:url value="/admin/wrongnote_make.do" />">오답노트생성</a></li>
		</c:if>
		<c:if test="${menuId eq 'A00007'}">
			<li><a class="btn btn-primary" href="<c:url value="/admin/clinic_make.do" />">클리닉생성</a></li>
		</c:if>
		<c:if test="${menuId ne 'A00007'}">
			<li><a class="btn btn-default" href="<c:url value="/admin/clinic_make.do" />">클리닉생성</a></li>
		</c:if>
	</ul>
</div>
