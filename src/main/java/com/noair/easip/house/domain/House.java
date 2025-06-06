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
    @Column(name = "HOUSE_ID", columnDefinition = "CHAR(26)")
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "NEAR_STATION")
    private String nearStation;

    @Column(name = "DEVELOPER_NAME")
    private String developerName;

    @Column(name = "CONSTRUCTOR_NAME")
    private String constructorName;

    @Column(name = "FIRST_RECRUITMENT_DATE")
    private String firstRecruitmentDate;

    @Column(name = "MOVE_IN_DATE")
    private String moveInDate;

    @Column(name = "GENERAL_SUPPLY_COUNT", nullable = false)
    private Integer generalSupplyCount;

    @Column(name = "SPECIAL_SUPPLY_COUNT", nullable = false)
    private Integer specialSupplyCount;

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "PAGE_URL", columnDefinition = "VARCHAR(2083)")
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
