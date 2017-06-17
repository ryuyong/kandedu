<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
});

function insertstudent() {
	var frm_insert = $('#frm_insert')[0];
	
	var hakwon_cd = $('#hakwon_cd').val();
	if(hakwon_cd == '') {
		alert('학원을 선택해 주세요.');
		return;
	}
	
	
	var stud_id = $('#stud_id').val();
	if(stud_id == '') {
		alert('학생ID를 입력해 주세요.');
		return;
	}
	
	var stud_nm = $('#stud_nm').val();
	if(stud_nm == '') {
		alert('학생명를 입력해 주세요.');
		return;
	}
	
	var hakneon = $('#hakneon').val();
	if(hakneon == '') {
		alert('학년을 선택해 주세요.');
		return;
	}
	
	/*
	var clinictype = $('#clinictype').val();
	if(clinictype != '') {
		if(/[^A-Z]/g.test(clinictype)) {
			alert('클리닉 유형은 영문대문자로 입력해 주세요.');
			return;
		}
	}*/
	
	var cnt = 0;
	var kwamoks = '';
	$("input[name=kwamok]").each(function() {
		if(this.checked) {
			cnt++;
			kwamoks += this.value+":::::";
		}
	});
	if(cnt == 0) {
		alert('과목을  선택해주세요');
		return;
	}
	
	
	if(confirm("학생을 등록하겠습니까?")) {
		frm_insert.kwamoks.value = kwamoks;
		frm_insert.target = "summitframe";
		frm_insert.submit();
	}
}
</script>
<br/>
<p class="bg-primary bg-titleval">학생등록</p>

<form id="frm_insert" name="frm_insert" method="post" action="<c:url value="/admin/student_insertafter.do" />">
<input type="hidden" name="use_yn" value="Y" />
<input type="hidden" name="kwamoks" value="" />
	<div class="row">
		<div class="container col-xs-12">
			<table class="table table-bordered">
				<colgroup><col width="15%" /><col width="85%" /></colgroup>
				<tbody>
					<tr>
						<th scope="row">학원선택</th>
						<td>
							<select name='hakwon_cd' id='hakwon_cd' class="form-control">
		      						<option value="">선택</option>
			         			<c:forEach var="hakwonList" items="${hakwonList}" varStatus="status">
									<option value="${hakwonList.hakwon_cd}">${hakwonList.hakwon_nm}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">학생ID</th>
						<td>
							<input type="text" name="stud_id" id="stud_id" maxlength="16" onkeyup="checkHan(this)" class="form-control" placeholder="학생ID">
						</td>
					</tr>
					<tr>
						<th scope="row">학생명</th>
						<td>
							<input type="text" name="stud_nm" id="stud_nm" maxlength="16" class="form-control" placeholder="학생명">
						</td>
					</tr>
					<tr>
						<th scope="row">클리닉 유형</th>
						<td>
							<input type="text" name="clinictype" id="clinictype" value="" maxlength="20" class="form-control" placeholder="클리닉 유형" />
						</td>
					</tr>
					<tr>
						<th scope="row">학교</th>
						<td>
							<input type="text" name="school" id="school" value="" maxlength="50" class="form-control" placeholder="학교" />
						</td>
					</tr>
					<tr>
						<th scope="row">전화번호</th>
						<td>
							<input type="text" name="tel_no" id="tel_no" value="" maxlength="20" class="form-control" placeholder="전화번호" />
						</td>
					</tr>
					<tr>
						<th scope="row">부모님전화번호</th>
						<td>
							<input type="text" name="ptel_no" id="ptel_no" value="" maxlength="20" class="form-control" placeholder="부모님전화번호" />
						</td>
					</tr>
					<tr>
						<th scope="row">학년</th>
						<td>
							<select name='hakneon' id='hakneon' class="form-control">
	       						<option value="">선택</option>
			         			<option value="1">1학년</option>
			         			<option value="2">2학년</option>
			         			<option value="3">3학년</option>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">과목</th>
						<td>
							<c:forEach var="kwamokList" items="${kwamokList}" varStatus="status">
								<input type="checkbox" name="kwamok" id="kwamok" value="${kwamokList.kwamok_cd}"/>${kwamokList.kwamok_nm}&nbsp;
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th scope="row">비고</th>
						<td>
							<textarea name="bigo" id="bigo" cols="70" rows="10" class="form-control"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="form-group">
				<a class="btn btn-primary" href="javascript:insertstudent()" role="button">등록</a>
	        </div>
		</div>
	</div>
</form>
