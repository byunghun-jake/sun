package com.sun.tingle.calendar.responsedto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarRpDto {
    String calendarCode;
    String calendarName;
    long id;

}
