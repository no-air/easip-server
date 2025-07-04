package com.noair.easip.post.domain;

import java.util.ArrayList;
import java.util.List;
import com.noair.easip.house.domain.Badge;
import com.noair.easip.post.exception.IncomeLimitPersonExceedException;
import com.noair.easip.util.DeletableBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST")
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class Post extends DeletableBaseEntity {
    @Id
    @Column(name = "POST_ID", columnDefinition = "CHAR(26)")
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Boolean isIncomeLimited;
    @Column(name = "INCOME_LIMIT_1_PERSON")
    private Double incomeLimit1Person;
    @Column(name = "INCOME_LIMIT_2_PERSON")
    private Double incomeLimit2Person;
    @Column(name = "INCOME_LIMIT_3_PERSON")
    private Double incomeLimit3Person;
    @Column(name = "INCOME_LIMIT_4_PERSON")
    private Double incomeLimit4Person;
    @Column(name = "INCOME_LIMIT_5_PERSON")
    private Double incomeLimit5Person;

    @Column(nullable = false)
    private Boolean isCarPriceLimited;
    private Double carPriceLimit;

    @Column(nullable = false)
    private Boolean isAssetLimited;
    private Double youngManAssetLimit;
    private Double newlyMarriedCoupleAssetLimit;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "POST_BADGE",
        joinColumns = @JoinColumn(name = "POST_ID"),
        inverseJoinColumns = @JoinColumn(name = "BADGE_ID")
    )
    @Builder.Default
    private List<Badge> badges = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<PostSchedule> schedules = new ArrayList<>();

    public double getIncomeLimit(int familyMemberCount) {
        return switch (familyMemberCount) {
            case 0 -> incomeLimit1Person; // 0명은 1인 기준으로 처리
            case 1 -> incomeLimit1Person;
            case 2 -> incomeLimit2Person;
            case 3 -> incomeLimit3Person;
            case 4 -> incomeLimit4Person;
            case 5 -> incomeLimit5Person;
            default -> incomeLimit5Person; // 6명 이상은 5인 기준으로 처리
        };
    }

    public List<String> getBadgeNames() {
        return badges.stream()
            .map(Badge::getName)
            .toList();
    }
} 
