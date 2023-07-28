package com.bingbong.consult.classroomMember.presentation;

import com.bingbong.consult.classroomMember.application.ClassRoomMemberService;
import com.bingbong.consult.classroomMember.presentation.response.ClassRoomMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClassRoomMemberController {
    private final ClassRoomMemberService classRoomMemberService;

    /**
     * 내가 등록된 학급 조회
     * */
//    @RequestMapping("/classRoomMember?memberId={memberId}")
//    public ResponseEntity<ClassRoomMemberResponse> getMyClassRoom(@PathVariable Long memberId){
//
//    }
}
