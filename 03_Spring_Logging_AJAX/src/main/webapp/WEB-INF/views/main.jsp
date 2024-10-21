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

	<h3>메인페이지 입니다</h3>

	<a href="${contextPath}/member/manage1.do">회원관리1</a> <br>
	<a href="${contextPath}/member/manage2.do">회원관리2</a> <br>

</body>
</html>