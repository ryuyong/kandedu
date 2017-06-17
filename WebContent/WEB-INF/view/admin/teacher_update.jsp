<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
});

function updateteacher() {
	var frm_update = $('#frm_update')[0];
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
	
	if(confirm("선생님을 수정하겠습니까?")) {
		frm_update.hakwon_cds.value = hakwon_cds;
		frm_update.target = "summitframe";
		frm_update.submit();
	}
}
</script>
<br/>
<p class="bg-primary bg-titleval">선생님수정</p>
<!-- state : end -->
<form id="frm_update" name="frm_update" method="post" action="<c:url value="/admin/teacher_updateafter.do" />">
<input type="hidden" name="teacher_cd" value="${result.teacher_cd}" />
<input type="hidden" name="hakwon_cds" value="" />
<input type="hidden" name="old_teacher_id" value="${result.teacher_id}" />
	<div class="row">
		<div class="container col-xs-12">
			<table class="table table-bordered">
				<colgroup><col width="15%" /><col width="85%" /></colgroup>
				<tbody>
					<tr>
						<th scope="row">선생님ID</th>
						<td>
							<input type="text" name="teacher_id" id="teacher_id" maxlength="16" value="${result.teacher_id}" class="form-control" placeholder="선생님ID">
						</td>
					</tr>
					<tr>
						<th scope="row">선생님PASS</th>
						<td>
							<input type="text" name="teacher_pass" id="teacher_pass" maxlength="16" value="${result.teacher_pass}" class="form-control" placeholder="선생님PASS">
						</td>
					</tr>
					<tr>
						<th scope="row">선생님명</th>
						<td>
							<input type="text" name="teacher_nm" id="teacher_nm" maxlength="16" value="${result.teacher_nm}" class="form-control" placeholder="선생님명">
						</td>
					</tr>
					<tr>
						<th scope="row">전화번호</th>
						<td>
							<input type="text" name="tel_no" id="tel_no" maxlength="20" value="${result.tel_no}" class="form-control" placeholder="전화번호" />
						</td>
					</tr>
					<tr>
						<th scope="row">핸드폰</th>
						<td>
							<input type="text" name="ptel_no" id="ptel_no" maxlength="20" value="${result.ptel_no}" class="form-control" placeholder="핸드폰"/>
						</td>
					</tr>
					<tr>
						<th scope="row">학원</th>
						<td>
							<c:forEach var="hakwonList" items="${hakwonList}" varStatus="status">
								<c:set value="" var="checkval"/>
								<c:forEach var="teacherHakwonList" items="${teacherHakwonList}" varStatus="status">
									<c:if test="${hakwonList.hakwon_cd eq teacherHakwonList.hakwon_cd}">
										<c:set value="checked" var="checkval"/>
									</c:if>
								</c:forEach>
								<input type="checkbox" name="hakwon_cd" id="hakwon_cd" value="${hakwonList.hakwon_cd}" ${checkval} }/>${hakwonList.hakwon_nm}&nbsp;
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th scope="row">비고</th>
						<td>
							<textarea class="form-control" name="bigo" id="bigo" cols="70" rows="10">${result.bigo}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="form-group">
				<a class="btn btn-primary" href="javascript:updateteacher()" role="button">수정</a>
	        </div>
		</div>
	</div>
</form>
