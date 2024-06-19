package com.example.tripit.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDate;


@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    @JsonProperty("schedule_id")
    private Long scheduleId;

    @JsonProperty("metro_id")
    private String metro_id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    @JsonProperty("start_date")
    private LocalDate start_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    @JsonProperty("end_date")
    private LocalDate end_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    @JsonProperty("register_date")
    private LocalDate register_date;

    @JsonProperty("schedule_title")
    private String schedule_title;


}
