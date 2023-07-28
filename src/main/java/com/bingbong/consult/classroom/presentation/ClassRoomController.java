package com.bingbong.consult.classroom.presentation;

import com.bingbong.consult.classroom.application.ClassRoomService;
import com.bingbong.consult.classroom.presentation.request.ClassRoomRequest;
import com.bingbong.consult.classroom.presentation.response.ClassRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClassRoomController {
    private final ClassRoomService classRoomService;

    @PostMapping("/classRooms")
    public ResponseEntity<Long> createClassRoom(@RequestBody ClassRoomRequest form) {
        try{
            Long createdClassId = classRoomService.create(form);
            return ResponseEntity.ok(createdClassId);
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/classRooms/{id}")
    public ResponseEntity<ClassRoomResponse> findClassRoom(@PathVariable Long id) {
        try{
            ClassRoomResponse classRoomResponse = classRoomService.findClassRoom(id);
            return ResponseEntity.ok(classRoomResponse);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
