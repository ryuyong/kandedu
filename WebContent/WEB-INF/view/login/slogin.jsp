<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html> 
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta name="viewport" content="width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=1.0, minimum-scale=0.5, target-densitydpi=medium-dpi">
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="imagetoolbar" content="no" />
<title>로그인</title>
<script src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
<script src="<c:url value="/js/bootstrap.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css" />" />

<style type="text/css">
	    body {
	  		padding-top: 40px;
	  		padding-bottom: 40px;
	  		background-color: #eee;
		}
		.form-horizontal {
		  max-width: 330px;
		  padding: 15px;
		  margin: 0 auto;
		  background-color: #fff;
          border: 1px solid #e5e5e5;
          padding: 19px 29px 29px;
		}
</style>
<script type="text/javascript">
$(document).ready(function(){
	var stud_cd = '${sessionScope.stud.stud_cd}';
	if(stud_cd != '') {
		$('#logininput').hide();
		$('#gologininput').show();
	} else {
		$('#logininput').show();
		$('#gologininput').hide();
	}
});
	
	
	function login(){
		var frm_login = $('#frm_login')[0];
		var validity_check = formCheck(frm_login); // 폼 유효성체크
		if( !validity_check) return;
		frm_login.target = "summitframe";
		frm_login.submit();
	}
	
	function formCheck(frmObj){	
		var stud_id = $('#stud_id').val();
		var stud_nm = $('#stud_nm').val();
		var hakneon = $('#hakneon').val();
		if(stud_id.length < 1) {
			alert('ID를 입력해 주세요.');
			frmObj.stud_id.focus();
			return false;
		}
		if(stud_nm.length < 1) {
			alert('이름을 입력해 주세요.');
			frmObj.stud_nm.focus();
			return false;
		}
		if(hakneon.length < 1) {
			alert('학년을 선택해 주세요.');
			frmObj.hakneon.focus();
			return false;
		}
		return true;
	}
	
	function goquestion() {
		var frm_question = $('#frm_question')[0];
		frm_question.submit();
	}
</script>
</head>

<body>
<div class="container">
	<div class="container" id="logininput" style="display:block;text-align:center;">
    	<form class="form-horizontal" id="frm_login" name="frm_login" method="post" action="<c:url value="/login/studlogin.do" />" onsubmit="return false;">
			<h2 class="form-signin-heading">학생 로그인 화면</h2>
     		<div class="form-group">
      			<label for="stud_id" class="sr-only">아이디</label>
		      	<div class="controls">
		      		<input type="text" id="stud_id" name="stud_id" class="form-control" placeholder="아이디" />
		      	</div>
     		</div>
	        <div class="form-group">
	      		<label for="inputPassword" class="sr-only">학생명</label>
	      		<div class="controls">
	      			<input type="text" id="stud_nm" name="stud_nm" class="form-control" placeholder="학생명">
	      		</div>
	        </div>
	        <div class="form-group">
		      	<label for="hakneon" class="sr-only">학년</label>
		      	<div class="controls">
		  			<select name='hakneon' id='hakneon' class="form-control">
		  				<option value="">학년선택</option>
		      			<option value="1">1학년</option>
		      			<option value="2">2학년</option>
		      			<option value="3">3학년</option>
				</select>
		    	</div>
		    </div>
			<div class="form-group">
		      	<button class="btn btn-lg btn-primary btn-block"  onclick="javascript:login();">로그인</button>
		    </div>
		 </form>
	</div>
	<div class="jumbotron" id="gologininput" style="display:none">
	    <p>이미 로그인 하셨습니다. 아래의 버튼을 클릭해 답안입력 화면으로 이동하세요.</p>
	    <p>
	      <a class="btn btn-lg btn-primary" href="javascript:goquestion();" role="button">답안입력화면 &raquo;</a>
		  <a class="btn btn-lg btn-primary" href="<c:url value="/slogout.do" />" role="button">로그아웃</a>
	    </p>
	</div>
</div>


<form id="frm_question" name="frm_question" method="post" action="<c:url value="/stud/question_list.do" />">
</form>  
</body>
<iframe src="" name="summitframe" id="summitframe" width="0" height="0"/>  
</html>