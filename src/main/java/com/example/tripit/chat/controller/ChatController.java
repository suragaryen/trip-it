package com.example.tripit.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.tripit.chat.entity.ChatMessage;
import com.example.tripit.chat.service.ChatService;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;
    //http://localhost:8080/chat/room?roomId=1 
    // 채팅방 열기
    @GetMapping("/chat/room")
    public String showRoom(@RequestParam("roomId") int roomId) {
        return "chat/room";
    }

    // 채팅 메세지 전송
    @RequestMapping("/chat/doAddMessage")
    @ResponseBody
    public Map<String, Object> doAddMessage(@RequestParam("roomId") int roomId, @RequestParam("userId") int userId, @RequestParam("content") String content) {
        chatService.addMessage(roomId, userId, content);

        Map<String, Object> rs = new HashMap<>();
        rs.put("resultCode", "S-1");
        rs.put("msg", "채팅 메시지가 추가되었습니다.");
        return rs;
    }

  //http://localhost:8080/chat/getMessages
    // 채팅 메세지 가져오기
    @GetMapping("/chat/getMessages")
    @ResponseBody
    public List<ChatMessage> getMessages() {
        return chatService.getMessages();
    }

    // 새로운 메세지 출력
    @GetMapping("/chat/getMessagesFrom")
    @ResponseBody
    public Map<String, Object> getMessages(@RequestParam("roomId") int roomId, @RequestParam("fromId") Long fromId) {
        List<ChatMessage> messages = chatService.getMessagesFrom(roomId, fromId);

        Map<String, Object> rs = new HashMap<>();
        rs.put("resultCode", "S-1");
        rs.put("msg", "새 메세지들을 가져왔습니다.");
        rs.put("messages", messages);
        return rs;
    }

    // 모든 메세지 삭제
    @GetMapping("/chat/clearAllMessages")
    @ResponseBody
    public Map<String, Object> clearAllMessages() {
        chatService.clearAllMessages();

        Map<String, Object> rs = new HashMap<>();
        rs.put("resultCode", "S-1");
        rs.put("msg", "모든 메세지들을 삭제 하였습니다.");
        return rs;
    }
    
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
    

}
