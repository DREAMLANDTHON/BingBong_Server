package com.bingbong.consult.apply.application;

import com.bingbong.consult.apply.domain.Apply;
import com.bingbong.consult.apply.domain.repo.ApplyRepo;
import com.bingbong.consult.apply.presentation.ApplyDto;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.chatroom.domain.repo.ChatRoomRepo;
import com.bingbong.consult.classroom.application.ClassRoomService;
import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.classroom.domain.repository.ClassRoomRepository;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.domain.repository.MemberRepository;
import com.bingbong.consult.stomp.ApplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplyService {

    @Autowired
    private ApplyRepo applyRepo;
    @Autowired
    private ChatRoomRepo chatRoomRepo;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private MemberRepository memberRepository;

//    @Transactional
//    public Apply save(ApplyRequest request, ChatRoom chatRoom){
//        return applyRepo.save(Apply.from(request, chatRoom));
//    }

    @Transactional
    public Apply saveApply(ApplyRequest request){

        Optional<Apply> ret = applyRepo.findByClassRoomIdAndMemberId(request.getClassId(), request.getMemberId());
        if(ret.isPresent()) return ret.get();

        Optional<ClassRoom> classRoom = classRoomRepository.findById(request.getClassId());
        classRoom.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ClassRoom입니다."));

        Optional<Member> parent = memberRepository.findById(request.getMemberId());
        parent.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Member입니다."));

        Optional<ChatRoom> chatRoom = chatRoomRepo.findByClassRoomIdAndParentId(request.getClassId(), request.getMemberId());
        chatRoomRepo.save(ChatRoom.from(classRoom.get(), parent.get()));
//        chatRoom.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ChatRoom입니다."));

        return applyRepo.save(Apply.from(request, chatRoom.get(), parent.get()));
    }

    public List<ApplyDto> findApplyByClassRoomId(Long classRoomId) {
        Optional<ClassRoom> classRoom = classRoomRepository.findById(classRoomId);
        if(!classRoom.isPresent()) throw new IllegalArgumentException("존재하지 않는 ClassRoom입니다.");
        return applyRepo.findAllByClassroomOrderByTimePinDesc(classRoom.get())
                .stream().map(
                        apply -> ApplyDto.builder()
                                .id(apply.getId())
                                .roomToken(apply.getChatRoom().getRoomToken())
                                .subject(apply.getSubject())
                                .status(apply.getStatus())
                                .parentName(apply.getChatRoom().getParent().getName())
                                .chatRoomId(apply.getChatRoom().getId())
                                .build()
                ).collect(Collectors.toList());
    }

    public boolean findApplyExist(ApplyRequest request) {
        Optional<Apply> ret = applyRepo.findByClassRoomIdAndMemberId(request.getClassId(), request.getMemberId());
        if(ret.isPresent()) {
            return true;
        }
        return false;
    }

    public List<ApplyDto> findApplyByClassRoomIdAsParent(Long classRoomId, String parentEmail) {
        Optional<ClassRoom> classRoom = classRoomRepository.findById(classRoomId);
        classRoom.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ClassRoom입니다."));

        Optional<Member> parent = memberRepository.findByEmailAndRole(parentEmail, "parent");
        parent.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Member입니다."));

        Optional<ChatRoom> chatRoom = chatRoomRepo.findByClassRoomIdAndParentId(classRoom.get().getId(), parent.get().getId());
//        Optional<ChatRoom> chatRoom = chatRoomRepo.findByClassRoomAndParent(classRoom.get(), parent.get());
        chatRoom.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ChatRoom입니다."));

        List<Apply> applies = applyRepo.findAllByChatRoomOrderByCreatedAtDesc(chatRoom.get());
        return applies.stream().map(
                apply -> ApplyDto.builder()
                        .id(apply.getId())
                        .roomToken(apply.getChatRoom().getRoomToken())
                        .subject(apply.getSubject())
                        .status(apply.getStatus())
                        .parentName(apply.getChatRoom().getParent().getName())
                        .chatRoomId(apply.getChatRoom().getId())
                        .build()
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ApplyDto findById(Long applyId) {
        Apply ret = applyRepo.findById(applyId).get();
        return ApplyDto.builder()
                .roomToken(ret.getChatRoom().getRoomToken())
                .chatRoomId(ret.getChatRoom().getId())
                .id(ret.getId())
                .subject(ret.getSubject())
                .parentName(ret.getReqMem().getName())
                .status(ret.getStatus())
                .build();
    }

//    public Apply findApplyByClassRoomIdAndMemberId(ApplyRequest request) {
//        return applyRepo.findByChatRoomIdAndMemberId(request.getClassId(), request.getMemberId());
//    }
}
