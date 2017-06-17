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
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, user-scalable=yes, initial-scale=0.5, maximum-scale=1.0, minimum-scale=0.5, target-densitydpi=medium-dpi">
		<meta http-equiv="Content-Script-Type" content="text/javascript" />
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="imagetoolbar" content="no" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css" />" />
		<script src="<c:url value="/js/bootstrap.min.js" />"></script>
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
            

            function changekwamokfirst() {
            	var kwamok_folder_cd = '${AnswerVo.kwamok_folder_cd}';
            	$.ajax({
                    url:"<%=request.getContextPath()%>/stud/kwamok_list.do",
                    type:"POST",
                    timeout:3000,
                    async:true,
                    datatype:"String",
                    data:{
                    	"kwamok_folder_cd":kwamok_folder_cd
                    },
                    success:function(data){
                    	if(data == '') {
                    		var setData = "<option value=''>선택</option>";
                    		$("#kwamok_folder_cd").html(setData);
                    	} else {
                    		$("#kwamok_folder_cd").html(data);
                    	}
                    },
                    error: function(error){
                        alert("에러발생");
                    }
                });
            }

            function changeweekfirst() {
            	var kwamok_folder_cd = '${AnswerVo.kwamok_folder_cd}';
            	var weekvalue = '${AnswerVo.weekvalue}';
            	
            	if(kwamok_folder_cd == '') {
            		return;
            	}
            	$.ajax({
                    url:"<%=request.getContextPath()%>/stud/week_list.do",
                    type:"POST",
                    timeout:3000,
                    async:true,
                    datatype:"String",
                    data:{
                    	"kwamok_folder_cd":kwamok_folder_cd,
                    	"weekvalue":weekvalue
                    },
                    success:function(data){
                    	if(data == '') {
                    		var setData = "<option value=''>선택</option>";
                    		$("#weekvalue").html(setData);
                    	} else {
                    		$("#weekvalue").html(data);
                    		//$('#school_cd').val(school_cd);
                    		//$('#gaeyeul_nm').val(gaeyeul_nm);
                    	}
                    },
                    error: function(error){
                        alert("에러발생");
                    }
                });
            }


            function changedayfirst() {
            	var kwamok_folder_cd = '${AnswerVo.kwamok_folder_cd}';
            	var weekvalue = '${AnswerVo.weekvalue}';
            	var dayvalue = '${AnswerVo.dayvalue}';
            	var hakneon = '${AnswerVo.hakneon}';
            	//var depart_no = $('#depart_no').val();
            	
            	if(kwamok_folder_cd == '' || weekvalue == '') {
            		return;
            	}
            	$.ajax({
                    url:"<%=request.getContextPath()%>/stud/day_list.do",
                    type:"POST",
                    timeout:3000,
                    async:true,
                    datatype:"String",
                    data:{
                    	"kwamok_folder_cd":kwamok_folder_cd,
                    	"weekvalue":weekvalue,
                    	"dayvalue":dayvalue,
                    	"hakneon":hakneon
                    },
                    success:function(data){
                    	if(data == '') {
                    		var setData = "<option value=''>선택</option>";
                    		$("#dayvalue").html(setData);
                    	} else {
                    		$("#dayvalue").html(data);
                    		//$('#school_cd').val(school_cd);
                    		//$('#gaeyeul_nm').val(gaeyeul_nm);
                    	}
                    },
                    error: function(error){
                        alert("에러발생");
                    }
                });
            }

            function changekwamok() {
        		var setData = "<option value=''>선택</option>";
        		$("#kwamok_folder_cd").html(setData);
        		$("#weekvalue").html(setData);
        		$("#dayvalue").html(setData);
        		
            	var hakneon = $('#hakneon').val();
            	if(hakneon == '') {
            		return;
            	}
            	var kwamok_folder_cd = '';
            	
            	$.ajax({
                    url:"<%=request.getContextPath()%>/stud/kwamok_list.do",
                    type:"POST",
                    timeout:3000,
                    async:true,
                    datatype:"String",
                    data:{
                    	"kwamok_folder_cd":kwamok_folder_cd
                    },
                    success:function(data){
                    	if(data == '') {
                    		var setData = "<option value=''>선택</option>";
                    		$("#kwamok_folder_cd").html(setData);
                    	} else {
                    		$("#kwamok_folder_cd").html(data);
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
        		
            	var kwamok_folder_cd = $('#kwamok_folder_cd').val();
            	if(kwamok_folder_cd == '') {
            		return;
            	}
            	$.ajax({
                    url:"<%=request.getContextPath()%>/stud/week_list.do",
                    type:"POST",
                    timeout:3000,
                    async:true,
                    datatype:"String",
                    data:{
                    	"kwamok_folder_cd":kwamok_folder_cd
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
        		
            	var kwamok_folder_cd = $('#kwamok_folder_cd').val();
            	var weekvalue = $('#weekvalue').val();
            	var hakneon = $('#hakneon').val();
            	if(kwamok_folder_cd == '' || weekvalue == '') {
            		return;
            	}
            	$.ajax({
                    url:"<%=request.getContextPath()%>/stud/day_list.do",
                    type:"POST",
                    timeout:3000,
                    async:true,
                    datatype:"String",
                    data:{
                    	"kwamok_folder_cd":kwamok_folder_cd,
                    	"weekvalue":weekvalue,
                    	"hakneon":hakneon
                    },
                    success:function(data){
                    	if(data == '') {
                    		var setData = "<option value=''>선택</option>";
                    		$("#dayvalue").html(setData);
                    	} else {
                    		$("#dayvalue").html(data);
                    		//$('#school_cd').val(school_cd);
                    		//$('#gaeyeul_nm').val(gaeyeul_nm);
                    	}
                    },
                    error: function(error){
                        alert("에러발생");
                    }
                });
            }
        //]]>
        </script>
        
		<title><tiles:getAsString name="title"/></title>
		<style type="text/css">
	    body {
	    	padding-left: 40px;
	    	padding-right: 40px;
	  		padding-top: 40px;
	  		padding-bottom: 40px;
	  		font-style: normal;
			font-variant: normal;
			font-weight: normal;
			font-size: 9pt;
		}
		th {
		  background-color: #eee;
		}
		.bg-titleval {
			padding-left: 10px;
	    	padding-right: 10px;
	  		padding-top: 10px;
	  		padding-bottom: 10px;
		}
		
		.searchval {
			margin-left: 1px;
			margin-right: 1px;
		}
		.hearderval {
			padding-left: 10px;
	    	padding-right: 10px;
	  		padding-top: 10px;
	  		padding-bottom: 10px;
		}
		</style>
	</head>
	<body onkeypress="if(event.keyCode==13){keypressSearch();};">
    	<!-- new header :: START -->
    	<div class="container-fluid">
    		<nav class="navbar navbar-inverse navbar-fixed-top">
	    		<div class="row-fluid">
	    			<tiles:insertAttribute name="header" flush="false"/>	
	    		</div>
	    	</nav>
      		<div class="row-fluid">
		    	<tiles:insertAttribute name="menu" flush="false"/>
				<div class="col-md-10">
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
		<!-- wrap_area :: END -->
	</body>
	<iframe src="" name="summitframe" id="summitframe" width="0" height="0" />
</html>
