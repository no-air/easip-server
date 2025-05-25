package com.noair.easip.post.domain;

import com.noair.easip.house.domain.House;
import com.noair.easip.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST_HOUSE")
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class PostHouse extends BaseEntity {
    @EmbeddedId
    private PostHouseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", columnDefinition = "CHAR(26)")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("houseId")
    @JoinColumn(name = "HOUSE_ID")
    private House house;

    @Column(name = "SUPPLY_ROOM_COUNT", nullable = false)
    private Integer supplyRoomCount;
} 