package com.bingbong.consult.apply.application;

import com.bingbong.consult.apply.domain.Apply;
import com.bingbong.consult.apply.domain.repo.ApplyRepo;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.classroom.application.ClassRoomService;
import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.stomp.ApplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplyService {

    @Autowired
    private ApplyRepo applyRepo;

    @Transactional
    public Apply save(ApplyRequest request, ChatRoom chatRoom){
        return applyRepo.save(Apply.from(request, chatRoom));
    }

}
