package com.bingbong.consult.classroomMember.presentation;

import com.bingbong.consult.classroom.presentation.response.ClassRoomResponse;
import com.bingbong.consult.classroomMember.application.ClassRoomMemberService;
import com.bingbong.consult.classroomMember.presentation.request.JoinClassRequest;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.presentation.dto.MemberDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "Bearer Authentication")
public class ClassRoomMemberController {
    private final ClassRoomMemberService classRoomMemberService;

    @PostMapping("/classRoomMembers")
    public ResponseEntity<Long> createClassRoomMember(@RequestBody JoinClassRequest form) {
        try{
            Long savedId = classRoomMemberService.joinClass(form.getCode(), form.getMemberId());

            return ResponseEntity.ok(savedId);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/classRoomMembers/classrooms/{id}")
    public ResponseEntity<List<ClassRoomResponse>> findMyClassRooms(@PathVariable Long id) {
        return ResponseEntity.ok(classRoomMemberService.findMyClassRoom(id));
    }

    @GetMapping("/classRoomMembers/members/{parentId}")
    public ResponseEntity<List<MemberDto>> findClassRoomMembers(@PathVariable Long parentId) {
        return ResponseEntity.ok(classRoomMemberService.findClassRoomMembers(parentId));
    }

    @GetMapping("/classRoomMembers/{classRoomId}")
    public ResponseEntity<List<MemberDto>> findClassRoomMembersByClassRoomId(@PathVariable Long classRoomId) {
        return ResponseEntity.ok(classRoomMemberService.findClassRoomMembersByClassRoomId(classRoomId));
    }
    @DeleteMapping("/{groupId}/DeleteClassMembers/{deletingMemberId}")
    public void deleteMemberInClassRoom(@PathVariable Long groupId,@PathVariable Long deletingMemberId){
         classRoomMemberService.deleteMemberInClassRoom(groupId,deletingMemberId);
    }

}
