package com.noair.easip.house.domain;

import com.noair.easip.member.domain.Position;
import com.noair.easip.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROOM_RENTAL_CONDITION")
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class RoomRentalCondition extends BaseEntity {
    @Id
    @Column(name = "RENTAL_CONDITION_ID", columnDefinition = "CHAR(26)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_ID", nullable = false)
    private House house;

    @Column(name = "SUPPLY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Position supplyType;

    @Column(name = "ROOM_TYPE", nullable = false)
    private String roomType;

    @Column(name = "EXCLUSIVE_AREA", nullable = false)
    private Double exclusiveArea;

    @Column(name = "TOTAL_ROOM_COUNT", nullable = false)
    private Integer totalRoomCount;

    @Column(name = "DEPOSIT", nullable = false)
    private Double deposit;

    @Column(name = "MONTHLY_RENT", nullable = false)
    private Double monthlyRent;

    @Column(name = "MAINTENANCE_FEE", nullable = false)
    private Double maintenanceFee;
} 
