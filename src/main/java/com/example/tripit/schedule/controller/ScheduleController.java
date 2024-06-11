package com.example.tripit.schedule.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripit.schedule.service.ApiExplorer;


@RestController
public class ScheduleController {

    private final ApiExplorer apiExplorer;

    @Autowired
    public ScheduleController(ApiExplorer apiExplorer) {
        this.apiExplorer = apiExplorer;
    }

    @GetMapping("test")
    public ResponseEntity<Object> apiTest(@RequestParam String metroId) throws IOException {
        try {
            ResponseEntity<Object> jsonResponse = apiExplorer.apiTest(metroId);
            return jsonResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
