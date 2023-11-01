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
import com.bingbong.consult.stomp.ApplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Apply save(ApplyRequest request){
        ChatRoom chatRoom = chatRoomRepo.findByRoomToken(request.getRoomToken()).get();
        return applyRepo.save(Apply.from(request, chatRoom));
    }

    @Transactional
    public Apply saveApply(ApplyRequest request){
        Optional<ChatRoom> chatRoom = chatRoomRepo.findByRoomToken(request.getRoomToken());
        if(!chatRoom.isPresent()) throw new IllegalArgumentException("존재하지 않는 ChatRoom입니다.");
        return applyRepo.save(Apply.from(request, chatRoom.get()));
    }

    public List<ApplyDto> findApplyByClassRoomId(Long classRoomId) {
        Optional<ClassRoom> classRoom = classRoomRepository.findById(classRoomId);
        if(!classRoom.isPresent()) throw new IllegalArgumentException("존재하지 않는 ClassRoom입니다.");
        return applyRepo.findAllByClassroomOrderByTimePinDesc(classRoom.get())
                .stream().map(
                        apply -> ApplyDto.builder()
                                .id(apply.getId())
                                .subject(apply.getSubject())
                                .status(apply.getStatus())
                                .parentName(apply.getChatRoom().getParent().getName())
                                .chatRoomId(apply.getChatRoom().getId())
                                .build()
                ).collect(Collectors.toList());
    }

    public boolean findApplyExist(ApplyRequest request) {
        Optional<Apply> ret = applyRepo.findByRoomTokenAndMemberId(request.getRoomToken(), request.getMemberId());
        if(ret.isPresent()) {
            return true;
        }
        return false;
    }
}
