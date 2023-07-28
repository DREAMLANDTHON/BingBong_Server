package com.bingbong.consult.classroom.presentation;

import com.bingbong.consult.classroom.application.ClassRoomService;
import com.bingbong.consult.classroom.presentation.request.ClassRoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClassRoomController {
    private final ClassRoomService classRoomService;

    /**
     * 회원가입
     * */
    @PostMapping("/classRoom")
    public ResponseEntity<Long> registerMember(@RequestBody ClassRoomRequest form) {
        return ResponseEntity.ok(classRoomService.create(form));
    }
}
