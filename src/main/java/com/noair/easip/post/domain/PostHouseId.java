package com.noair.easip.post.domain;

import com.noair.easip.member.domain.Position;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostHouseId {
    @Column(name = "HOUSE_ID", nullable = false, columnDefinition = "CHAR(26)")
    private String houseId;

    @Column(name = "SUPPLY_TYPE", nullable = false, columnDefinition = "VARCHAR(100)")
    @Enumerated(EnumType.STRING)
    private Position supplyType;

    @Column(name = "LIVING_TYPE", nullable = false, columnDefinition = "VARCHAR(100)")
    private String livingType;

    @Column(name = "DEPOSIT", nullable = false)
    private Double deposit;

    @Column(name = "MONTHLY_RENT", nullable = false)
    private Double monthlyRent;
} 
