package com.bingbong.consult.classroom.presentation.request;

import lombok.Data;

@Data
public class ClassRoomRequest {
    String classRoomName;
    String description;
    String groupCode;
    Integer year;
    Long teacherId;
}
