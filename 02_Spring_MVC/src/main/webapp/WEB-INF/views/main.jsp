<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="${ contextPath }/resources/js/sample.js"></script>

<script src="${ contextPath }/assets/js/jquery-3.7.1.min.js"></script>

</head>
<body>

	<!-- / 또는 /main.do라는 url mapping으로 요청시 해당 /WEB-INF/views/main.jsp가 보여지도록 한다. -->
	<h1>메인페이지입니다</h1>

	<h3>1. 정적 자원 확인</h3>
	<img src="${ contextPath }/resources/images/1.jpeg" width="100" onclick="test();">
	<img src="${ contextPath }/assets/images/2.jpg" width="100" id="img">
	
	<script>
		$(function(){
			$('#img').on("click", () => {
				alert("어서오세요");
			})
		})
	</script>
	
	<hr>


	<h3>2. 응답페이지 보여지게 하는 연습 (포워딩, redirect)</h3>

	<!-- <a href="${contextPath}/list.bk">도서목록페이지로 이동</a> -->
	<a href="${contextPath}/book/list.do">도서목록페이지로 이동</a> <br>
	<a href="${contextPath}/book/enrollForm.do">도서등록페이지로 이동</a>



	<hr>


	<h3>3. 요청시 전달되는 파라미터 처리하는 연습 (request의 parameter)</h3>

	<!-- <a href="${contextPath}/detail.mem?no=1">회원상세조회</a> -->
	<a href="${contextPath}/member/detail.do?no=1">회원상세조회</a> <br><br><br>

	<!-- (1) HttpServletRequest 방법 -->
	<form action="${ contextPath }/member/enroll1.do" method="post">
		이름 : <input type="text" name="name"> <br>
		나이 : <input type="text" name="age"> <br>
		주소 : <input type="text" name="address"> <br>
		<button>등록</button>
	</form>

	<br><br>


	<!-- (2) @RequestParam 방법 -->
	<form action="${ contextPath }/member/enroll2.do" method="post">
		이름 : <input type="text" name="name"> <br>
		나이 : <input type="text" name="age"> <br>
		주소 : <input type="text" name="address"> <br>
		<button>등록</button>
	</form>

	<!-- <a href="${contextPath}/detail2.mem?no=1">회원상세조회</a> -->
	<a href="${contextPath}/member/detail2.do?no=1">회원상세조회</a> <br><br><br>

</body>
</html>