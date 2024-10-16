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

	<h3>도서 상세 페이지</h3>
	<div>
		번호 : 1 <br>
		제목 : 수학의 정석 <br>
		저자 : 나수학 <br><br>
		
		<a href="${contextPath}/book/modifyForm.do">수정하기 페이지로 이동</a>
	</div>

</body>
</html>