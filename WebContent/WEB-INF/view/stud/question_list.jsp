<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
		.form-inline {
		  padding: 15px;
		  margin: 0 auto;
		  background-color: #fff;
          border: 1px solid #e5e5e5;
          padding: 19px 29px 29px;
		}
</style>
<script type="text/javascript">
$(document).ready(function(){
	var kwamok_cd = '${AnswerVo.kwamok_cd}';
	var weekvalue = '${AnswerVo.weekvalue}';
	var dayvalue = '${AnswerVo.dayvalue}';
	if(kwamok_cd != '' && weekvalue != '') {
		changeweekfirst();	
	}
	if(weekvalue != '' && dayvalue != '') {
		changedayfirst();	
	}
});


function changeweekfirst() {
	var kwamok_cd = '${AnswerVo.kwamok_cd}';
	var weekvalue = '${AnswerVo.weekvalue}';
	if(kwamok_cd == '') {
		return;
	}
	$.ajax({
        url:"<%=request.getContextPath()%>/stud/week_list.do",
        type:"POST",
        timeout:3000,
        async:true,
        datatype:"String",
        data:{
        	"kwamok_cd":kwamok_cd,
        	"weekvalue":weekvalue
        },
        success:function(data){
        	if(data == '') {
        		var setData = "<option value=''>선택</option>";
        		$("#weekvalue").html(setData);
        	} else {
        		$("#weekvalue").html(data);
        	}
        },
        error: function(error){
            alert("에러발생");
        }
    });
}


function changedayfirst() {
	var kwamok_cd = '${AnswerVo.kwamok_cd}';
	var weekvalue = '${AnswerVo.weekvalue}';
	var dayvalue = '${AnswerVo.dayvalue}';
	
	if(kwamok_cd == '' || weekvalue == '') {
		return;
	}
	$.ajax({
        url:"<%=request.getContextPath()%>/stud/day_list.do",
        type:"POST",
        timeout:3000,
        async:true,
        datatype:"String",
        data:{
        	"kwamok_cd":kwamok_cd,
        	"weekvalue":weekvalue,
        	"dayvalue":dayvalue
        },
        success:function(data){
        	if(data == '') {
        		var setData = "<option value=''>선택</option>";
        		$("#dayvalue").html(setData);
        	} else {
        		$("#dayvalue").html(data);
        	}
        },
        error: function(error){
            alert("에러발생");
        }
    });
}

function changeweek() {
	var setData = "<option value=''>선택</option>";
	$("#weekvalue").html(setData);
	$("#dayvalue").html(setData);
	
	var kwamok_cd = $('#kwamok_cd').val();
	if(kwamok_cd == '') {
		return;
	}
	
	$.ajax({
        url:"<%=request.getContextPath()%>/stud/week_list.do",
        type:"POST",
        timeout:3000,
        async:true,
        datatype:"String",
        data:{
        	"kwamok_cd":kwamok_cd
        },
        success:function(data){
        	if(data == '') {
        		var setData = "<option value=''>선택</option>";
        		$("#weekvalue").html(setData);
        	} else {
        		$("#weekvalue").html(data);
        	}
        },
        error: function(error){
            alert("에러발생");
        }
    });
}

function changeday() {
	var setData = "<option value=''>선택</option>";
	$("#dayvalue").html(setData);
	
	var kwamok_cd = $('#kwamok_cd').val();
	var weekvalue = $('#weekvalue').val();

	if(kwamok_cd == '' || weekvalue == '') {
		return;
	}
	
	$.ajax({
        url:"<%=request.getContextPath()%>/stud/day_list.do",
        type:"POST",
        timeout:3000,
        async:true,
        datatype:"String",
        data:{
        	"kwamok_cd":kwamok_cd,
        	"weekvalue":weekvalue
        },
        success:function(data){
        	if(data == '') {
        		var setData = "<option value=''>선택</option>";
        		$("#dayvalue").html(setData);
        	} else {
        		$("#dayvalue").html(data);
        	}
        },
        error: function(error){
            alert("에러발생");
        }
    });
}

function SearchQuestion() {
	var frmObj = $('#frm_search')[0];
	var kwamok_cd = $('#kwamok_cd').val();
	var weekvalue = $('#weekvalue').val();
	var dayvalue = $('#dayvalue').val();
	
	if(kwamok_cd.length < 1) {
		alert('과목을선택해 주세요.');
		frmObj.kwamok_cd.focus();
		return;
	}
	if(weekvalue.length < 1) {
		alert('주차를 선택해 주세요.');
		frmObj.weekvalue.focus();
		return;
	}
	if(dayvalue.length < 1) {
		alert('일자를 선택해 주세요.');
		frmObj.dayvalue.focus();
		return;
	}
	frmObj.submit();
}

function questioninsert() {
	var frm = $('#frm_insert')[0];
	var cnt = 1;
	var answers = '';
	var questions = '';
	var answercheck = false;
	var alertval = '';
	$("input[name=answercnt]").each(function() {
		questions += this.value + ":::::";
		
		if ($('input:radio[name=answer'+cnt+']:checked').length != 0) {
			answercheck = true;
		} else {
			alertval += cnt+ ' ';
		}
		$("input[name=answer"+cnt+"]").each(function() {
			if(this.checked) {
				answers += this.value + ':::::';
			}
		});
		cnt++;
	});
	
	if(alertval != '') {
		alert(alertval + "문제의 답변을 선택해 주세요.");
		return;
	}
	
	//alert(questions + ":" + answers);
	var kwamok_cd = $('#kwamok_cdhi').val();
	var weekvalue = $('#weekvaluehi').val();
	var dayvalue = $('#dayvaluehi').val();
	
	if(confirm("답변을 등록 하시겠습니까?")) {
		frm.answers.value = answers;
		frm.questions.value = questions;
		frm.kwamok_cd.value = kwamok_cd;
		frm.weekval.value = weekvalue;
		frm.dayval.value = dayvalue;
		frm.submit();
	}
}


function fn_downFile(){
	var kwamok_cd = $('#kwamok_cdhi').val();
	var weekvalue = $('#weekvaluehi').val();
	var dayvalue = $('#dayvaluehi').val();
	
	window.open("<c:url value='/common/fildDownload.do?kwamok_cd="+kwamok_cd+"&weekvalue="+weekvalue+"&dayvalue="+dayvalue+"'/>");
}	

</script>
<br/>
<p class="bg-primary bg-titleval">문제리스트</p>

<form class="form-inline"  id="frm_search" name="frm_search" method="post" action="<c:url value="/stud/question_list.do" />">
	<input type="hidden" id="kwamok_cdhi" name="kwamok_cdhi" value="${AnswerVo.kwamok_cd}" />
	<input type="hidden" id="weekvaluehi" name="weekvaluehi" value="${AnswerVo.weekvalue}" />
	<input type="hidden" id="dayvaluehi" name="dayvaluehi" value="${AnswerVo.dayvalue}" />
	<div class="row">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-12" style="background-color: #eee;">
					<label for="kwamok_cd">과목선택:</label>
					<select name='kwamok_cd' id='kwamok_cd' class="form-control" style="width:150px;" onchange="javascript:changeweek()">
	     							<option value="">선택</option>
	        				<c:forEach var="studKwamokList" items="${studKwamokList}" varStatus="status">
							<option value="${studKwamokList.kwamok_cd}" <c:if test="${studKwamokList.kwamok_cd eq AnswerVo.kwamok_cd}">selected</c:if>>${studKwamokList.kwamok_nm}</option>
						</c:forEach>
					</select>
					<label for="weekvalue">주차선택:</label>
					<select name='weekvalue' id='weekvalue' class="form-control" style="width:150px;" onchange="javascript:changeday()">
	     							<option value="">선택</option>
					</select>
					<label for="dayvalue">일차선택:</label>
					<select name='dayvalue' id='dayvalue' class="form-control" style="width:150px;">
		         			<option value="">선택</option>
			    	</select>
			    	<input type="button" value="검색" class="btn btn-default" onclick="javascript:SearchQuestion();" />
				</div>
				<div class="row">
					<div class="col-md-12">
						<br/>
					</div>
				</div>
			</div>

		</div>
		<br/>	
		<div class="row">
			<div class="container col-xs-12">
				<c:set value="" var="tablestyleval"/>
				<c:if test="${empty answerlist}">
					<c:if test="${!empty questionlist}">
						<c:set value="table-striped" var="tablestyleval"/>
					</c:if>
				</c:if>
				<table class="table table-bordered ${tablestyleval}">
					<colgroup><col width="10%" /><col width="90%" /></colgroup>
					<thead>
						<tr>
							<th>질문번호</th>
							<th>문제정답</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${empty answerlist}">
							<c:if test="${empty AnswerVo.dayvalue}">
								<tr>
									<td colspan="2">과목과 주차와 일차를 먼저 선택해 주세요1</td>
								</tr>
							</c:if>
							<c:if test="${!empty AnswerVo.dayvalue}">
								<c:if test="${empty questionlist}">
									<tr>
										<td colspan="2">검색결과가 없습니다.</td>
									</tr>
								</c:if>
							</c:if>
						</c:if>
						<c:if test="${!empty answerlist}">
							<c:forEach var="answerlist" items="${answerlist}" varStatus="status">
							<c:set value="" var="styleval"/>
							<c:if test="${answerlist.rightyn eq 'N'}">
								<c:set value="danger" var="styleval"/>
							</c:if>
							<tr class="${styleval}" >
								<td>${answerlist.questionvalue} 번</td>
								<td>
									<c:if test="${answerlist.rightyn eq 'Y'}">
										<font color="#0000ff">정답입니다</font>(정답 : ${answerlist.rightanswer}번)
									</c:if>
									<c:if test="${answerlist.rightyn eq 'N'}">
										<font color="#ff0000">오답입니다</font> (답변 : ${answerlist.studanswer}번 | 정답 : ${answerlist.rightanswer}번)
									</c:if>
									
								</td>
							</tr>
							</c:forEach>
						</c:if>
						
						<c:if test="${empty answerlist}">
							<c:if test="${!empty questionlist}">
								<c:forEach var="questionlist" items="${questionlist}" varStatus="status">
									<input type="hidden" name="answercnt" id="answercnt" value="${questionlist}"/>
									<tr>
										<td><label>${questionlist}번</label></td>
										<td><input type="radio" name="answer${status.count}" value="1" /><label>1&nbsp;&nbsp;</label>
										<input type="radio" name="answer${status.count}" value="2" /><label>2&nbsp;&nbsp;</label>
										<input type="radio" name="answer${status.count}" value="3" /><label>3&nbsp;&nbsp;</label>
										<input type="radio" name="answer${status.count}" value="4" /><label>4&nbsp;&nbsp;</label>
										<input type="radio" name="answer${status.count}" value="5" /><label>5&nbsp;&nbsp;</label>
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</c:if>
					</tbody>
					
				</table>
			</div>
			<div class="container col-xs-12">
				<c:if test="${!empty answerlist}">
					<div class="default_btn_wrap">	
						<a href="javascript:fn_downFile();" class="btn btn-primary"><span>해설지다운</span></a>
					</div>
				</c:if>
				<c:if test="${!empty questionlist and empty answerlist}">
					<div class="default_btn_wrap">	
						<a href="javascript:questioninsert();" class="btn btn-primary"><span>답변제출</span></a>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</form>
<form id="frm_insert" name="frm_insert" method="post" action="<c:url value="/stud/answer_insert.do" />">
	<input type="hidden" id="answers" name="answers" value="" />
	<input type="hidden" id="questions" name="questions" value="" />
	<input type="hidden" id="kwamok_cd" name="kwamok_cd" value="" />
	<input type="hidden" id="weekval" name="weekval" value="" />
	<input type="hidden" id="dayval" name="dayval" value="" />
</form>