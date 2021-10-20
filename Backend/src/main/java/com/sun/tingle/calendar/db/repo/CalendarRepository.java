package com.sun.tingle.calendar.db.repo;


import com.sun.tingle.calendar.db.entity.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity,String> {

    public Optional<CalendarEntity> findByCalendarCode(String calendarCode);
    public List<CalendarEntity> findByMemberId(String memberId);


}



