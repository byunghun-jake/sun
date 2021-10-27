package com.sun.tingle.calendar.db.repo;


import com.sun.tingle.calendar.db.entity.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity,String> {

    public CalendarEntity findByCalendarCode(String calendarCode);
    public CalendarEntity findByCalendarName(String calendarName);
    public List<CalendarEntity> findById(long id);


}



