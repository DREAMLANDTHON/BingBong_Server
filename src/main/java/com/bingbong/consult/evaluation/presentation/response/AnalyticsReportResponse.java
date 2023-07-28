package com.bingbong.consult.evaluation.presentation.response;

import lombok.Data;

@Data
public class AnalyticsReportResponse {
    FiveCriteria recentReport;
    FiveCriteria averageReport;
}
