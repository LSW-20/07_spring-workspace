<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
    .chat{width:400px; margin:auto; padding:10px; border:1px solid lightgray;}
    .chat-area{height:500px; overflow: auto;}

     .chat-message{margin:10px 0px;}
     .chat-message.mine{display: flex; justify-content:flex-end;}
     
    .chat-message .send-message{
       padding: 5px 7px;
       border-radius: 10px;
       max-width: 190px;
       font-size:0.9em;
       white-space: pre-line;
    }
    .chat-message.other .send-message{background: lightgray;}
    .chat-message.mine .send-message{background: #FFE08C;}
 
    .chat-user {
       text-align:center; 
       border-radius:10px;   
       background: lightgray;
       opacity: 0.5;
       margin: 20px 0px;
       color: black;
       line-height: 30px;
    }
 </style>

</head>
<body>
<div class="container p-3">

    <!-- Header, Nav start -->
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <!-- Header, Nav end -->

    <!-- Section start -->
    <section class="row m-3" style="min-height: 500px">

      <div class="container border p-5 m-4 rounded">
        <h2 class="m-4">채팅방</h2>
        
        <div class="chat">
   
          <div class="chat-area">
          
          	<!--  
						<div class="chat-message mine">
	            <div class="send-message">내가보낸 메세지내가보낸 메세지내가보낸 메세지내가보낸 메세지내가보낸 메세지</div>
	          </div>  
	         
	          <div class="chat-message other">
	            <span class="send-user">상대방</span>
	            <div class="send-message">남이보낸 메세지남이보낸 메세지남이보낸 메세지남이보낸 메세지남이보낸 메세지남이보낸 메세지</div>
	          </div>
	          -->
            
          </div>
          
          <div class="input-area">
          
            <div class="form-group">
                <textarea class="form-control" rows="3" id="message" style="resize:none"></textarea>
            </div>
            
            <button type="button" class="btn btn-sm btn-secondary btn-block" onclick="sendMessage();">전송하기</button>
            <button type="button" class="btn btn-sm btn-danger btn-block" onclick="onClose();">퇴장하기</button>
            
          </div>   
          
        </div>
      </div>


			<script src="https://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script> 
			<script>
			
				const sock = new SockJS("${contextPath}/chat"); // 이 구문이 웹소켓 서버와 연결이 되는 구문이다. 이 페이지에 진입한 클라이언트가.
				// - 아까 servlet-context에 /chat url 요청시에 ChatEchoHandler가 동작되게 해놨다.
				// 웹소켓 서버와 연결되는 순간 ChatEchoHandler가 동작된다고 보면 된다.
				// after afterConnectionEstablished 메소드가 실행된다.
				// 웹소켓 서버와 연결됨(즉, ChatEchoHandler의 afterConnectionEstablished 메소드가 실행된다.)
				
				sock.onmessage = onMessage; // 웹소켓에서 해당 클라이언트로 메세지 발송시 자동으로 실행할 함수를 지정(매핑)하는 구문
				sock.onclose = onClose; // 웹소켓과 해당 클라이언트간의 연결이 끊겼을 경우 자동으로 실행할 함수를 지정(매핑)하는 구문
				// 이렇게 3줄이 기본세팅이다.
				
				
				
			
				// 메세지를 출력시키는 영역의 요소
				const $chatArea = $(".chat-area");
				
				
				// 메세지 전송시 실행될 함수
				function sendMessage() {
					sock.send($("#message").val()); // 웹소켓 측으로 메세지를 전송 (ChatEchoHandler의 handleMessage 메소드 자동 실행)
					$("#message").val("");
				}
			
				
				// 나에게 메세지가 왔을 때 실행될 함수
				function onMessage(evt) { // 매개볁수를 하나 둬야한다. 웹소켓에서 클라이언트로 보내는 메세지를 받기 위해 
					// console.log("evt", evt);
					// console.log("evt.data", evt.data);
					
					let msgArr = evt.data.split("|"); // ["메세지유형(chat|entry|exit)", "출력시킬메세지내용", "발신자아이디"];
					
          
          let $chatDiv = $("<div>"); // 채팅창에 append시킬 요소 (메세지 유형별로 다르게 제작)
          // $("<div>")는 기본적으로 빈 <div></div> 요소를 만듭니다. 
          // jQuery 대신 순수 JavaScript로 <div> 요소를 생성하고 설정하려면 let chatDiv = document.createElement("div");
          
          if(msgArr[0] == "chat"){ // 채팅메세지일 경우
        	  $chatDiv.addClass("chat-message")
        	  			  .addClass(msgArr[2] == "${loginUser.userId}" ? "mine" : "other") // 제이쿼리가 좋은게 메소드체이닝이 가능
										.append( $("<div>").addClass("send-message").text(msgArr[1]) ); // innerText로 추가하고 자손으로 추가
        	  			  // 여기가진 내가 보낸 메세지든 남이 보낸 메세지든 같다.
        	  			  
        	  if($chatDiv.hasClass("other")) { // class 관련해선 add remove has 3개가 있다.
        		  $chatDiv.prepend( $("<span>").addClass("send-user").text(msgArr[2]) );
        	  }
										
						// short-circuit으로 단일 if문 대체도 가능. 앞이 true면 뒤가 실행.				
        	  // $chatDiv.hasClass("other") && $chatDiv.prepend( $("<span>").addClass("send-user").text(msgArr[2]) );
        	  		
						
										
          }else { // 입장 또는 퇴장 메세지일 경우
        	  $chatDiv.addClass("chat-user")
        	  				.addClass(msgArr[0])
        	  				.text(msgArr[1]);
          }
          
          
					// 근데 계속 메세지가 추가되어도 맨 위의 메세지만 보여진다. 스크롤바가 생기고 하단으로 고정되려면.
					$chatArea.append($chatDiv);
					$chatArea.scrollTop( $chatArea[0].scrollHeight ); // 스크롤을 항상 하단으로 유지시켜주는 코드.
					
					
				}
				
				
				// 퇴장시 실행될 함수
				function onClose() {
					location.href = "${contextPath}"; // 이 페이지를 빠져나가면 
				}
				
				
				$(document).ready(function(){
					
					// enter 눌렀을 때 메세지 전송, shift+enter 눌렀을 때 줄바꿈 적용
					$("#message").on("keydown", function(evt){
						if(evt.keyCode == 13){
							if(!evt.shiftKey){
								evt.preventDefault();
								sendMessage(); // 메세지 전송시키는 함수
							}
						}
					})
					
				})
				
				
			</script>


    </section>
    <!-- Section end -->

    <!-- Footer start -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
    <!-- Footer end -->

  </div>
</body>
</html>