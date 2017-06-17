function keypressSearch() {
	if($('#search_btn').length) {
		Search(1);
	}
}


//한글입력불가
function checkHan(formName) {
	var value = formName.value;
	for(i=0;i<value.length;i++) {
		var a = value.charCodeAt(i);
		if(a > 128) {
			alert("한글 입력 금지");
			formName.value = "";
			formName.focus();
			break;
		}
	}
}