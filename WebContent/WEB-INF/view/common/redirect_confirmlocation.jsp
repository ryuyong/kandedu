<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>문제풀이</title>
<script type="text/javascript">
//<![CDATA[
var message = '${message}';
if(message != '') {
	if(confirm(message)) {
		var returnurl = '${redirecturlok}';
		parent.location.replace(returnurl);
	} else {
		var returnurl = '${redirecturlno}';
		parent.location.replace(returnurl);
	}
}

//]]>
</script>
</head>
<body>
</body>
</html>