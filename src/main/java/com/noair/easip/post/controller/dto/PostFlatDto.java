package com.noair.easip.post.controller.dto;

public record PostFlatDto(
        boolean isIncomeLimited,
        Integer incomeLimit1Person,
        Integer incomeLimit2Person,
        Integer incomeLimit3Person,
        Integer incomeLimit4Person,
        Integer incomeLimit5Person,
        boolean isCarPriceLimited,
        Integer carPriceLimit,
        boolean isAssetLimited,
        Integer youngManAssetLimit,
        Integer newlyMarriedCoupleAssetLimit
        ) {
}
