<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(document).ready(function(){
});

function insertkwamok() {
	var frm_insert = $('#frm_insert')[0];
	var kwamok_nm = $('#kwamok_nm').val();
	var kwamok_folder_cd = $('#kwamok_folder_cd').val();
	if(kwamok_nm == '') {
		alert('과목명을 입력해 주세요.');
		return;
	}
	if(kwamok_folder_cd == '') {
		alert('과목폴더명을 입력해 주세요.');
		return;
	}
	if(confirm("과목을 등록하겠습니까?")) {
		frm_insert.target = "summitframe";
		frm_insert.submit();
	}
}
</script>
<br/>
<p class="bg-primary bg-titleval">과목등록</p>

<!-- state : end -->
<form id="frm_insert" name="frm_insert" method="post" action="<c:url value="/admin/kwamok_insertafter.do" />">
	<input type="hidden" name="use_yn" value="Y" />
	<div class="row">
		<div class="container col-xs-12">
			<table class="table table-bordered">
				<colgroup><col width="15%" /><col width="85%" /></colgroup>
				<tbody>
					<tr>
						<th>과목명</th>
						<td>
							<input type="text" id="kwamok_nm" name="kwamok_nm" class="form-control" placeholder="과목명" />
						</td>
					</tr>
					<tr>
						<th>과목폴더명</th>
						<td>
							<input type="text" id="kwamok_folder_cd" name="kwamok_folder_cd" class="form-control" placeholder="과목폴더명" />
						</td>
					</tr>
				</tbody>
			</table>
			<div class="form-group">
				<a class="btn btn-primary" href="javascript:insertkwamok()" role="button">등록</a>
	        </div>
	    </div>
	</div>
</form>
