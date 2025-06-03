package com.noair.easip.post.domain;

public enum SUBSCRIPTION_STATE {
    SCHEDULED("청약예정"),
    ONGOING("청약중"),
    COMPLETED("청약완료");

    private final String name;

    SUBSCRIPTION_STATE(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
