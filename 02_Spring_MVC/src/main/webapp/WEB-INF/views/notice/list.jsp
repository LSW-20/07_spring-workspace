<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>공지사항 목록 페이지</h3>
	
	<table border="1">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>내용</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="n" items="${ list }">
				<tr>
					<td>${ n.no }</td>
					<td><a href="${contextPath}/notice/detail.do?no=${ n.no }">${ n.title }</a></td>
					<td>${ n.content }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>