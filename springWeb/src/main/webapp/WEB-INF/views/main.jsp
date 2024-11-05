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

  <div class="container p-3">

    <!-- Header, Nav start -->
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
    <!-- Header, Nav end -->


    <!-- Section start -->
    <section class="row m-3" style="min-height: 500px">

      <div class="container border p-5 m-4 rounded">
        <h2 class="m-4">webSocket을 이용하여 실시간으로 통신하기</h2>
        
      
      	<p>
      		실시간으로 알림을 발생시킨다거나 채팅을 할 때 주로 websocket 사용 <br><br>
      		
      		<c:if test="${ not empty loginUser }">
      			<a class="btn btn-secondary" href="${ contextPath }/chat/room.do">채팅방 입장</a>
      		</c:if>
      		
      	</p>
      
      </div>

    </section>
    <!-- Section end -->
     

    <!-- Footer start -->
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
    <!-- Footer end -->

  </div>
  
</body>
</html>