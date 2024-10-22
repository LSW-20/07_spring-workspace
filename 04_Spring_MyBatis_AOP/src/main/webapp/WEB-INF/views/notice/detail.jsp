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

	<h3>공지사항 상세 페이지</h3>

	<c:choose>
		<c:when test="${ empty n }">
			* 조회된 공지사항이 없습니다.
		</c:when>
		
		<c:otherwise>
			* 조회된 공지사항 정보 <br><br>
			
			번호 : ${ n.no } <br>
			제목 : ${ n.title } <br>
			내용 : ${ n.content } <br><br>
			
			<a href="${ contextPath }/notice/modify.do?no=${n.no}">수정페이지로 이동</a>
		</c:otherwise>
	</c:choose>

</body>
</html>