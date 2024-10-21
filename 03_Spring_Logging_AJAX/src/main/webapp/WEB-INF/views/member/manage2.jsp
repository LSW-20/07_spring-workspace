<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="${contextPath}/resources/js/jquery-3.7.1.min.js"></script>

</head>
<body>
	<h3>회원관리 2번 페이지</h3>

	<form id="mem-form">
		번호 : <input type="text" name="userNo"> <br>
		아이디 : <input type="text" name="userId"> <br>
		비밀번호 : <input type="text" name="userPwd"> <br>
		
		<button type="button" onclick="fn_ajax1();">조회1(아이디, 비번으로 이름 조회)</button>
		<button type="button" onclick="fn_ajax2();">조회2(아이디, 비번으로 이름 조회)</button>
		<button type="button" onclick="fn_ajax3()">조회3(번호로 회원전체정보 조회)</button>
		<button type="button" onclick="fn_ajax4()">전체조회</button>
		<br>
		
		<button type="button" onclick="fn_etc1()">번외1(응답데이터가 다수일 경우)</button>
		<button type="button" onclick="fn_etc2()">번외2(요청시 전달값 다수를 Map으로 받아보기)</button>
	</form>
    
    <hr>
    
    <div id="result">
    	결과가 보여지는 영역
    </div>

    <script>
        // 조회1 버튼 : 아이디랑 비번으로 이름을 조회
        function fn_ajax1() {

            let id = $("input[name=userId]").val();
            let pwd = $("input[name=userPwd]").val();

            $.ajax({
                // 요청
                url: "${contextPath}/member2/detail1.do",
                data: "id=" + id + "&pwd=" + pwd, // 쿼리스트링으로 전달값 작성
                type: "get",

                // 응답
                success: function(resData){
                    $("#result").text(resData);
                },
                error: function(){

                }
            })

        }
    
    
    	function fn_ajax2() {
    		
    		$.ajax({
    			url: "${contextPath}/member2/detail2.do",
    			type: "get",
    			data: $("#mem-form").serialize(), // form의 모든 입력요소들을 직렬화

    			success: function(resData){
    				$("#result").text(resData);
    			},
    			error: function(){
    				console.log("조회2 버튼에 대한 ajax 통신 실패");
    			}
    			
    		})
    		
    	}


		
		// 회원 전체 정보 조회(1명 - dto)
    	function fn_ajax3() {
    		
    		$.ajax({ 
    			url: "${contextPath}/member2/detail3.do",
    			type: "get",
    			data: { no: $('input[name=userNo]').val() },

    			success: function(resData){
					console.log(resData);

					let value = '<ul>'
									+ '<li>번호: ' + resData.userNo + '</li>'
									+ '<li>아이디: ' + resData.userId + '</li>'
									+ '<li>이름: ' + resData.userName + '</li>'
								+ '</ul>';
								
					$('#result').html(value);			

    			},
    			error: function(){
    				console.log("조회3 버튼에 대한 ajax 통신 실패");
    			}
    			
    		})
    		
    	}


		// 회원 전체 정보 조회(여러명 - list)
    	function fn_ajax4() {
    		
    		$.ajax({ 
    			url: "${contextPath}/member2/list.do",

    			success: function(resData){
					console.log(resData); // [{}, {}, {}]

					let table = "<table border='1'>";

					for(let i=0; i<resData.length; i++) {
						table += '<tr>'
									+ '<td>' + resData[i].userNo + '</td>'
									+ '<td>' + resData[i].userId + '</td>'
									+ '<td>' + resData[i].userName + '</td>'
								+ '</tr>';
					}	

					table += "</table>";

					$('#result').html(table);		

    			},
    			error: function(){
    				console.log("전체조회 버튼에 대한 ajax 통신 실패");
    			}
    			
    		})
    		
    	}



		// 응답데이터가 다수일 경우 Map에 담아서 반환
    	function fn_etc1() {
    		
    		$.ajax({ 
    			url: "${contextPath}/member2/etc1.do",

    			success: function(resData){
					console.log(resData); 
					console.log('no', resData.no);
					console.log('list', resData.list);
					console.log('m', resData.m);

    			},
    			error: function(){
    				console.log("번외1 버튼에 대한 ajax 통신 실패");
    			}
    			
    		})
    		
    	}


		// 요청시 전달되는 다수의 파라미터들을 Map으로 받아보기
		function fn_etc2() {
		
			$.ajax({ 
				url: "${contextPath}/member2/etc2.do",
				type: 'post',
				data: JSON.stringify( { // json 객체 => json 문자열 변환
					no: 10,
					name: '김잔디',
					arr: ['홍길동', '김말똥', 20]
				}),
				contentType: 'application/json',

				success: function(resData){
					console.log(resData); 

				},
				error: function(){
					console.log("번외2 버튼에 대한 ajax 통신 실패");
				}
				
			})
    		
    	}
    </script>
    
</body>
</html>