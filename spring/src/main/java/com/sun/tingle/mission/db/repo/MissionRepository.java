package com.sun.tingle.mission.db.repo;

import com.sun.tingle.mission.db.entity.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<MissionEntity,Long> {
    MissionEntity findByMissionName(String missionName);
    MissionEntity findByMissionId(Long MissionId);
    List<MissionEntity> findByCalendarCode(String calendarCode);

}
