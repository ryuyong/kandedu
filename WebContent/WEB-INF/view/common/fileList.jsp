<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
	//alert('test');
	function fn_downFile(file_id, file_sn){
		window.open("<c:url value='/common/fildDownloadup.do?file_id="+file_id+"&file_sn="+file_sn+"'/>");
	}	
	
	function fn_deleteFile(file_id, file_sn) {
		//var frm = $('#frmfile')[0];
		//forms = document.getElementsByTagName("form");
		var form = $('#frmfile')[0];
		/*for (var i = 0; i < forms.length; i++) {
			if (typeof(forms[i].fileId) != "undefined" &&
					typeof(forms[i].fileSn) != "undefined") {
				form = forms[i];
			}
		}*/
		form.file_id.value = file_id;
		form.file_sn.value = file_sn;
		form.action = "<c:url value='/common/deleteFileInfs.do'/>";
		form.submit();
	}
	
</script>
<c:if test="${!empty result}">
 <a href="#LINK" onclick="javascript:fn_downFile('<c:out value="${result.file_id}"/>','<c:out value="${result.file_sn}"/>')">
 <c:out value="${result.ori_file_nm}"/>&nbsp;[<c:out value="${result.file_size}"/>&nbsp;byte]<br/>
 </a>	       
 </c:if>
 <c:if test="${empty result}">
 	&nbsp;
 </c:if>