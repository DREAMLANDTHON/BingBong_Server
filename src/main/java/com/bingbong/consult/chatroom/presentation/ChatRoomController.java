package com.bingbong.consult.chatroom.presentation;

import com.bingbong.consult.chatroom.application.ChatRoomService;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.chatroom.presentation.response.ChatRoomResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "Bearer Authentication")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;


    @GetMapping("/chatRooms/{id}")
    public ResponseEntity<ChatRoom> findChatRoom(@PathVariable Long id) {
        ChatRoom res = chatRoomService.findChatRoom(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/chatRooms/classrooms/{id}")
    public ResponseEntity<List<ChatRoomResponse>> findChatRoomByClassRoomId(@PathVariable Long id) {
        return ResponseEntity.ok(chatRoomService.findChatRoomByClassRoomId(id));
    }
}



