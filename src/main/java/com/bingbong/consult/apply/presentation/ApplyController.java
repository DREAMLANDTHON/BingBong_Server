package com.bingbong.consult.apply.presentation;

import com.bingbong.consult.apply.application.ApplyService;
import com.bingbong.consult.chatroom.application.ChatRoomService;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.chatroom.presentation.response.ChatRoomResponse;
import com.bingbong.consult.stomp.ApplyRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "Bearer Authentication")
public class ApplyController {

    @Autowired
    private ApplyService applyService;


    @GetMapping("/applies/byClassRoom/{classRoomId}")
    public ResponseEntity<List<ApplyDto>> findApplyByClassRoomId(@PathVariable Long classRoomId) {
        return ResponseEntity.ok(applyService.findApplyByClassRoomId(classRoomId));
    }

    @GetMapping("/applies/parent/{classRoomId}")
    public ResponseEntity<List<ApplyDto>> findApplyByClassRoomIdAsParent(@PathVariable Long classRoomId) {
        return ResponseEntity.ok(applyService.findApplyByClassRoomIdAsParent(classRoomId, "jyj9747@kakao.com"));
    }
    @GetMapping("/applies/{applyId}")
    public ResponseEntity<ApplyDto> findById(@PathVariable Long applyId){
        return ResponseEntity.ok(applyService.findById(applyId));
    }

    @PostMapping("/applies")
    public ResponseEntity<Long> save(@RequestBody ApplyRequest request) {
        return ResponseEntity.ok(applyService.saveApply(request).getId());
    }
}



