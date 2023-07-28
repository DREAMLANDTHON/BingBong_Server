package com.bingbong.consult.chatroom.presentation;

import com.bingbong.consult.chatroom.application.ChatRoomService;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;


    @GetMapping("/chatRooms/{id}")
    public ResponseEntity<ChatRoom> findChatRoom(@PathVariable Long id) {
        ChatRoom res = chatRoomService.findChatRoom(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/chatRooms/classrooms/{id}")
    public ResponseEntity<List<ChatRoom>> findChatRoomByClassRoomId(@PathVariable Long id) {
        List<ChatRoom> res = chatRoomService.findChatRoomByClassRoomId(id);
        return ResponseEntity.ok(res);
    }
}



