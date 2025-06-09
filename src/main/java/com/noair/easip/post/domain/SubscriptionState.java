package com.noair.easip.post.domain;

import lombok.Getter;

@Getter
public enum SubscriptionState {
    SCHEDULED("청약예정"),
    ONGOING("청약중"),
    COMPLETED("청약완료");

    private final String korName;

    SubscriptionState(String korName) {
        this.korName = korName;
    }

}
