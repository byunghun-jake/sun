package com.sun.tingle.mission.responsedto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionRpDto {

    private Long missionId;
    private String missionName;
    private String startDate;
    private  String endDate;
    private  List<String> tag;
    private  String calendarCode;
}
