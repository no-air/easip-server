package com.noair.easip.post.controller.dto;

import com.noair.easip.member.domain.Position;

public record PostHouseFlatDto(
        Position supplyType,
        String livingType,
        Double deposit,
        Double monthlyRent,
        Integer supplyRoomCount
) {
}
