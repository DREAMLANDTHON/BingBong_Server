package com.bingbong.consult.classroom.presentation;

import com.bingbong.consult.classroom.application.ClassRoomService;
import com.bingbong.consult.classroom.presentation.request.ClassRoomRequest;
import com.bingbong.consult.classroom.presentation.response.ClassRoomResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "Bearer Authentication")
public class ClassRoomController {
    private final ClassRoomService classRoomService;

    @PostMapping("/classRooms")
    public ResponseEntity<Long> createClassRoom(@RequestBody ClassRoomRequest form) {
        Long createdClassId = classRoomService.create(form);
        return ResponseEntity.ok(createdClassId);
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

    @GetMapping("/classRooms/teacher/{teacherId}")
    public ResponseEntity<List<ClassRoomResponse>> findClassRoomByTeacherId(
            @PathVariable Long teacherId
    ) {
        List<ClassRoomResponse> classRoomResponse = classRoomService.findClassRoomByTeacherId(teacherId);
        return ResponseEntity.ok(classRoomResponse);
    }
}
