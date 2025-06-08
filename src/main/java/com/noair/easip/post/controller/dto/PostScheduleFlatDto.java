package com.noair.easip.post.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PostScheduleFlatDto(
        int ordering,
        String scheduleType,
        String title,
        LocalDate startDate,
        LocalDateTime startDateTime,
        String startNote,
        LocalDateTime endDateTime,
        String endNote
) {
}
