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

	<h3>도서 수정 페이지</h3>

	<form action="${contextPath}/book/modify.do" method="post">
		제목 : <input type="text" name="title" value="수학의 정석"> <br>
		저자 : <input type="text" name="author" value="나수학"> <br><br>
		
		<button type="submit">수정</button>
	</form>

</body>
</html>