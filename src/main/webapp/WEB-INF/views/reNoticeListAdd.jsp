<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<style>
input[name="title"] {
	width: 500px;
}



</style>

<script type='text/javascript'>
	$(function () {
		
		console.log("확인");
		
		$("#submit").click(function () {

			if(!confirm("나가게 되면 작성내용이 사라집니다. 정말 나가시겠습니까?")){
				return false;
			} else {
				return true;
			}
		});
		
	});
		
</script>



<body>
	<form method='post'>
		<table cellspacing=1 width=600 border=1>

			<tr>
				<td width=100>번호</td>
				<td>신규 (insert)</td>
			</tr>
			<tr>
				<td width=100>답글번호</td>
				<td><input type='text' name='rootid' value='${noticeList.getRootid()}' readonly/></td>
			</tr>
			<tr>
				<td width=100>답글순서</td>
				<td><input type='text' name='recnt'
					value='${noticeList.getRecnt()}' readonly /></td>
			</tr>
			<tr>
				<td width=100>답글레벨</td>
				<td><input type='text' name='relevel' value='${noticeList.getRelevel()}' readonly/></td>
			</tr>
			
			<tr>
				<td width=100>제목</td>
				<td><input type='text' name='title' value='' /></td>
			</tr>
			<tr>
				<td width=100>일자</td>
				<td><input type='date' name='date' value='${noticeList.getDate()}' readonly/></td>
			</tr>
			<tr>
				<td width=100>내용</td>
				<td><textarea name='content' cols=70 rows=15></textarea></td>
			</tr>
		</table>
		</br>
		</br>
		<div>
			<input formaction='/home' id ='submit' type='submit' value='취소' />
			<input formaction='/home/save/re' type='submit' value='쓰기' />
		</div>
	</form>
</body>
</html>