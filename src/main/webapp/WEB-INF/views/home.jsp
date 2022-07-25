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
body {
	color: #666;
	font: 14px/24px "Open Sans", "HelveticaNeue-Light",
		"Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial,
		"Lucida Grande", Sans-Serif;
}

table {
	border-collapse: separate;
	border-spacing: 0;
	width: 800px;
	
}

th, td {
	padding: 6px 15px;
}

th {
	background: #42444e;
	color: #fff;
	text-align: left;
}

tr:first-child th:first-child {
	border-top-left-radius: 6px;
}

tr:first-child th:last-child {
	border-top-right-radius: 6px;
}

td {
	border-right: 1px solid #c6c9cc;
	border-bottom: 1px solid #c6c9cc;
	text-align: left;
}

td:first-child {
	border-left: 1px solid #c6c9cc;
}

tr:nth-child(even) td {
	background: #eaeaed;
}

tr:last-child td:first-child {
	border-bottom-left-radius: 6px;
}

tr:last-child td:last-child {
	border-bottom-right-radius: 6px;
}

.pagination {
	display: inline-block;
}

.pagination a {
	color: black;
	float: left;
	padding: 8px 16px;
	text-decoration: none;
	transition: background-color .3s;
	border: 1px solid #ddd;
}

.pagination a.active {
	background-color: #78b9f7;
	color: white;
	border: 1px solid #78b9f7;
}

.pagination a:hover:not(.active) {
	background-color: #ddd;
}

div, table {
	text-align: center;
	margin-left: auto;
	margin-right: auto;
}

input[name=add] {
	width: 100px;
	height: 50px;
}



</style>

<body>
 <div>
	<h3>게시판</h3>

	<table>

		<tr>
			<th width=100>번호</th>
			<th width=400>제목</th>			
			<th width=100>조회수</th>
			<th width=100>등록일</th>
		</tr>
		<c:forEach var="noticeList" items="${noticeListAll}">
			<tr>
				<td>${noticeList.id}</td>
				<td><a href="/home/noticeOne?id=${noticeList.id}">${noticeList.title}</a></td>
				<td>${noticeList.views}</td>
				<td>${noticeList.date}</td>
			</tr>
		</c:forEach>

	</table>
	</br>
	<div class="pagination">	
		<c:if test="${p.pageStart != 1}">
		<a href='/home?page=${p.ppPage -1}&size=${p.pageSize}'>❮❮</a>
		<a href='/home?page=${p.pPage -1}&size=${p.pageSize}'>❮</a>
		</c:if>
		
		<c:set var="isBreak" value="false"/>
		<c:set var="nowRed" value=""/>
		<c:forEach var="i" begin="0" end ="9" step="1">	
			<c:if test="${isBreak != true}">
				<c:if test="${p.pageStart + i == p.cPage}">
					<c:set var="nowRed" value="class='active'"/>
				</c:if>
				<a href='/home?page=${p.pageStart + i -1}&size=${p.pageSize}' ${nowRed}>${p.pageStart + i}</a>
				<c:set var="nowRed" value=""/>
				<c:if test="${p.pageStart + i == p.nnPage}">				
					<c:set var="isBreak" value="true"/>
				</c:if>
			</c:if>
		</c:forEach>
		
		<c:if test="${p.isLast == 0}">
		<a href='/home?page=${p.npage -1}&size=${p.pageSize}'>❯</a>
		<a href='/home?page=${p.nnPage -1}&size=${p.pageSize}'>❯❯</a>
		</c:if>

	</div>

	</br>
	<form method='post' action='/home/insert'>
		<input id="submit" name='add' type="submit" value="추가" />
	</form>
</div>
	
</body>
</html>