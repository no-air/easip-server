package com.noair.easip.post.domain;

public enum SUBSCRIPTION_STATE {
    SCHEDULED("청약예정"),
    ONGOING("청약중"),
    COMPLETED("청약완료");

    private final String korName;

    SUBSCRIPTION_STATE(String korName) {
        this.korName = korName;
    }

    public String getKorName() {
        return korName;
    }
}
