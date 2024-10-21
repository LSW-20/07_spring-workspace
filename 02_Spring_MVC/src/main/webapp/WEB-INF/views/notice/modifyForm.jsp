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

	<h2>공지사항 수정 페이지</h2>

	<form action="${contextPath}/notice/update.do" method="post">
		<input type="hidden" name="no" value="${notice.no}">
		제목 : <input type="text" name="title" value="${notice.title}"> <br>
		내용 : <textarea name="content">${ notice.content }</textarea>
		
		<input type="submit" value="수정">
	</form>

</body>
</html>