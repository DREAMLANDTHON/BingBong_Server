package com.bingbong.consult.chatroom.application;

import com.bingbong.consult.apply.domain.Apply;
import com.bingbong.consult.apply.domain.repo.ApplyRepo;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.chatroom.domain.repo.ChatRoomRepo;
import com.bingbong.consult.chatroom.presentation.response.ChatRoomResponse;
import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.classroom.domain.repository.ClassRoomRepository;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.domain.repository.MemberRepository;
import com.bingbong.consult.stomp.ApplyRequest;
import com.bingbong.consult.stomp.StartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepo chatRoomRepo;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private ApplyRepo applyRepo;
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public ChatRoom findById(Long id){
        return chatRoomRepo.findById(id).get();
    }

    @Transactional
    public ChatRoom findChatRoom(Long id) {
        ChatRoom ret = this.findById(id);
        return ret;
    }
    @Transactional
    public ChatRoom findChatRoomById(Long id) {
        ChatRoom ret = this.findById(id);
        return ret;
    }

    @Transactional
    public List<ChatRoomResponse> findChatRoomByClassRoomId(Long id) {
        return chatRoomRepo.findByClassRoomId(id)
                .stream().map(chatRoom -> {
                    Optional<Apply> applyInfo = applyRepo.findFirstByChatRoomOrderByCreatedAtDesc(chatRoom);
                    String subject = "아직 신청된 주제가 없습니다.";
                    if(applyInfo.isPresent()) subject = applyInfo.get().getSubject();
                    return ChatRoomResponse.builder()
                            .id(chatRoom.getId())
                            .session(chatRoom.isSession())
                            .roomToken(chatRoom.getRoomToken())
                            .parentName(chatRoom.getParent().getName())
                            .subject(subject)
                            .build();
                }).collect(Collectors.toList());
    }

//    @Transactional
//    public ChatRoom findMemberAndClassRoom(ApplyRequest request) {
//        return chatRoomRepo.findMemberAndClassRoom(request.getMemberId(), request.getClassId());
//    }

    @Transactional
    public ChatRoom sessionUpdate(StartRequest request) {
        Optional<ChatRoom> ret = chatRoomRepo.findById(request.getChatRoomId());
        if(ret.isPresent()) {
            ret.get().updateSession(request.getType());
            if(request.getType().equals("start")) {
                System.out.println(ret.get().getClassRoom().getId());
                System.out.println(ret.get().getParent().getId());
                Apply apply = applyRepo.findByClassRoomIdAndMemberId(ret.get().getId(), ret.get().getParent().getId()).get();
                apply.updateStatus();
            }
            return ret.get();
        }
        else return null;
    }

    public List<ChatRoomResponse> findChatRoomByClassRoomIdAndParent(Long classRoomId, String parentEmail) {
        Optional<ClassRoom> classRoom = classRoomRepository.findById(classRoomId);
        classRoom.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ClassRoom입니다."));

        Optional<Member> parent = memberRepository.findByEmailAndRole(parentEmail, "parent");
        parent.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Member입니다."));

        Optional<ChatRoom> chatRoom = chatRoomRepo.findByClassRoomAndParent(classRoom.get(), parent.get());
        chatRoom.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ChatRoom입니다."));

        List<Apply> recentApplyInfo = applyRepo.findAllByChatRoomOrderByCreatedAtDesc(chatRoom.get());

        String recentSubject = (recentApplyInfo.size() == 0) ? "아직 신청된 주제가 없습니다." : recentApplyInfo.get(0).getSubject();
        return Arrays.asList(ChatRoomResponse.builder()
                .id(chatRoom.get().getId())
                .session(chatRoom.get().isSession())
                .parentName(chatRoom.get().getParent().getName())
                .subject(recentSubject)
                .build());
    }
}
