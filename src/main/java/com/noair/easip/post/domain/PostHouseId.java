package com.noair.easip.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PostHouseId {
    @Column(name = "HOUSE_ID", nullable = false, columnDefinition = "CHAR(26)")
    private String houseId;

    @Column(name = "SUPPLY_TYPE", nullable = false, columnDefinition = "VARCHAR(100)")
    private String supplyType;

    @Column(name = "LIVING_TYPE", nullable = false, columnDefinition = "VARCHAR(100)")
    private String livingType;

    @Column(name = "DEPOSIT", nullable = false)
    private Double deposit;

    @Column(name = "MONTHLY_RENT", nullable = false)
    private Double monthlyRent;
} 