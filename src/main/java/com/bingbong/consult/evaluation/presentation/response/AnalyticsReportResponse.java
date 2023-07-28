package com.bingbong.consult.evaluation.presentation.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnalyticsReportResponse {
    FiveCriteria recentReport;
    FiveCriteria averageReport;
}
