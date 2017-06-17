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
<title>어드민로그인</title>
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
	var admin_cd = '${sessionScope.admin.admin_cd}';
	var teacher_cd = '${sessionScope.teacher.teacher_cd}';
	if(admin_cd != '') {
		$('#logininput').hide();
		$('#gologininput').show();
	} else if(teacher_cd != '') {
		$('#logininput').hide();
		$('#gologintinput').show();
	} else {
		$('#logininput').show();
		$('#gologininput').hide();
		$('#gologintinput').hide();
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
		
		var admin_id = $('#admin_id').val();
		var admin_pw = $('#admin_pw').val();

		if(admin_id.length < 1) {
			alert('ID를 입력해 주세요.');
			frmObj.admin_id.focus();
			return false;
		}
		if(admin_pw.length < 1) {
			alert('이름을 입력해 주세요.');
			frmObj.admin_pw.focus();
			return false;
		}
		return true;
	}
	
	function gohakwonlist() {
		var frm_hakwonlist = $('#frm_hakwonlist')[0];
		frm_hakwonlist.submit();
	}
	
	function goanswerlist() {
		var frm_answerlist = $('#frm_answerlist')[0];
		frm_answerlist.submit();
	}
</script>
</head>
<body onkeypress="if(event.keyCode==13){login();};">
<div class="container">
	<div class="container" id="logininput" style="display:block;text-align:center;">
		<form class="form-horizontal" id="frm_login" name="frm_login" method="post" action="<c:url value="/login/adminlogin.do" />" onsubmit="return false;">
			<h2 class="form-signin-heading">관리자 로그인 화면</h2>
	        <div class="form-group">
	        	<label for="admin_id" class="sr-only">아이디</label>
	        	<div class="controls">
	        		<input type="text" id="admin_id" name="admin_id" class="form-control" placeholder="아이디" />
	        	</div>
	        </div>
	        <div class="form-group">
	        	<label for="inputPassword" class="sr-only">비밀번호</label>
	        	<div class="controls">
	        		<input type="password" id="admin_pw" name="admin_pw" class="form-control" placeholder="비밀번호" />
	        	</div>
	        </div>
			<div class="form-group">
	        	<button class="btn btn-lg btn-primary btn-block"  onclick="javascript:login();">로그인</button>
	        </div>
		</form>
	</div>
	<div class="jumbotron" id="gologininput" style="display:none">
       <p>이미 로그인 하셨습니다. 아래의 버튼을 클릭해 학원리스트 화면으로 이동하세요.</p>
       <p>
         <a class="btn btn-lg btn-primary" href="javascript:gohakwonlist();" role="button">학원리스트화면 &raquo;</a>
		 <a class="btn btn-lg btn-primary" href="<c:url value="/alogout.do" />" role="button">로그아웃</a>
       </p>
    </div>
	<div class="jumbotron" id="gologintinput" style="display:none">
       <p>이미 로그인 하셨습니다. 아래의 버튼을 클릭해 문제별정답리스트 화면으로 이동하세요.</p>
       <p>
         <a class="btn btn-lg btn-primary" href="javascript:goanswerlist();" role="button">문제별정답리스트화면 &raquo;</a>
		 <a class="btn btn-lg btn-primary" href="<c:url value="/alogout.do" />" role="button">로그아웃</a>
       </p>
    </div>
</div>
<form id="frm_hakwonlist" name="frm_hakwonlist" method="post" action="<c:url value="/admin/hakwon_list.do" />">
</form>  

<form id="frm_answerlist" name="frm_answerlist" method="post" action="<c:url value="/teacher/answer_list.do" />">
</form>   
	
<iframe src="" name="summitframe" id="summitframe" width="0" height="0"></iframe>
</body>
</html>