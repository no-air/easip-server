package com.noair.easip.post.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "일정 정보")
public record ScheduleDto(
        @Schema(description = "일정 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97")
        String id,

        @Schema(description = "일정 제목", example = "서류 대상자 발표")
        String title,

        @Schema(description = "시작 일정 (날짜 부분은 필수 값이지만, 시간 부분은 필수 값이 아님)\n" +
                                "- 시간형태가 아니라, 문자열이 들어올 수 있음 (ex. 공실 발생시 순차적으로 연락)", example = "2023-12-05T10:00:00")
        String start,

        @Schema(description = "종료 일정 (날짜 부분은 필수 값이지만, 시간 부분은 필수 값이 아님)\n" +
                                "- 시간형태가 아니라, 문자열이 들어올 수 있음 (ex. 공실 발생시 순차적으로 연락)" +
                                "- 일정이 기간이 아닌 일시인 경우, end 값은 Null", example = "2023-12-06T12:00:00")
        String end,

        @Schema(description = "푸시알림 등록 여부")
        boolean isPushAlarmRegistered   
) {
        public static ScheduleDto of(String id, String title, String start, String end, boolean isPushAlarmRegistered) {
                return new ScheduleDto(id, title, start, end, isPushAlarmRegistered);
        }
}
