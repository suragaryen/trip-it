package com.example.tripit.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.tripit.chat.dto.ChatMessage;
import com.example.tripit.chat.service.ChatService;

import aj.org.objectweb.asm.Attribute;
import ch.qos.logback.core.model.Model;

@Controller
public class ChatController {

	@Autowired
	ChatService chatService;

	// 채팅방 열기
	@GetMapping("/chat/room")
	public String showRoom(@RequestParam("roomId") int roomId) {
//		ModelAndView mav = new ModelAndView();
//	    mav.addObject("roomId", roomId);
//	    mav.setViewName("chat/room");
		return "chat/room";
	}

	// 채팅 메세지 전송
	@PostMapping("/chat/doAddMessage")
	@ResponseBody
	public Map<String, Object> doAddMessage(@RequestParam("roomId") int roomId, @RequestParam("writer") String writer,
			@RequestParam("body") String body) {
		Map<String, Object> rs = new HashMap<>();

		chatService.addMessage(roomId, writer, body);

		rs.put("result", "S-1");
		rs.put("msg", "채팅 메시지가 추가되었습니다.");
		return rs;
	}// doAddMessage() end

	// 채팅 메세지 저장
	@GetMapping("/chat/getMessages")
	@ResponseBody
	public List<ChatMessage> getMessages() {

		return chatService.getMessages();
	}// doAddMessage() end

	//새로운 메세지 출력
	@GetMapping("/chat/getMessagesFrom")
	@ResponseBody
	public Map<String, Object> getMessages(@RequestParam("roomId") int roomId, @RequestParam("from") int from) {
		List<ChatMessage> messages = chatService.getMessagesFrom(roomId, from);

		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "s-1");
		rs.put("msg", "새 메세지들을 가져왔습니다.");
		rs.put("messages", messages);

		return rs;
	}// doAddMessage() end

	// 데이터 삭제
	@GetMapping("/chat/clearAllMessages")
	@ResponseBody
	public Map<String, Object> clearAllMessages() {
		chatService.clearAllMessages();

		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "s-1");
		rs.put("msg", "모든 메세지들을 삭제 하였습니다.");
		return rs;
	}// doAddMessage() end

}// class end