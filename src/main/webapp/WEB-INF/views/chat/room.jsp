<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>제발</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript">

let Chat_roomId = ${param.roomId};

// 폼이 발송되기전에 실행
// 엔터 발생시 실행
function Chat_addMessage(writer, body){
	$.post(
			'/chat/doAddMessage',
			{
				roomId:Chat_roomId,
				writer:writer,
				body:body
			},
			function(data){
// 				console.log(data.msg);
// 				let chatMessage = {
// 					writer:"홍길동",
// 					body:"ㅋㅋㅋ"
// 				};
// 				Chat_drawMessage(chatMessage);
			},
			'json'
			);
}// Chat_addMessage() end

//메제지를 채팅방에 보여줌
	function Chat_drawMessage(chatMessage){
	let html = chatMessage.writer + ' : ' + chatMessage.body;
	
	$('.chat_messages').prepend('<div>' + html + '</div>');
}

// 가장 마지막에 받은 메세지를 0으로 저장
let Chat_lastLoadMessageId = 0;


//새로운 메세지를 채팅방에 보여줌
	function Chat_loadNewMessages(){
		$.get(
			'/chat/getMessagesFrom',
			{
				roomId:Chat_roomId,
				from:Chat_lastLoadMessageId + 1
			},
			function(data){
// 				console.log(data.messages);
				for(let i = 0; i < data.messages.length; i++){
					
					Chat_drawMessage(data.messages[i]);
					
					Chat_lastLoadMessageId = data.messages[i].id;
				}
			},
			'json'
		);
	}
setInterval(Chat_loadNewMessages, 2000);

//메세지 날리는 form
	function submitChatMessageForm(form){
		let writer = form.writer.value.trim();	
		let body = form.body.value;
		//공백제거
	
		//유효성검사
		if(writer.length == 0){
			alert('작성자를 입력해주세요.');
			form.writer.focus();
			
			return false;
		}// if() end

		if(body.length == 0){
			alert('내용를 입력해주세요.');
			form.body.focus();
			
			return false;
		}// if() end
		
		
		
		form.body.body = '';
		form.body.focus();
		
		
		Chat_addMessage(writer, body);
	}// submitChatMessageForm(form) end
	
	
</script>

</head>
<body>

	<h1>${param.roomId}번방</h1>
	<form onsubmit="submitChatMessageForm(this); return false;">
		<div>
			<input type="text" name="writer" placeholder="작성자" autocomplete="name">
		</div>
		<div>
			<input type="text" name="body" placeholder="내용" autocomplete="name">
		</div>
		<div>
			<input type="submit" value="작성">
		</div>
	</form>
	<div class="chat_messages"></div>
	
</body> 
</html>