<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제발</title>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript">


let Chat_roomId = ${param.roomId};
// 폼이 발송되기 전에 실행
// 엔터 발생시 실행
function Chat_addMessage(userId, content){
	$.post(
		'/chat/doAddMessage',
		{
			roomId: Chat_roomId,
			userId: userId,
			content: content
		},
		function(data){
// 			if (data.resultCode === 'S-1') {
// 				console.log(data.msg);
// 			}
		},
		'json'
	);
}

// 메세지를 채팅방에 보여줌
function Chat_drawMessage(chatMessage){
	let html = '<strong>' + chatMessage.userId + '</strong>: ' + chatMessage.content+ ' (' + chatMessage.sendTime + ')';
	$('.chat_messages').prepend('<div>' + html + '</div>');
}

// 가장 마지막에 받은 메세지 ID
let Chat_lastLoadMessageId = 0;

// 새로운 메세지를 채팅방에 보여줌
function Chat_loadNewMessages(){
	$.get(
		'/chat/getMessagesFrom',
		{
				
			roomId: Chat_roomId,
			fromId: Chat_lastLoadMessageId
		},
		function(data){
			if (data.messages) {
				for (let i = 0; i < data.messages.length; i++){
					Chat_drawMessage(data.messages[i]);
					Chat_lastLoadMessageId = data.messages[i].messageId;
				}
			}
		},
		'json'
	);
}
//초기 로딩 시 새로운 메시지를 가져와 화면에 표시
$(document).ready(function() {
    Chat_loadNewMessages();
});

// 새로운 메시지를 주기적으로 가져와 화면에 표시
setInterval(Chat_loadNewMessages, 1000);

// 메세지 날리는 form
function submitChatMessageForm(form){
	let userId = form.userId.value.trim();
	let content = form.content.value.trim();

	// 유효성 검사
	if (userId.length == 0){
		alert('작성자를 입력해주세요.');
		form.userId.focus();
		return false;
	}

	if (content.length == 0){
		alert('내용을 입력해주세요.');
		form.content.focus();
		return false;
	}

	Chat_addMessage(userId, content);

	form.content.value = '';
	form.content.focus();

	return false;
}




</script>

</head>
<body>
	<h1>${param.roomId}번방</h1>
	<form onsubmit="submitChatMessageForm(this); return false;">
		<div>
			<input type="text" name="userId" placeholder="작성자" autocomplete="name">
		</div>
		<div>
			<input type="text" name="content" placeholder="내용" autocomplete="name">
		</div>
		<div>
			<input type="submit" value="작성">
		</div>
	</form>
	<div class="chat_messages"></div>
	
</body>
</html>
