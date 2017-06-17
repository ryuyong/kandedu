<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
});

function Search(pageIndex) {
	var frm_search = $('#frm_search')[0];
	frm_search.pageIndex.value = pageIndex;
	frm_search.submit();
}

function teacherinsert() {
	var frm_insert = $('#frm_insert')[0];
	frm_insert.submit();
}

function teacherupdate() {
	var frm = $('#frm_update')[0];
	var cnt = 0;
	var teacher_cd = '';
	$("input[name=teacher_cd]").each(function() {
		if(this.checked) {
			cnt++;
			teacher_cd = this.value;
		}
	});
	if(cnt == 0) {
		alert('수정할 선생님을 선택해주세요');
		return;
	}
	frm.teacher_cd.value = teacher_cd;
	frm.submit();
}

function teacherdelete() {
	var frm = $('#frm_delete')[0];
	var cnt = 0;
	var teacher_cds = '';
	$("input[name=teacher_cd]").each(function() {
		if(this.checked) {
			cnt++;
			teacher_cds += this.value+":::::";
		}
	});
	if(cnt == 0) {
		alert('삭제할 선생님을 선택해주세요');
		return;
	}
	if(confirm("선생님을 삭제하겠습니까?")) {
		frm.teacher_cds.value = teacher_cds;
		frm.submit();
	}
}
</script>
<br/>
<p class="bg-primary bg-titleval">선생님리스트</p>

<form class="form-inline" id="frm_search" name="frm_search" method="post" action="<c:url value="/admin/teacher_list.do" />">
<input type="hidden" name="pageIndex" value="1" />
	<div class="row searchval">
		<div class="col-md-12" style="background-color: #e0e0e0">
			<label for="teacher_nm">선생님명:</label>
			<input class="form-control" type="text" name="teacher_nm" id="teacher_nm" value="${TeacherVo.teacher_nm}" />
			
			<input type="button" value="검색" class="btn btn-default" onclick="javascript:Search(1)"/>
		</div>
	</div>
	<br/>	
	<div class="row">
		<div class="container col-xs-12">
			<table class="table table-bordered table-striped">
				<colgroup><col width="5%" /><col width="5%" /><col width="10%" /><col width="20%" /><col width="20%" /><col width="20%" /><col width="20%" /></colgroup>
				<thead>
					<tr>
						<th>ㅁ</th>
						<th>번호</th>
						<th>선생님ID</th>
						<th>선생님PASS</th>
						<th>선생님명</th>
						<th>선생님핸드폰</th>
						<th>학원</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty teacherList}">
						<tr>
							<td colspan="7">검색결과가 없습니다.</td>
						</tr>
					</c:if>
					
					<c:if test="${!empty teacherList}">
						<c:forEach var="teacherList" items="${teacherList}" varStatus="status">
							<tr>
								<td><input type="checkbox" name="teacher_cd" id="teacher_cd" value="${teacherList.teacher_cd}"/></td>
								<td>${teacherList.rnum}</td>
								<td>${teacherList.teacher_id}</td>
								<td>${teacherList.teacher_pass}</td>
								<td>${teacherList.teacher_nm}</td>
								<td>${teacherList.ptel_no}</td>
								<td>${teacherList.hakwon_nms}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="container col-xs-12">
			<a class="btn btn-primary" href="javascript:teacherinsert()" role="button">등록</a>
			<a class="btn btn-primary" href="javascript:teacherupdate()" role="button">수정</a>
			<a class="btn btn-primary" href="javascript:teacherdelete()" role="button">삭제</a>
		</div>
	</div>
	<div class="row">
		<br/>
		<div class="container col-xs-12">
		<!-- 이전/다음페이지 없을 경우, 숨김처리-->
			${pageUtil}
		</div>
	</div>
</form>


<form id="frm_insert" name="frm_insert" method="post" action="<c:url value="/admin/teacher_insert.do" />">
</form>

<form id="frm_update" name="frm_update" method="post" action="<c:url value="/admin/teacher_update.do" />">
	<input type="hidden" name="teacher_cd" value="" />
</form>

<form id="frm_delete" name="frm_delete" method="post" action="<c:url value="/admin/teacher_deleteafter.do" />">
	<input type="hidden" name="teacher_cds" value="" />
	<input type="hidden" name="use_yn" value="N" />
</form>