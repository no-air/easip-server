package com.noair.easip.post.controller.dto;

public record PostHouseFlatDto(
        String supplyType,
        String livingType,
        Double deposit,
        Double monthlyRent,
        Integer supplyRoomCount
) {
}
