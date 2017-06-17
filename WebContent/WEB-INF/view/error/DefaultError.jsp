<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
    <head>
        <meta http-equiv="Content-Script-Type" content="text/javascript" />
        <meta http-equiv="Cache-Control" content="no-cache" />
        <meta http-equiv="Pragma" content="no-cache" />
        <meta http-equiv="imagetoolbar" content="no" />
        <meta name="keywords" content="문제풀이" />
        <meta name="language" content="ko" />
        <title>에러페이지</title>
        <script src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
        <script type="text/javascript">
        //<![CDATA[            
            $(document).ready(function(){                
                $('#btn_back').click(function(){                    
                	//history.back(-1);
                });
            });
            
            function gobackapp(){
    			document.location.href = '<c:url value="/mobile/main.do" />';
    		}
        //]]>
        </script>
        
    </head>
	<body>
	<div id="wrap">
		<div id="container" style="width:100%;">
			<div class="mgt_5" style="width: 100%; height: 28px; repeat-x;"></div>
	           <div style="padding-top:120px; margin-left:0px;  width: 100%; float: left;">
	            <table width="100%" border="0" style="text-align: center; font-size:30px;color:#c63d00; font-weight:bold;" class="tb_1">
	            	<tr>
	            		<td>
	            			불편을 드려 죄송합니다.<br/>
	            			관리자에게 문의하여 주시기 바랍니다.<br/><br/>
	            			<button type="button" class="btn_st1" id="" onclick="javascript:history.back(-1);">되돌아가기</button>
	            		</td>
	            	</tr>
	            </table>
	         </div>
	      </div>
      </div>
    </body>
</html>