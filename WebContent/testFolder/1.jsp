<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" language="javascript">
function noBorderWin(fileName,w,h,titleBg,moveBg,titleColor,titleWord,scr){
	alert("a");
pop=window.open("","_blank","fullscreen=yes"); //打开一个全屏窗口。
pop.resizeTo(w,h); //用resize()方法将窗口定制成自己想要的大小。
pop.moveTo((screen.width-w)/2,(screen.height-h)/2); //用moveTo()方法将窗口移到屏幕中心。
pop.document.writeln(contents); //将窗口内容(即变量contents)写进去。

}
	function callCheck(obj) {
		alert("aa");
		alert(obj.checked);
		if (obj.checked)
			a
		alert(boolean(obj.value));
		if (boolean(obj.value))
			alert("相等");
		else
			alert("不等");
	}
</script>
<body>
	<input type='checkbox' value=true title="car" onclick="callCheck(this)"
		name="id">
	<label>汽车人来吧</label>
	<a href='#' onclick=noBorderWin('test.html','400','240','#000000','#333333','#CCCC','一个无边窗口的测试例子','yes');>点击此处将弹出NBW窗口</a>
<script type="text/javascript">

</script>

</body>
</html>