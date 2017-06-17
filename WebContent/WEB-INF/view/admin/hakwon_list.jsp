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

function hakwoninsert() {
	var frm_insert = $('#frm_insert')[0];
	frm_insert.submit();
}

function hakwonupdate() {
	var frm = $('#frm_update')[0];
	var cnt = 0;
	var hakwon_cd = '';
	$("input[name=hakwon_cd]").each(function() {
		if(this.checked) {
			cnt++;
			hakwon_cd = this.value;
		}
	});
	if(cnt == 0) {
		alert('수정할 학원을 선택해주세요');
		return;
	}
	frm.hakwon_cd.value = hakwon_cd;
	frm.submit();
}

function hakwondelete() {
	var frm = $('#frm_delete')[0];
	var cnt = 0;
	var hakwon_cds = '';
	$("input[name=hakwon_cd]").each(function() {
		if(this.checked) {
			cnt++;
			hakwon_cds = this.value+":::::";
		}
	});
	if(cnt == 0) {
		alert('삭제할 학원을 선택해주세요');
		return;
	}
	if(confirm("학원을 삭제하겠습니까?")) {
		frm.hakwon_cds.value = hakwon_cds;
		frm.submit();
	}
}
</script>
<br/>
<p class="bg-primary bg-titleval">학원리스트</p>
<!-- state : end -->

<form class="form-inline" id="frm_search" name="frm_search" method="post" action="<c:url value="/admin/hakwon_list.do" />">
<input type="hidden" name="pageIndex" value="1" />
	<div class="row searchval">
		<div class="col-md-12" style="background-color: #e0e0e0">
			<label for="hakwon_nm">학원명:</label>
			<input class="form-control" type="text" name="hakwon_nm" id="hakwon_nm" value="${HakwonVo.hakwon_nm}" />
			
			<input type="button" value="검색" class="btn btn-default" onclick="javascript:Search(1)"/>
		</div>
	</div>
	<br/>	
	<div class="row">
		<div class="container col-xs-12">
			<table class="table table-bordered table-striped">
				<colgroup><col width="10%" /><col width="10%" /><col width="80%" /></colgroup>
				<thead>
					<tr>
						<th>ㅁ</th>
						<th>번호</th>
						<th>학원명</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty hakwonList}">
						<tr>
							<td colspan="3">검색결과가 없습니다.</td>
						</tr>
					</c:if>
					
					<c:if test="${!empty hakwonList}">
						<c:forEach var="hakwonList" items="${hakwonList}" varStatus="status">
							<tr>
								<td><input type="checkbox" name="hakwon_cd" id="hakwon_cd" value="${hakwonList.hakwon_cd}"/></td>
								<td>${hakwonList.rnum}</td>
								<td>${hakwonList.hakwon_nm}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="container col-xs-12">
			<a class="btn btn-primary" href="javascript:hakwoninsert()" role="button">등록</a>
			<a class="btn btn-primary" href="javascript:hakwonupdate()" role="button">수정</a>
			<a class="btn btn-primary" href="javascript:hakwondelete()" role="button">삭제</a>
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
<form id="frm_insert" name="frm_insert" method="post" action="<c:url value="/admin/hakwon_insert.do" />">
</form>

<form id="frm_update" name="frm_update" method="post" action="<c:url value="/admin/hakwon_update.do" />">
	<input type="hidden" name="hakwon_cd" value="" />
</form>

<form id="frm_delete" name="frm_delete" method="post" action="<c:url value="/admin/hakwon_deleteafter.do" />">
	<input type="hidden" name="hakwon_cds" value="" />
	<input type="hidden" name="use_yn" value="N" />
</form>