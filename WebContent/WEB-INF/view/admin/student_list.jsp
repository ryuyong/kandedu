<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
});

function Search(pageIndex) {
	var frm_search = $('#frm_search')[0];
	
	var hakwon_cd = $('#hakwon_cd').val();
	if(hakwon_cd == '') {
		alert('학원을 선택해 주세요.');
		return;
	}
	
	frm_search.pageIndex.value = pageIndex;
	frm_search.submit();
}

function studentinsert() {
	var frm_insert = $('#frm_insert')[0];
	frm_insert.submit();
}

function studentupdate() {
	var frm = $('#frm_update')[0];
	var cnt = 0;
	var stud_cd = '';
	$("input[name=chk_stud_cd]").each(function() {
		if(this.checked) {
			cnt++;
			stud_cd = this.value;
		}
	});
	if(cnt == 0) {
		alert('수정할 학생을 선택해주세요');
		return;
	}
	if(cnt > 1) {
		alert('수정할 학생을 1명 선택해주세요');
		return;
	}
	frm.stud_cd.value = stud_cd;
	frm.submit();
}

function clinicupdate() {
	var frm = $('#frm_clinic')[0];
	var cnt = 0;
	var numcnt = 0;
	var stud_cds = '';
	var clinictypes = '';
	
	var checkvalue = false;
	var checknullvalue = false;
	
	$("input[name=chk_stud_cd]").each(function() {
		var clinictype = $("input[name=clinictype]").eq(numcnt).val();
		if(this.checked) {
			if(clinictype == '') {
				clinictype = 'NULL';
			}
			if(/[^A-Z]/g.test(clinictype)) {
				checkvalue = true;
			}
			cnt++;
			clinictypes += clinictype + ":::::";
			stud_cds += this.value + ":::::";
		}
		numcnt++;
	});
	if(cnt == 0) {
		alert('클리닉유형을 수정할 학생을 선택해주세요');
		return;
	}
	if(checknullvalue) {
		alert('미입력인 클리닉 유형이 있습니다.');
		return;
	}
	/*if(checkvalue) {
		alert('영문대문자가 아닌 클리닉 유형이 있습니다.');
		return;
	}*/

	if(confirm("클리닉 유형을 일괄 수정 하시겠습니까?")) {
		frm.stud_cds.value = stud_cds;
		frm.clinictypes.value = clinictypes;
		frm.submit();
	}
	
	
}

function allcheck() {
	$("input[name=chk_stud_cd]").each(function() {
		if(this.checked) {
			this.checked = false;
		} else {
			this.checked = true;
		}
		
	});
}

function studentdelete(stud_cds, use_yn) {
	var frm = $('#frm_delete')[0];
	//var cnt = 0;
	//var stud_cds = stud_cds;
	/*$("input[name=chk_stud_cd]").each(function() {
		if(this.checked) {
			cnt++;
			stud_cds = this.value+":::::";
		}
	});
	if(cnt == 0) {
		if(use_yn)
		alert('탈퇴할 학생을 선택해주세요');
		return;
	}*/
	if(use_yn == 'Y') {
		if(confirm("학생을 탈퇴취소시키겠습니까?")) {
			frm.stud_cds.value = stud_cds;
			frm.use_yn.value = use_yn;
			frm.submit();
		}
	}
	if(use_yn == 'N') {
		if(confirm("학생을 탈퇴시키겠습니까?")) {
			frm.stud_cds.value = stud_cds;
			frm.use_yn.value = use_yn;
			frm.submit();
		}
	}

}
</script>
<br/>
<p class="bg-primary bg-titleval">학생리스트</p>

<!-- state : end -->
<form class="form-inline"  id="frm_search" name="frm_search" method="post" action="<c:url value="/admin/student_list.do" />">
<input type="hidden" name="pageIndex" value="1" />
<input type="hidden" name="stud_cds" value="" />
	<div class="row searchval">
		<div class="col-md-12" style="background-color: #e0e0e0">
			<label for="hakwon_cd">학원:</label>
			<select class="form-control" name='hakwon_cd' id='hakwon_cd' >
    						<option value="">선택</option>
        			<c:forEach var="hakwonList" items="${hakwonList}" varStatus="status">
					<option value="${hakwonList.hakwon_cd}" <c:if test="${hakwonList.hakwon_cd eq StudentVo.hakwon_cd}">selected</c:if>>${hakwonList.hakwon_nm}</option>
				</c:forEach>
			</select>

			<label for="stud_nm">학생명</label>
			<input class="form-control" type="text" name="stud_nm" id="stud_nm" value="${StudentVo.stud_nm}" />
			
			<label for="stud_id">학생ID</label>
			<input class="form-control" type="text" name="stud_id" id="stud_id" value="${StudentVo.stud_id}" />
			
			<label for="hakneon">학년</label>
			<select class="form-control" name='hakneon' id='hakneon'>
    				<option value="">전체</option>
        			<option value="1" <c:if test="${StudentVo.hakneon eq '1'}">selected</c:if>>1학년</option>
        			<option value="2" <c:if test="${StudentVo.hakneon eq '2'}">selected</c:if>>2학년</option>
        			<option value="3" <c:if test="${StudentVo.hakneon eq '3'}">selected</c:if>>3학년</option>
			</select>
	    	<label for="use_yn">탈퇴여부</label>
	    	<select class="form-control" name='use_yn' id='use_yn'>
    						<option value="">전체</option>
    						<option value="Y" <c:if test="${ StudentVo.use_yn eq 'Y'}">selected</c:if>>회원</option>
    						<option value="N" <c:if test="${ StudentVo.use_yn eq 'N'}">selected</c:if>>탈퇴</option>
   			</select>
			<label for="pageUnit">건수</label>
			<select class="form-control" name="pageUnit">
				<option value="10" <c:if test="${StudentVo.pageUnit eq '10'}">selected</c:if>>10건</option>
				<option value="20" <c:if test="${StudentVo.pageUnit eq '20'}">selected</c:if>>20건</option>
				<option value="30" <c:if test="${StudentVo.pageUnit eq '30'}">selected</c:if>>30건</option>
				<option value="40" <c:if test="${StudentVo.pageUnit eq '40'}">selected</c:if>>40건</option>
				<option value="50" <c:if test="${StudentVo.pageUnit eq '50'}">selected</c:if>>50건</option>
			</select>
			<input type="button" value="검색" class="btn btn-default" onclick="javascript:Search(1)"/>
		</div>
	</div>
	<br/>	
	<div class="row">
		<div class="container col-xs-12">
			<table class="table table-bordered table-striped">
				<colgroup><col width="10%" /><col width="10%" /><col width="15%" /><col width="20%" /><col width="10%" /><col width="10%" /><col width="10%" /><col width="15%" /></colgroup>
				<thead>
					<tr>
						<th><a href="javascript:allcheck()">전체선택</a></th>
						<th>번호</th>
						<th>탈퇴여부</th>
						<th>학생명</th>
						<th>학생ID</th>
						<th>학년</th>
						<th>과목</th>
						<th>클리닉 유형</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty studentList}">
						<tr>
							<c:if test="${empty StudentVo.hakwon_cd}">
								<td colspan="8">학원을 선택후 검색버튼을 클릭해 주세요.</td>
							</c:if>
							<c:if test="${!empty StudentVo.hakwon_cd}">
								<td colspan="8">검색결과가 없습니다.</td>
							</c:if>
						</tr>
					</c:if>
					
					<c:if test="${!empty studentList}">
						<c:forEach var="studentList" items="${studentList}" varStatus="status">
							<tr>
								<td><input type="checkbox" name="chk_stud_cd" id="chk_stud_cd" value="${studentList.stud_cd}"/></td>
								<td>${studentList.rnum}</td>
								<td>
									<c:if test="${studentList.use_yn eq 'Y'}">
										회원
										<span class="sb_type_s"><a class="btn btn-primary" href="javascript:studentdelete('${studentList.stud_cd}','N')">탈퇴</a></span>
									</c:if>
									<c:if test="${studentList.use_yn eq 'N'}">
										탈퇴
										<span class="sb_type_s"><a class="btn btn-primary" href="javascript:studentdelete('${studentList.stud_cd}','Y')">탈퇴취소</a></span>
									</c:if>
								</td>
								<td>${studentList.stud_nm}</td>
								<td>${studentList.stud_id}</td>
								<td>${studentList.hakneon}</td>
								<td>${studentList.kwamok_nms}</td>
								<td><input type="text" name="clinictype" class="form-control"  id="clinictype" value="${studentList.clinictype}" maxlength="20" /></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="container col-xs-12">
			<a class="btn btn-primary" href="javascript:studentinsert()" role="button">등록</a>
			<a class="btn btn-primary" href="javascript:studentupdate()" role="button">수정</a>
			<a class="btn btn-danger" href="javascript:clinicupdate()" role="button">클리닉유형일괄수정</a>
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


<form id="frm_insert" name="frm_insert" method="post" action="<c:url value="/admin/student_insert.do" />">
</form>

<form id="frm_update" name="frm_update" method="post" action="<c:url value="/admin/student_update.do" />">
	<input type="hidden" name="stud_cd" value="" />
</form>

<form id="frm_clinic" name="frm_clinic" method="post" action="<c:url value="/admin/student_clinicupdate.do" />">
	<input type="hidden" name="stud_cds" value="" />
	<input type="hidden" name="clinictypes" value="" />
</form>

<form id="frm_delete" name="frm_delete" method="post" action="<c:url value="/admin/student_deleteafter.do" />">
	<input type="hidden" name="stud_cds" value="" />
	<input type="hidden" name="use_yn" value="" />
</form>