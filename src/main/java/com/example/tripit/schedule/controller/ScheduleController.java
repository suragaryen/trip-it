package com.example.tripit.schedule.controller;

import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.service.ApiConnection;
import com.example.tripit.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/home")
public class ScheduleController {

    private final ApiConnection apiConnection; //외부 Api 연결 담당 서비스 클래스
    private final ScheduleService scheduleService; //일정 관련 비즈니스 로직을 담당 서비스 클래스

    @Autowired
    public ScheduleController(ApiConnection apiConnection, ScheduleService scheduleService) {
        this.apiConnection = apiConnection;
        this.scheduleService = scheduleService;
    }

    @GetMapping({"/test/{metroId}/{pageNo}", "/test/{metroId}/{pageNo}/{contentTypeId}"})
    public ResponseEntity<Object> apiTest1(@PathVariable String metroId,
                                           @PathVariable String pageNo,
                                           @PathVariable(required = false) String contentTypeId) {
        //contentTypeId가 제공되지 않았을 때 기본값 설정
        if (contentTypeId == null) {
            contentTypeId = "12";
        }

        //apiConnection 메서드 호출 및 반환
        return apiConnection.cultureFacilityApi(metroId, pageNo, contentTypeId);
    }

    @GetMapping("/detailTest/{contentId}")
    public ResponseEntity<Object> apiTest2(@PathVariable String contentId) {
        return apiConnection.detailApi(contentId);
    }

    @GetMapping("/test/{metroId}/{pageNo}/{contentTypeId}/{keyword}")
    public ResponseEntity<Object> apiTest3(@PathVariable String metroId,
                                           @PathVariable String pageNo,
                                           @PathVariable String contentTypeId,
                                           @PathVariable String keyword) throws UnsupportedEncodingException {
        return apiConnection.searchApi(metroId, pageNo, contentTypeId, keyword);
    }

    @PostMapping("/save")
    public String saveSchedule(@RequestBody ScheduleDto scheduleDto){
        System.out.println(scheduleDto);
        scheduleService.saveSchedule(scheduleDto);
        return "아";
    }

}
