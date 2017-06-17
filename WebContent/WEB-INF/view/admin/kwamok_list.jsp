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

function kwamokinsert() {
	var frm_insert = $('#frm_insert')[0];
	frm_insert.submit();
}

function kwamokupdate() {
	var frm = $('#frm_update')[0];
	var cnt = 0;
	var kwamok_cd = '';
	$("input[name=kwamok_cd]").each(function() {
		if(this.checked) {
			cnt++;
			kwamok_cd = this.value;
		}
	});
	if(cnt == 0) {
		alert('수정할 과목을 선택해주세요');
		return;
	}
	frm.kwamok_cd.value = kwamok_cd;
	frm.submit();
}

function kwamokdelete() {
	var frm = $('#frm_delete')[0];
	var cnt = 0;
	var kwamok_cds = '';
	$("input[name=kwamok_cd]").each(function() {
		if(this.checked) {
			cnt++;
			kwamok_cds = this.value+":::::";
		}
	});
	if(cnt == 0) {
		alert('삭제할 과목을 선택해주세요');
		return;
	}
	if(confirm("과목을 삭제하겠습니까?")) {
		frm.kwamok_cds.value = kwamok_cds;
		frm.submit();
	}
}
</script>
<br/>
<p class="bg-primary bg-titleval">과목리스트</p>
<!-- state : end -->

<form class="form-inline" id="frm_search" name="frm_search" method="post" action="<c:url value="/admin/kwamok_list.do" />">
<input type="hidden" name="pageIndex" value="1" />
	<div class="row searchval">
		<div class="col-md-12" style="background-color: #e0e0e0">
			<label for="kwamok_nm">과목명:</label>
			<input class="form-control" type="text" name="kwamok_nm" id="kwamok_nm" value="${KwamokVo.kwamok_nm}" />
			
			<input type="button" value="검색" class="btn btn-default" onclick="javascript:Search(1)"/>
		</div>
	</div>
	<br/>	
	<div class="row">
		<div class="container col-xs-12">
			<table class="table table-bordered table-striped">
				<colgroup><col width="10%" /><col width="10%" /><col width="20%" /><col width="20%" /><col width="20%" /></colgroup>
				<thead>
					<tr>
						<th>ㅁ</th>
						<th>번호</th>
						<th>과목폴더</th>
						<th>과목명</th>
						<th>사용여부</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty kwamokList}">
						<tr>
							<td colspan="5">검색결과가 없습니다.</td>
						</tr>
					</c:if>
					
					<c:if test="${!empty kwamokList}">
						<c:forEach var="kwamokList" items="${kwamokList}" varStatus="status">
							<tr>
								<td><input type="checkbox" name="kwamok_cd" id="kwamok_cd" value="${kwamokList.kwamok_cd}"/></td>
								<td>${kwamokList.rnum}</td>
								<td>${kwamokList.kwamok_nm}</td>
								<td>${kwamokList.kwamok_folder_cd}</td>
								<td>${kwamokList.use_yn}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="container col-xs-12">
			<a class="btn btn-primary" href="javascript:kwamokinsert()" role="button">등록</a>
			<a class="btn btn-primary" href="javascript:kwamokupdate()" role="button">수정</a>
			<a class="btn btn-primary" href="javascript:kwamokdelete()" role="button">삭제</a>
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
<form id="frm_insert" name="frm_insert" method="post" action="<c:url value="/admin/kwamok_insert.do" />">
</form>

<form id="frm_update" name="frm_update" method="post" action="<c:url value="/admin/kwamok_update.do" />">
	<input type="hidden" name="kwamok_cd" value="" />
</form>

<form id="frm_delete" name="frm_delete" method="post" action="<c:url value="/admin/kwamok_deleteafter.do" />">
	<input type="hidden" name="kwamok_cds" value="" />
	<input type="hidden" name="use_yn" value="N" />
</form>