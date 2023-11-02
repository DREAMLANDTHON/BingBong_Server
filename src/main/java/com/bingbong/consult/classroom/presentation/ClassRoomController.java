package com.bingbong.consult.classroom.presentation;

import com.bingbong.consult.classroom.application.ClassRoomService;
import com.bingbong.consult.classroom.posts.Post;
import com.bingbong.consult.classroom.posts.repository.PostRepository;
import com.bingbong.consult.classroom.presentation.request.ClassRoomRequest;
import com.bingbong.consult.classroom.presentation.request.PostRequest;
import com.bingbong.consult.classroom.presentation.response.ClassRoomResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "Bearer Authentication")
public class ClassRoomController {
    private final ClassRoomService classRoomService;
    private final PostRepository postRepository;

    @PostMapping("/classRooms")
    public ResponseEntity<String> createClassRoom(@RequestBody ClassRoomRequest form) {
        return ResponseEntity.ok(classRoomService.create(form));
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

    @GetMapping("/classRooms/byGroupCode/{groupCode}")
    public ResponseEntity<ClassRoomResponse> findClassRoomByGroupCode(
            @PathVariable String groupCode
    ) {
        try{
            ClassRoomResponse classRoomResponse = classRoomService.findClassRoomByGroupCode(groupCode);
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

    @GetMapping("/classRooms/parent/{parentId}")
    public ResponseEntity<List<ClassRoomResponse>> findClassRoomByParentId(
            @PathVariable Long parentId
    ) {
        List<ClassRoomResponse> classRoomResponse = classRoomService.findClassRoomByParentId(parentId);
        return ResponseEntity.ok(classRoomResponse);
    }

    @GetMapping("/{classRoomId}/posts")
    public ResponseEntity<List<Post>> getClassRoomPost(@PathVariable Long classRoomId){
        List<Post> posts = classRoomService.getClassRoomPost(classRoomId);
        return ResponseEntity.ok(posts);
    }

//        classRoomId로 어디에 post 올릴지 결정하고 Post내용들 추가한다
    @PostMapping("/{classRoomId}/addPosts")
    public ResponseEntity<Post> addClassPost(@PathVariable Long classRoomId,@RequestBody PostRequest post){
        return ResponseEntity.ok(classRoomService.addClassPost(classRoomId, post));
    }
}
