package com.example.tripit.schedule.controller;

import com.example.tripit.schedule.dto.DetailScheduleDto;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.dto.ScheduleRequest;
import com.example.tripit.schedule.service.ApiConnection;
import com.example.tripit.schedule.service.ScheduleService;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/home")
public class ScheduleController {

    private final ApiConnection apiConnection; //외부 Api 연결 담당 서비스 클래스
    private final ScheduleService scheduleService; //일정 관련 비즈니스 로직을 담당 서비스 클래스

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

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
    public ResponseEntity<?> saveSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        try {
            List<ScheduleDto> scheduleDtos = scheduleService.saveSchedule(scheduleRequest);
            return ResponseEntity.ok(scheduleDtos);
        } catch (Exception e) {
            // 예외 로그 출력
            logger.error("일정 저장 실패: {}", e.getMessage(), e);

            // 클라이언트에게 전달할 에러 메시지
            String errorMessage = "일정 저장 실패: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/{userId}/{scheduleId}")
    public ResponseEntity<?> getDetailSchedule(@PathVariable Long userId, @PathVariable Long scheduleId) {
        return scheduleService.detailSchedule(userId, scheduleId);
    }


}
