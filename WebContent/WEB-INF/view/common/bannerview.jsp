<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
</head>
<script type="text/javascript">
	$(document).ready(function(){
		var width = '${width}';
		var height = '${height}';
		width = parseInt(width,10)+70;
		height = parseInt(height,10)+90;
		//alert(width + "ll" + height);
		window.resizeTo(width, height);
	});
</script>
<body>
<div style="text-align:center;">
	<table cellpadding="0" border=0 cellspacing="0">
	    <tr>
	        <td>
	           <img src="http://110.10.174.113${path}" width="${width}px" height="${height}px" />     
	    	</td>
	    </tr>
	</table>
</div>
</body>
</html>
