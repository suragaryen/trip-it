package com.example.tripit.report.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ReportUpdateRequest {
    private Long reportId;
    private int reportFalseValue;

    // Getters and Setters
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public int getReportFalseValue() {
        return reportFalseValue;
    }

    public void setReportFalseValue(int reportFalseValue) {
        this.reportFalseValue = reportFalseValue;
    }
}
