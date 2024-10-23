<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<style>
	form > label {
		font-size: 12px;
		color: gray;
	}
</style>

</head>
<body>

		<script>
			$(function(){
				
				$("input[type=file]").on('change', function(evt){
					
					const files = evt.target.files; // FileList {0:File, 1:File, ...} 
					console.log(files);
					
					
					for(let i=0; i<files.length; i++){
						if(files[i].size > 10 * 1024 * 1024) {
							alert("첨부파일의 최대 크기는 10MB입니다.");
							evt.target.value = ""; // 선택한 파일 초기화
						}
					}
					
				})
				
			})
		</script>


		<h2>1. 한 개의 첨부파일 업로드 테스트</h2>
		<form action="${ contextPath }/board/insert1.do" method="post" enctype="multipart/form-data">
			게시글 제목 : <input type="text" name="boardTitle"> <br>
			게시글 내용 : <textarea name="boardContent"></textarea> <br>
			첨부파일 : <input type="file" name="uploadFile"> <br>
			<label>첨부파일 사이즈는 10MB 이하여야 됩니다.</label> <br><br>
			
			<button type="submit">등록</button>
		</form>
		

    <h2>2. 다중 첨부파일 업로드 테스트</h2>

    <h2>3. 비동기식으로 첨부파일 업로드 테스트</h2>

    <h2>4. 첨부파일 목록 조회</h2>

</body>
</html>