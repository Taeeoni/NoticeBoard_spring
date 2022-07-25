<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<style>
input[name="title"] {
	width: 500px;
}

.button-28 {
	appearance: none;
	background-color: transparent;
	border: 2px solid #1A1A1A;
	border-radius: 10px;
	box-sizing: border-box;
	color: #3B3B3B;
	cursor: pointer;
	display: inline-block;
	font-family: Roobert, -apple-system, BlinkMacSystemFont, "Segoe UI",
		Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji",
		"Segoe UI Symbol";
	font-size: 10px;
	font-weight: 600;
	line-height: normal;
	margin: 0;
	min-height: 40px;
	min-width: 0;
	outline: none;
	text-align: center;
	text-decoration: none;
	transition: all 300ms cubic-bezier(.23, 1, 0.32, 1);
	user-select: none;
	-webkit-user-select: none;
	touch-action: manipulation;
	width: 10%;
	will-change: transform;
}

.button-28:disabled {
	pointer-events: none;
}

.button-28:hover {
	color: #fff;
	background-color: #1A1A1A;
	box-shadow: rgba(0, 0, 0, 0.25) 0 8px 15px;
	transform: translateY(-2px);
}

.button-28:active {
	box-shadow: none;
	transform: translateY(0);
}

.container {
	display: flex;
}

.item {
	width: 130px;
	padding-right: 20px;
}

input[name=user] {
	width: 130px;
}
</style>

<body>

	<form method='post'>
		<table cellspacing=1 width=600 border=1>

			<tr>
				<td width=100>번호</td>
				<td colspan=2><input type='text' name='id'
					value='${noticeList.getId()}' readonly /></td>
			</tr>
			<tr>
				<td width=100>답글번호</td>
				<td><input type='text' name='rootid'
					value='${noticeList.getRootid()}' readonly /></td>
			</tr>
			<tr>
				<td width=100>답글순서</td>
				<td><input type='text' name='recnt'
					value='${noticeList.getRecnt()}' readonly /></td>
			</tr>
			<tr>
				<td width=100>답글레벨</td>
				<td><input type='text' name='relevel'
					value='${noticeList.getRelevel()}' readonly /></td>
			</tr>
			<tr>
				<td width=100>제목</td>
				<td colspan=2><input type='text' name='title'
					value='${noticeList.getTitle()}' /></td>
			</tr>
			<tr>
				<td width=100>조회수</td>
				<td colspan=2>${noticeList.getViews()}</td>
			</tr>
			<tr>
				<td width=100>일자</td>
				<td colspan=2>${noticeList.getDate()}</td>
			</tr>
			<tr>
				<td width=100>내용</td>
				<td colspan=2><textarea name='content' cols=70 rows=15>${noticeList.getContent()}</textarea></td>
			</tr>
		</table>
		</br>
		<div>전체 댓글 ${noticeComment.size()}개</div>
		<img src="/resources/img/blueLine.png" width="600px" height="40px">
		<c:forEach var="noticeComment" items="${noticeComment}">

			<div class='container'>
				<div class='item'>${noticeComment.user}</div>
				<div class='item'>
					<textarea cols=60 rows=2 readonly>${noticeComment.comment}</textarea>
				</div>
			</div>
			</br>
		</c:forEach>
		<img src="/resources/img/blueLine.png" width="600px" height="40px">
		</br> <input type="text" name='user' placeholder="닉네임을 입력하세요" />
		<textarea name='comment' cols=50 rows=2></textarea>
		<input type="submit" value="댓글 달기"
			formaction='http://localhost:8889/home/addComment' />
		</td> </br> </br>
		<div>
			<input formaction='/home' type='submit' value='취소' /> <input
				formaction='/home/revise' type='submit' value='수정' /> <input
				formaction='/home/delete' type='submit' value='삭제' /> <input
				formaction='/home/reInsert' type='submit' value='답글달기' />
		</div>
	</form>

</body>
</html>