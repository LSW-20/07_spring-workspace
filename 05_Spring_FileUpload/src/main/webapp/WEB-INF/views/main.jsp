<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>
   form>label {
      font-size: 12px;
      color: gray;
   }
</style>
</head>
<body>
   <script>/* $(function): 요소가 다 만들어지고 스크립트실행  */
      $(function(){
         $("input[type=file]").on('change',function(evt){
            const files = evt.target.files;//FileList타입 {0:File,1:File..}
            console.log(files);
            
            let totalSize = 0;                   //File전체용량
            
            for(let i=0;i<files.length;i++){//File객체에 접근
               if(files[i].size > 10 * 1024 * 1024){//하나의 파일 크기가 10메가 초과일 경우
                  alert("첨부 파일의 최대 크기는 10MB.");
                  evt.target.value="";
                  return;   
               }
               totalSize += files[i].size;      //누적해서 파일 용량 더해주기
               
               if(totalSize > 100 * 1024 * 1024){//전체 첨부파일 사이즈가 100메가 초과일 경우
                  alert("총 용량의 최대 크기는 100MB입니다.");
                  evt.target.value="";
                  return;
               }
               
            }//for
            
         })/* 이런 function: eventHandler */
      })
   </script>
   
   <h2>1. 한 개의 첨부파일 업로드 테스트</h2>
   <form action="${ contextPath }/board/insert1.do" method="post" enctype="multipart/form-data">
      <!-- 파일이 파일로 넘어가게 하려면 enctype작성필요 -->
      게시글 제목 : <input type="text" name="boardTitle"><br>
      게시글 내용 : <textarea name="boardContent"></textarea><br>
      첨부파일 : <input type="file" name="uploadFile"><br>
      <label>첨부 파일 사이즈 10MB 이하</label><br><br>
      
      <button type="submit">등록</button>
   </form>
   
   <h2>2. 다중 첨부파일 업로드 테스트</h2>
   <form action="${ contextPath }/board/insert2.do" method="post" enctype="multipart/form-data">
      게시글 제목 : <input type="text" name="boardTitle"><br>
      게시글 내용 : <textarea name="boardContent"></textarea><br>
      첨부파일 : <input type="file" name="uploadFile" multiple><br>
      <label>각 첨부 파일 사이즈는 10MB 이하, 총100MB이하여야 합니다.</label><br><br>
      
      <button type="submit">등록</button>
   </form>   
   
   <h2>3. 비동기식 첨부파일 업로드 테스트</h2>
   <div id="async_test">
      게시글 제목 : <input type="text" id="title"><br>
      게시글 내용 : <textarea id="content"></textarea><br>
      첨부파일 : <input type="file" id="file"><br><br>
      
      <button type="button" id="submit">등록</button>
   </div>
   
   <script>
      $(function(){
         $("#submit").on("click",function(){
            
            //ajax게시글 등록(요청시 전달값 : 제목,내용,파일)
            //가상의 form요소에 담아서 전달
            //첨부파일 전달 해야 할 경우->FormData객체(가상의 form요소)에 담아서 전달
            let formData = new FormData();//javaScript객체
            formData.append("boardTitle",document.getElementById("title").value);
            formData.append("boardContent",document.getElementById("content").value);
            formData.append("uploadFile",document.getElementById("file").files[0]);//File객체 담기. files[]
            
            $.ajax({
               url: '${contextPath}/board/ajaxInsert.do',
               type: 'post',
               data: formData,//요청시 전달값 이미 담아놨음
               //첨부파일 기능 있을 시 ㅍ평소와 다르게 아래 두가지 넣음
               processData: false,//false선언시 
               contentType: false,
               success: function(result){
                  if(result == "SUCCESS"){
                     alert("등록 성공");
                     location.reload();//새로고침 진행
                  }else{                     
                     alert("등록 실패");
                  }
               },
               error: function(){
                  console.log()
               }
            })
            
         })
      })
   </script>
   
   
   <h2>4. 첨부파일 목록 조회(비동기식)</h2>
   <button onclick="fn_selectAttachList();">조회</button>
   <div id="result">
      
   </div>
   
   <script>
      function fn_selectAttachList(){
         $.ajax({
            url: '${contextPath}/board/atlist.do',
            success: function(resData){
               console.log(resData);
               
               let a = '';
               for(let i=0;i<resData.length;i++){
//                  a += '<a href=${contextPath}'+resData[i].filePath+'/'+resData[i].filesystemName+'" download="'+resData[i].originalName+'">'+resData[i].originalName + '</a><br>';
                  a += '<a href="${contextPath}' + resData[i].filePath + '/' + resData[i].filesystemName + '" download="' + resData[i].originalName + '">' + resData[i].originalName + '</a><br>'
                     
                  
                  
                  
               }
               
               $("#result").html(a);
               
            }
         })
      }
   </script>
   
   <hr>
   <h2>번외. 내용 작성란에 에디터 적용 시키기</h2>
   <p>
      에디터 기능 사용 시 내용 작성 시 폰트 수정, 정렬, 이미지 삽입 등등이 가능함<br>
      위의 과정 들이 반영 된 html요소가 넘어감 -> DB에 기록 <br>
      html태그가 같이 섞여있는 구문을 뿌리면 적용 된 채로 보이게<br>
      > 에디터 api : summernote, ckeditor, 등등
   </p>
   
   <form action="${contextPath }/board/editor/insert.do" method="post">
      게시글 제목 : <input type="text" name="boardTitle"><br>
      게시글 내용 : <textarea id="summernote" name="boardContent"></textarea><br>
      <button type="submit">등록</button>
   </form>
   <!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.js"></script>
   <script>
      $(document).ready(function() {
           $('#summernote').summernote({
              width: 512,
              height: 500,
              placeholder: 'hello world',
              callbacks: {
                 onImageUpload: function(images) {
                    //이미지 들어오는 순간 자동 실행
                    //비동기식으로 이미지 업로드
                    for(let i=0; i<images.length;i++){
                       
                       let formData = new FormData();
                       formData.append('image',images[i]);
                       
                       $.ajax({
                          url: '${contextPath}/board/editor/imageUpload.do',
                          type: 'post',
                          data: formData,
                          processData: false,
                          contentType: false,
                          async: false,//동기방식으로 처리하겠다.
                          success: function(src){
                             //파일 업로드 후에 저장경로+저장파일명을 응답데이터로 받아
                             //현재 에디터의 <img src속성값을 응답데이터로 변경>
                             $("#summernote").summernote('insertImage',"${contextPath}"+src);
                          }
                       })
                    }
                 }
              }
           });
         });   
   </script>   
   
		<h2>번외. 에디터로 등록한 게시글 조회해보기</h2>
		<form id="editor_search">
			조회할 글번호 : <input type="text" name="boardNo">
			<button type="button" onclick="fn_selectBoard();">검색</button>
		</form>
		
		<h4>조회결과</h4>
		
		<b>제목 : </b> <span id="title-result"></span> <br>
		<b>내용</b>
		<div id="content-result"></div>
      
    <script>
    	function fn_selectBoard(){
    		$.ajax({
    			url: '${contextPath}/board/editor/detail.do',
    			type: 'get',
    			data: $('#editor_search').serialize(), // "boardNo=xx"
    			success: function(resData){ // {boardNo:xx, boardTitle:xx, boardContent:xx}
    				$('#title-result').text(resData.boardTitle);
    				$('#content-result').html(resData.boardContent);
    			}
    			
    		})
    	}
    </script>  
      
      
      
      
      
</body>
</html>