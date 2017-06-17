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
</script>
<br/>
<p class="bg-primary bg-titleval">문제별정답리스트</p>
<form class="form-inline" id="frm_search" name="frm_search" method="post" action="<c:url value="/admin/answer_list.do" />">
<input type="hidden" name="pageIndex" value="1" />
<input type="hidden" id="firstsearch" name="firstsearch" value="1"/>
	<div class="row searchval">
		<div class="col-md-12" style="background-color: #e0e0e0">
			<label for="hakwon_cd">학원:</label>
			<select class="form-control" name='hakwon_cd' id='hakwon_cd'>
    						<option value="">선택</option>
        			<c:forEach var="hakwonList" items="${hakwonList}" varStatus="status">
					<option value="${hakwonList.hakwon_cd}" <c:if test="${hakwonList.hakwon_cd eq AnswerVo.hakwon_cd}">selected</c:if>>${hakwonList.hakwon_nm}</option>
				</c:forEach>
			</select>

			<label><b>과목선택:</b></label>
			<select class="form-control" name='kwamok_cd' id='kwamok_cd'>
						<option value="">선택</option>
				<c:forEach var="kwamokList" items="${kwamokList}" varStatus="status">
					<option value="${kwamokList.kwamok_cd}" <c:if test="${kwamokList.kwamok_cd eq AnswerVo.kwamok_cd}">selected</c:if>>${kwamokList.kwamok_nm}</option>
				</c:forEach>
			</select>
			
			<label for="hakneon">학년:</label>
			<select class="form-control" name='hakneon' id='hakneon'>
				<option value="">선택</option>
        			<option value="1" <c:if test="${AnswerVo.hakneon eq '1'}">selected</c:if>>1학년</option>
        			<option value="2" <c:if test="${AnswerVo.hakneon eq '2'}">selected</c:if>>2학년</option>
        			<option value="3" <c:if test="${AnswerVo.hakneon eq '3'}">selected</c:if>>3학년</option>
			</select>

			<label for="weekvalue">주차</label>
			<select name='weekvalue' id='weekvalue' class="form-control">
    			<option value="">선택</option>
       			<c:forEach var="weekvalList" items="${weekvalList}" varStatus="status">
					<option value="${weekvalList}" <c:if test="${weekvalList eq AnswerVo.weekvalue}">selected</c:if>>${weekvalList}</option>
				</c:forEach>
			</select>

			<label for="dayvalue">일자</label>
			<select name='dayvalue' id='dayvalue' class="form-control">
    			<option value="">선택</option>
       			<c:forEach var="dayvalList" items="${dayvalList}" varStatus="status">
					<option value="${dayvalList}" <c:if test="${dayvalList eq AnswerVo.dayvalue}">selected</c:if>>${dayvalList}</option>
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
</form>
<br/>	
<div class="row">
	<div class="container col-xs-12">
		<table class="table table-bordered table-striped">
			<colgroup><col width="15%" /><col width="10%" /><col width="10%" /><col width="10%" /><col width="10%" /><col width="10%" /><col width="10%" /><col width="15%" /><col width="10%" /></colgroup>
			<thead>
				<tr>
					<th>학원명</th>
					<th>과목명</th>
					<th>주차</th>
					<th>일자</th>
					<th>질문</th>
					<th>정답자(명)</th>
					<th>오답자(명)</th>
					<th>총응답자(명)</th>
					<th>정답율(%)</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty answerststsList}">
					<tr>
						<c:if test="${empty AnswerVo.hakwon_cd}">
							<td colspan="9">검색조건을 선택후 검색버튼을 클릭해 주세요.</td>
						</c:if>
						<c:if test="${!empty AnswerVo.hakwon_cd}">
							<td colspan="9">검색결과가 없습니다.</td>
						</c:if>
					</tr>
				</c:if>
				<c:if test="${!empty answerststsList}">
					<c:forEach var="answerststsList" items="${answerststsList}" varStatus="status">
						<tr>
							<td>${answerststsList.hakwon_nm}</td>
							<td>${answerststsList.kwamok_nm}</td>
							<td>${answerststsList.weekvalue}주차</td>
							<td>${answerststsList.dayvalue}일자</td>
							<td>${answerststsList.questionvalue}문항</td>
							<td>${answerststsList.rightcnt}</td>
							<td>${answerststsList.wrongcnt}</td>
							<td>${answerststsList.allcnt}</td>
							<td>${answerststsList.rightpercent}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
<div class="row">
	<br/>
	<div class="container col-xs-12">
	<!-- 이전/다음페이지 없을 경우, 숨김처리-->
		${pageUtil}
	</div>
</div>