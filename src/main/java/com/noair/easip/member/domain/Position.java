package com.noair.easip.member.domain;

import lombok.Getter;

@Getter
public enum Position {
    MALE_YOUNG_MAN("청년(남)"),
    FEMALE_YOUNG_MAN("청년(여)"),
    SHARE_YOUNG_MAN("청년(쉐어)"),
    YOUNG_MAN("청년"),
    NEWLY_MARRIED_COUPLE("(예비)신혼부부"),
    BOTH("청년 및 신혼부부")
    ;

    private final String korName;

    Position(String korName) {
        this.korName = korName;
    }
}
