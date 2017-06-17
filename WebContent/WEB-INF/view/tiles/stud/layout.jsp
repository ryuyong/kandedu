<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html> 
<html lang="ko">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=yes, initial-scale=0.5, maximum-scale=1.0, minimum-scale=0.5, target-densitydpi=medium-dpi">
		<meta http-equiv="Content-Script-Type" content="text/javascript" />
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="imagetoolbar" content="no" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css" />" />
		<script src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
		<script src="<c:url value="/js/commonkandedu.js" />"></script>
        <script type="text/javascript">
        //<![CDATA[            
            $(document).ready(function(){
            	var message = '${sessionScope.message}';
            	if(message != '') {
            		alert(message);
            	}
            	<%
            		session.removeAttribute("message");
            	%>
            });
        	function goquestion() {
        		var frm_question = $('#frm_question')[0];
        		frm_question.submit();
        	}
        	
        	function gologout() {
        		var frm_logout = $('#frm_logout')[0];
        		frm_logout.submit();
        	}
        //]]>
        </script>
		<title><tiles:getAsString name="title"/></title>
		<style type="text/css">
	    body {
	  		padding-top: 40px;
	  		padding-bottom: 40px;
	  		background-color: #eee;
	  		font-style: normal;
			font-variant: normal;
			font-weight: normal;
			font-size: 9pt;
		}
		th {
		  background-color: #eee;
		}
		.hearderval {
			padding-left: 10px;
	    	padding-right: 10px;
	  		padding-top: 10px;
	  		padding-bottom: 10px;
		}
		.bg-titleval {
			padding-left: 10px;
	    	padding-right: 10px;
	  		padding-top: 10px;
	  		padding-bottom: 10px;
		}
		</style>
	</head>
	<body onkeypress="if(event.keyCode==13){keypressSearch();};">
	<div class="container-fluid">
		<!-- wrap_area :: START -->
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="row-fluid">
			    <!-- new header :: START -->
			    <tiles:insertAttribute name="header" flush="false"/>
			</div>
		</nav>
      	<div class="row-fluid">
		    <div class="col-md-12">
		    	<tiles:insertAttribute name="contents" flush="true"/>
		    </div>
		</div>
	    <nav class="navbar navbar-inverse navbar-fixed-bottom">
		    <div class="row-fluid">
		    	<div class="col-md-12">
		    		<tiles:insertAttribute name="footer" flush="false"/>
		    	</div>
		    </div>
	    </nav>
	</div>
	<form id="frm_bbs" name="frm_bbs" method="post" action="<c:url value="/stud/bbs_list.do" />">
		<input type="hidden" name="bbs_gubun" value="" />
	</form>
	<form id="frm_qna" name="frm_qna" method="post" action="<c:url value="/stud/qna_list.do" />">
	</form>
	
	<form id="frm_question" name="frm_question" method="post" action="<c:url value="/stud/question_list.do" />">
	</form>
	
	<form id="frm_logout" name="frm_logout" method="post" action="<c:url value="/slogout.do" />">
	</form>
	<!-- wrap_area :: END -->
	<iframe src="" name="summitframe" id="summitframe" width="0" height="0" />
	</body>
</html>
