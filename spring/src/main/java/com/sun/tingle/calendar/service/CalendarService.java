package com.sun.tingle.calendar.service;

import com.sun.tingle.calendar.db.entity.CalendarEntity;
import com.sun.tingle.calendar.db.entity.ShareCalendarEntity;
import com.sun.tingle.calendar.responsedto.CalendarRpDto;

import java.util.List;
import java.util.Map;

public interface CalendarService {

    CalendarRpDto insertCalendar(String calendarCode, String calendarName, long id);
    CalendarRpDto selectCalendar(String calendarCode);
    CalendarRpDto updateCalendar(String calendarCode,String calendarName);
    void deleteCalendar(String calendarCode);
    Map<String,Object> insertShareCalendar(String calendarCode,long id);
    void deleteShareCalendar(String calendarCode,long id);

    List<CalendarRpDto> getMyCalendarList(long id);
    List<CalendarRpDto> getShareCalendarList(long id);

}
