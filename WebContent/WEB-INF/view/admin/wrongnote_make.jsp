<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ page import="org.apache.pdfbox.pdmodel.PDDocument;" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
$(document).ready(function(){
});

function Search(pageIndex) {
	var frm_search = $('#frm_search')[0];
	
	var weekval = $('#weekval').val();
	var weekvalto = $('#weekvalto').val();
	
	if(weekval == '') {
		alert('주차From을 선택해 주세요');
		return;
	}
	if(weekvalto == '') {
		alert('주차To를 선택해 주세요');
		return;
	}
	if(weekval > weekvalto) {
		alert('주차From 보다 주차To가 작습니다.');
		return;
	}
	
	frm_search.pageIndex.value = pageIndex;
	frm_search.submit();
}

function wrongnotemakeone(stud_cd,kwamok_cd) {
	var frm_insert = $('#frm_insert')[0];
	var hakwon_cd = $('#hakwon_cd').val();
	if(kwamok_cd == '') {
		kwamok_cd = $('#kwamok_cd').val();
	}
	var hakneon = $('#hakneon').val();
	var weekval = $('#weekval').val();
	var weekvalto = $('#weekvalto').val();
	var titlevalue = $('#titlevalue').val();
	var titlevalueend = $('#titlevalueend').val();
	
	if(weekval == '') {
		alert('주차From을 선택해 주세요');
		return;
	}
	if(weekvalto == '') {
		alert('주차To를 선택해 주세요');
		return;
	}
	if(weekval > weekvalto) {
		alert('주차From 보다 주차To가 작습니다.');
		return;
	}
	if(titlevalue == '') {
		alert('표지앞면을 선택해 주세요');
		return;
	}
	if(titlevalueend == '') {
		alert('표지뒷면을 선택해 주세요');
		return;
	}
	
	if(confirm("오답노트를 생성하시겠습니까?")) {
		frm_insert.stud_cd.value = stud_cd;
		frm_insert.hakwon_cd.value = hakwon_cd;
		frm_insert.hakneon.value = hakneon;
		frm_insert.kwamok_cd.value = kwamok_cd;
		frm_insert.weekval.value = weekval;
		frm_insert.weekvalto.value = weekvalto;
		frm_insert.titlevalue.value = titlevalue;
		frm_insert.titlevalueend.value = titlevalueend;
		frm_insert.target = 'summitframe';
		frm_insert.submit();
	}
}

function deleteanswernodayvalue() {
	var frm_update = $('#frm_update')[0];
	frm_update.target = 'summitframe';
	frm_update.submit();
}

</script>
<br/>
<p class="bg-info bg-titleval"><b>주차From과주차To만 선택하시고 검색하신후 전체오답노트생성을 클릭하시면 /과목폴더/학원명/주차From_주차To폴더에 오답노트가 생성됩니다.</b></p>
<p class="bg-primary bg-titleval">오답노트생성</p>
<form class="form-inline" id="frm_search" name="frm_search" method="post" action="<c:url value="/admin/wrongnote_make.do" />">
	<input type="hidden" name="pageIndex" value="1" />	
	<input type="hidden" id="stud_cd" name="stud_cd" />
	<input type="hidden" id="firstsearch" name="firstsearch" value="1"/>
	
	<div class="row searchval" id="searchdiv1" style="display:block;">
		<div class="col-md-12" style="background-color: #e0e0e0">
			<label for="hakwon_cd">학원</label>
			<select class="form-control" name='hakwon_cd' id='hakwon_cd'>
    			<option value="">선택</option>
        		<c:forEach var="hakwonList" items="${hakwonList}" varStatus="status">
					<option value="${hakwonList.hakwon_cd}" <c:if test="${hakwonList.hakwon_cd eq AnswerVo.hakwon_cd}">selected</c:if>>${hakwonList.hakwon_nm}</option>
				</c:forEach>
			</select>
						
			<label><b>과목선택:</b></label>
			<select name='kwamok_cd' id='kwamok_cd' class="form-control">
    			<option value="">선택</option>
       			<c:forEach var="kwamokList" items="${kwamokList}" varStatus="status">
					<option value="${kwamokList.kwamok_cd}" <c:if test="${kwamokList.kwamok_cd eq AnswerVo.kwamok_cd}">selected</c:if>>${kwamokList.kwamok_nm}</option>
				</c:forEach>
			</select>
			
			<label for="hakneon">학년</label>
			<select name='hakneon' id='hakneon' class="form-control">
    				    <option value="">전체</option>
        			<option value="1" <c:if test="${AnswerVo.hakneon eq '1'}">selected</c:if>>1학년</option>
        			<option value="2" <c:if test="${AnswerVo.hakneon eq '2'}">selected</c:if>>2학년</option>
        			<option value="3" <c:if test="${AnswerVo.hakneon eq '3'}">selected</c:if>>3학년</option>
			</select>
			
			<label for="weekval">주차From</label>
			<select name='weekval' id='weekval' class="form-control">
    			<option value="">선택</option>
       			<c:forEach var="weekvalList" items="${weekvalList}" varStatus="status">
					<option value="${weekvalList}" <c:if test="${weekvalList eq AnswerVo.weekval}">selected</c:if>>${weekvalList}</option>
				</c:forEach>
			</select>
			
			<label for="weekvalto">주차To</label>
			<select name='weekvalto' id='weekvalto' class="form-control">
    			<option value="">선택</option>
       			<c:forEach var="weekvalList" items="${weekvalList}" varStatus="status">
					<option value="${weekvalList}" <c:if test="${weekvalList eq AnswerVo.weekvalto}">selected</c:if>>${weekvalList}</option>
				</c:forEach>
			</select>
			
			<!-- label for="pageUnit">주차간격</label>
			<select class="form-control" id="setweekval" name="setweekval">
				<option value="">선택</option>
				<option value="1" <c:if test="${AnswerVo.setweekval eq '1'}">selected</c:if>>1주간격</option>
				<option value="2" <c:if test="${AnswerVo.setweekval eq '2'}">selected</c:if>>2주간격</option>
				<option value="3" <c:if test="${AnswerVo.setweekval eq '3'}">selected</c:if>>3주간격</option>
				<option value="4" <c:if test="${AnswerVo.setweekval eq '4'}">selected</c:if>>4주간격</option>
				<option value="5" <c:if test="${AnswerVo.setweekval eq '5'}">selected</c:if>>5주간격</option>
				<option value="6" <c:if test="${AnswerVo.setweekval eq '6'}">selected</c:if>>6주간격</option>
				<option value="7" <c:if test="${AnswerVo.setweekval eq '7'}">selected</c:if>>7주간격</option>
				<option value="8" <c:if test="${AnswerVo.setweekval eq '8'}">selected</c:if>>8주간격</option>
				<option value="9" <c:if test="${AnswerVo.setweekval eq '9'}">selected</c:if>>9주간격</option>
				<option value="10" <c:if test="${AnswerVo.setweekval eq '10'}">selected</c:if>>10주간격</option>
			</select-->
			<label for="weekvalue">표지앞면</label>
			<select name='titlevalue' id='titlevalue' class="form-control">
    					<option value="">선택</option>
       				<c:forEach var="titlelist" items="${titlelist}" varStatus="status">
					<option value="${titlelist}" <c:if test="${titlelist eq AnswerVo.titlevalue}">selected</c:if>>${titlelist}</option>
				</c:forEach>
			</select>
			<label for="weekvalue">표지뒷면</label>
			<select name='titlevalueend' id='titlevalueend' class="form-control">
    					<option value="">선택</option>
       				<c:forEach var="titlelist" items="${titlelist}" varStatus="status">
					<option value="${titlelist}" <c:if test="${titlelist eq AnswerVo.titlevalueend}">selected</c:if>>${titlelist}</option>
				</c:forEach>
			</select>
			<label for="pageUnit">건수</label>
			<select class="form-control" name="pageUnit">
				<option value="10" <c:if test="${AnswerVo.pageUnit eq '10'}">selected</c:if>>10건</option>
				<option value="20" <c:if test="${AnswerVo.pageUnit eq '20'}">selected</c:if>>20건</option>
				<option value="30" <c:if test="${AnswerVo.pageUnit eq '30'}">selected</c:if>>30건</option>
				<option value="40" <c:if test="${AnswerVo.pageUnit eq '40'}">selected</c:if>>40건</option>
				<option value="50" <c:if test="${AnswerVo.pageUnit eq '50'}">selected</c:if>>50건</option>
				<option value="100" <c:if test="${AnswerVo.pageUnit eq '100'}">selected</c:if>>100건</option>
			</select>
			<input type="button" value="검색" class="btn btn-default" onclick="javascript:Search(1)"/>
		</div>
	</div>
	<br/>	
	<div class="row">
		<div class="container col-xs-12">
			<table class="table table-bordered table-striped">
				<colgroup><col width="10%" /><col width="10%" /><col width="10%" /><col width="10%" /><col width="10%" /><col width="10%" /><col width="10%" /><col width="10%" /><col width="20%" /></colgroup>
				<thead>
					<tr>
						<th>학원명</th>
						<th>과목</th>
						<th>학생ID</th>
						<th>학년</th>
						<th>학생명</th>
						<th>정답개수</th>
						<th>오답개수</th>
						<th>전체문항수</th>
						<th>오답노트생성</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty answerwrongststsList}">
						<tr>
							<c:if test="${empty AnswerVo.hakwon_cd}">
								<td colspan="9">검색조건을 선택후 검색버튼을 클릭해 주세요.</td>
							</c:if>
							<c:if test="${!empty AnswerVo.hakwon_cd}">
								<td colspan="9">검색결과가 없습니다.</td>
							</c:if>
						</tr>
					</c:if>
					
					<c:if test="${!empty answerwrongststsList}">
						<c:forEach var="answerwrongststsList" items="${answerwrongststsList}" varStatus="status">
							<tr>
								<td>${answerwrongststsList.hakwon_nm}</td>
								<td>${answerwrongststsList.kwamok_nm}</td>
								<td>${answerwrongststsList.stud_id}</td>
								<td>${answerwrongststsList.hakneon}</td>
								<td>${answerwrongststsList.stud_nm}</td>
								<td>${answerwrongststsList.rightcnt}</td>
								<td>${answerwrongststsList.wrongcnt}</td>
								<td>${answerwrongststsList.allcnt}</td>
								<td>
									<c:if test="${answerwrongststsList.rightcnt ne answerwrongststsList.allcnt}">
										<a class="btn btn-primary" href="javascript:wrongnotemakeone('${answerwrongststsList.stud_cd}','${answerwrongststsList.kwamok_cd}')">
										학생오답노트생성
										</a>
									</c:if>
									
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="container col-xs-12">
			<c:if test="${!empty answerwrongststsList}">
				<a class="btn btn-primary" href="javascript:wrongnotemakeone('','')">전체오답노트생성</a>
				<a class="btn btn-danger" href="javascript:deleteanswernodayvalue('')">오답노트생성오류처리</a>
			</c:if>
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
<form id="frm_insert" name="frm_insert" method="post" action="<c:url value="/admin/wrongnote_aftermake.do" />">
	<input type="hidden" name="stud_cd" />
	<input type="hidden" name="hakwon_cd" />
	<input type="hidden" name="dayvalue" />
	<input type="hidden" name="hakneon" />
	<input type="hidden" name="setweekval" />
	<input type="hidden" name="kwamok_cd" />
	<input type="hidden" name="weekval" />
	<input type="hidden" name="weekvalto" />
	<input type="hidden" name="titlevalue" />
	<input type="hidden" name="titlevalueend" />
	
	
	
</form>

<form id="frm_update" name="frm_update" method="post" action="<c:url value="/admin/deleteanswernodayvalue.do" />">
</form>