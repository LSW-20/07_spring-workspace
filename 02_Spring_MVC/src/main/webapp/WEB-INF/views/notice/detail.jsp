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

	<c:if test="${ not empty alertMsg }"> 
		<script>
			alert("${alertMsg}");
		</script>
	</c:if>


	<h3>공지사항 상세 페이지</h3>

	<c:choose>
		<c:when test="${ empty notice }">
			조회된 공지사항이 없습니다.
		</c:when>
		
		<c:otherwise>
			번호 : ${ notice.no } <br>
			제목 : ${ notice.title } <br>
			내용 : ${ notice.content } <br><br>
			
			<a href="${contextPath}/notice/modifyForm.do?no=${notice.no}">수정페이지로 이동</a>
		</c:otherwise>
	</c:choose>
	

</body>
</html>
