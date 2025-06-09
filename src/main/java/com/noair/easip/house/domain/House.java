package com.noair.easip.house.domain;

import java.util.ArrayList;
import java.util.List;
import com.noair.easip.member.domain.Bookmark;
import com.noair.easip.util.DeletableBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HOUSE")
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class House extends DeletableBaseEntity {
    @Id
    @Column(name = "house_id", columnDefinition = "CHAR(26)")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private String jibunAddress;

    private String nearStation;

    private String developerName;

    private String constructorName;

    private String firstRecruitmentDate;

    private String moveInDate;

    @Column(nullable = false)
    private Integer generalSupplyCount;

    @Column(nullable = false)
    private Integer specialSupplyCount;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(columnDefinition = "VARCHAR(2083)")
    private String pageUrl;

    @OneToMany(mappedBy = "house")
    @Builder.Default
    private List<HouseImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "house")
    @Builder.Default
    private List<RoomRentalCondition> roomRentalConditions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISTRICT_ID", nullable = false)
    private District district;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "HOUSE_BADGE",
        joinColumns = @JoinColumn(name = "HOUSE_ID"),
        inverseJoinColumns = @JoinColumn(name = "BADGE_ID")
    )
    @Builder.Default
    private List<Badge> badges = new ArrayList<>();

    @OneToMany(mappedBy = "house")
    @Builder.Default
    private List<Bookmark> bookmarks = new ArrayList<>();

    public List<String> getBadgeNames() {
        return badges.stream()
                .map(Badge::getName)
                .toList();
    }
}
