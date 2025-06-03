package com.noair.easip.member.domain;

import lombok.Getter;

@Getter
public enum Position {
    YOUNG_MAN("청년"),
    NEWLY_MARRIED_COUPLE("(예비)신혼부부"),
    ;

    private final String name;

    Position(String name) {
        this.name = name;
    }
}
