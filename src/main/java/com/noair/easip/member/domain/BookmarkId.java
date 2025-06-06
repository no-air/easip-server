package com.noair.easip.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkId {
    @Column(name = "MEMBER_ID", columnDefinition = "CHAR(26)")
    private String memberId;

    @Column(name = "HOUSE_ID", columnDefinition = "CHAR(26)")
    private String houseId;
} 