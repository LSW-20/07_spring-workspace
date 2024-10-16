<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="${contextPath}/assets/js/jquery-3.7.1.min.js"></script>

</head>
<body>

	<h3>도서 목록 페이지</h3>

	<table border="1">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>저자</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>1</td>
				<td>수학의 정석</td>
				<td>나수학</td>
			</tr>
			<tr>
				<td>2</td>
				<td>자바의 정석</td>
				<td>강자바</td>
			</tr>
			<tr>
				<td>3</td>
				<td>스프링의 정석</td>
				<td>이봄</td>
			</tr>
		</tbody>
	</table>

	<script>
		$(function(){
			$("tbody>tr").on("click", function(){
				location.href = '${contextPath}/book/detail.do?no=' + $(this).children().eq(0).text();
			})
		})
	</script>

</body>
</html>