<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(document).ready(function(){
});

function inserthakwon() {
	var frm_insert = $('#frm_insert')[0];
	var hakwon_nm = $('#hakwon_nm').val();
	if(hakwon_nm == '') {
		alert('학원명을 입력해 주세요.');
		return;
	}
	if(confirm("학원을 등록하겠습니까?")) {
		frm_insert.target = "summitframe";
		frm_insert.submit();
	}
}
</script>
<br/>
<p class="bg-primary bg-titleval">학원등록</p>

<!-- state : end -->
<form id="frm_insert" name="frm_insert" method="post" action="<c:url value="/admin/hakwon_insertafter.do" />">
	<input type="hidden" name="use_yn" value="Y" />
	<div class="row">
		<div class="container col-xs-12">
			<table class="table table-bordered">
				<colgroup><col width="15%" /><col width="85%" /></colgroup>
				<tbody>
					<tr>
						<th>학원명</th>
						<td>
							<input type="text" id="hakwon_nm" name="hakwon_nm" class="form-control" placeholder="학원명" />
						</td>
					</tr>
				</tbody>
			</table>
			<div class="form-group">
				<a class="btn btn-primary" href="javascript:inserthakwon()" role="button">등록</a>
	        </div>
	    </div>
	</div>
</form>
