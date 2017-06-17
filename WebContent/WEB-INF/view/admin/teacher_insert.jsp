<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
});

function insertteacher() {
	var frm_insert = $('#frm_insert')[0];
	
	var teacher_id = $('#teacher_id').val();
	if(teacher_id == '') {
		alert('선생님ID를 입력해 주세요.');
		return;
	}
	
	var teacher_pass = $('#teacher_pass').val();
	if(teacher_pass == '') {
		alert('선생님PASS를 입력해 주세요.');
		return;
	}
	
	var teacher_nm = $('#teacher_nm').val();
	if(teacher_nm == '') {
		alert('선생님명을 입력해 주세요.');
		return;
	}
	
	var cnt = 0;
	var hakwon_cds = '';
	$("input[name=hakwon_cd]").each(function() {
		if(this.checked) {
			cnt++;
			hakwon_cds += this.value+":::::";
		}
	});
	if(cnt == 0) {
		alert('학원을  선택해주세요');
		return;
	}
	
	
	if(confirm("선생님을 등록하겠습니까?")) {
		frm_insert.hakwon_cds.value = hakwon_cds;
		frm_insert.target = "summitframe";
		frm_insert.submit();
	}
}
</script>
<br/>
<p class="bg-primary bg-titleval">선생님등록</p>
<!-- state : end -->
<form id="frm_insert" name="frm_insert" method="post" action="<c:url value="/admin/teacher_insertafter.do" />">
<input type="hidden" name="use_yn" value="Y" />
<input type="hidden" name="hakwon_cds" value="" />
	<div class="row">
		<div class="container col-xs-12">
			<table class="table table-bordered">
				<colgroup><col width="15%" /><col width="85%" /></colgroup>
				<tbody>
					<tr>
						<th>선생님ID</th>
						<td>
							<input type="text" id="teacher_id" name="teacher_id" maxlength="16" class="form-control" placeholder="선생님ID" />
						</td>
					</tr>
					<tr>
						<th scope="row">선생님PASS</th>
						<td>
							<input type="text" name="teacher_pass" id="teacher_pass" maxlength="16" class="form-control" placeholder="선생님PASS">
						</td>
					</tr>
					<tr>
						<th scope="row">선생님명</th>
						<td>
							<input type="text" name="teacher_nm" id="teacher_nm" maxlength="16" class="form-control" placeholder="선생님명">
						</td>
					</tr>
					<tr>
						<th scope="row">전화번호</th>
						<td>
							<input type="text" name="tel_no" id="tel_no" value="" maxlength="20" class="form-control" placeholder="전화번호" />
						</td>
					</tr>
					<tr>
						<th scope="row">핸드폰</th>
						<td>
							<input type="text" name="ptel_no" id="ptel_no" value="" maxlength="20" class="form-control" placeholder="핸드폰"/>
						</td>
					</tr>
					<tr>
						<th scope="row">학원</th>
						<td>
							<c:forEach var="hakwonList" items="${hakwonList}" varStatus="status">
								<input type="checkbox" name="hakwon_cd" id="hakwon_cd"  value="${hakwonList.hakwon_cd}"/>${hakwonList.hakwon_nm}&nbsp;
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
				<a class="btn btn-primary" href="javascript:insertteacher()" role="button">등록</a>
	        </div>
	    </div>
	</div>
</form>
