package com.noair.easip.post.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.noair.easip.post.domain.ScheduleType;
import com.noair.easip.util.FlexibleLocalDateTimeDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PostScheduleFlatDto(
        int ordering,

        ScheduleType scheduleType,

        String title,

        LocalDate startDate,

        @JsonDeserialize(using = FlexibleLocalDateTimeDeserializer.class)
        LocalDateTime startDateTime,

        String startNote,

        @JsonDeserialize(using = FlexibleLocalDateTimeDeserializer.class)
        LocalDateTime endDateTime,

        String endNote
) {
}
