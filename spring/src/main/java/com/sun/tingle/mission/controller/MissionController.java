package com.sun.tingle.mission.controller;

import com.sun.tingle.mission.db.entity.MissionEntity;
import com.sun.tingle.mission.requestdto.MissionRqDto;
import com.sun.tingle.mission.responsedto.MissionRpDto;
import com.sun.tingle.mission.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mission")
public class MissionController {
    @Autowired
    MissionService missionService;


    @PostMapping
    public ResponseEntity<MissionRpDto> insertMission(@RequestBody MissionRqDto missionRqDto) {
        System.out.println(missionRqDto.toString());
        MissionRpDto missionRpDto = missionService.insertMission(missionRqDto);
        if(missionRpDto == null) {
            return new ResponseEntity<MissionRpDto>(missionRpDto, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<MissionRpDto>(missionRpDto,HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<MissionRpDto> selectMission(@RequestParam("missionId") Long missionId) {
        MissionRpDto missionRpDto = missionService.selectMission(missionId);
        if(missionRpDto == null) {
            return new ResponseEntity<MissionRpDto>(missionRpDto,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<MissionRpDto>(missionRpDto,HttpStatus.OK);
    }


    @PutMapping("{missionId}")
    public ResponseEntity<MissionRpDto> updateMission(@PathVariable("missionId") Long missionId,@RequestBody MissionRqDto missionRqDto) {
        MissionRpDto missionRpDto = missionService.updateMission(missionId,missionRqDto);
        if(missionRpDto == null) {
            return new ResponseEntity<MissionRpDto>(missionRpDto,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<MissionRpDto>(missionRpDto,HttpStatus.CREATED);

    }

    @DeleteMapping("{missionId}")
    public ResponseEntity<Void> deleteMission(@PathVariable("missionId") Long missionId) {

        try {
            missionService.deleteMission(missionId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); //삭제가 됐을 때
        }
        catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR); //삭제할 캘린더가 없을 때
        }

    }

    @GetMapping("{calendarCode}")
    public ResponseEntity<List<MissionRpDto>> selectMissionList(@PathVariable("calendarCode") String calendarCode) {

        List<MissionRpDto> list = missionService.selectMissionList(calendarCode);

        if(list.size() ==0) {
            return new ResponseEntity<List<MissionRpDto>>(list,HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<MissionRpDto>>(list,HttpStatus.OK);


    }

}
