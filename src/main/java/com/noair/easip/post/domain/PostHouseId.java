package com.noair.easip.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PostHouseId {
    @Column(name = "HOUSE_ID")
    private String houseId;

    @Column(name = "SUPPLY_TYPE")
    private String supplyType;

    @Column(name = "LIVING_TYPE")
    private String livingType;

    @Column(name = "DEPOSIT")
    private Double deposit;

    @Column(name = "MONTHLY_RENT")
    private Double monthlyRent;
} 