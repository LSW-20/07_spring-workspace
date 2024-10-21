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

	<c:choose>
		<c:when test="${ empty list }">
			존재하는 공지사항이 없습니다
		</c:when>
		
		<c:otherwise>
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
							<td>${ n.title }</td>
							<td>${ n.content }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>

</body>
</html>