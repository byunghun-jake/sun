package com.sun.tingle.calendar.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarRqDto {
    String calendarCode;
    String calendarName;
    long id;
}