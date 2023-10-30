package com.bingbong.consult.apply.presentation;

import com.bingbong.consult.apply.domain.ApplyStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplyDto {

    Long id;
    String subject;
    ApplyStatus status;
    String parentName;
    Long chatRoomId;
}
